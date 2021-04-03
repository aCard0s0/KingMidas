package dev.midas.dss.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import dev.midas.dss.config.domain.CandlesConfig;
import dev.midas.dss.config.domain.StorageConfig;
import dev.midas.dss.mongo.domain.Candle;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLC;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLCs;
import org.knowm.xchange.kraken.service.KrakenBaseService;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoManager {

    private final MongoClient mongo;
    private final MongoDatabase dataStore;

    public MongoManager(MongoConfig config) {
        this.mongo = config.mongo();
        this.dataStore = mongo.getDatabase("mydatabase");
    }

    public void updateDB(StorageConfig config) {
        for (CandlesConfig pair: config.getPairs()) {
            for (String step: pair.getSteps()) {

                String collection = config.getExchangeId()+"_"+pair.getPair().toLowerCase()+"_"+step;
                System.out.println(collection);
            }
        }
        //String collection = "candle";
        //this.createCollectionIfNotExists(collection);
        //this.updateCollection(collection, 60);
    }

    private void createCollectionIfNotExists(String collName) {
        if (!collectionExists(collName)) {
            dataStore.createCollection(collName);
        }
    }

    private boolean collectionExists(String collName) {
        for (String s : dataStore.listCollectionNames()) {
            if (collName.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private void updateCollection(String collName, int step) {
        MongoCollection<Candle> collection = dataStore.getCollection(collName, Candle.class);
        Candle lastCandle = getLastCandle(collection);
        assert lastCandle != null;

        long lastTimeStamp = lastCandle.getTimestamp() + step;
        System.out.println(lastCandle);
        System.out.println(lastTimeStamp);

        while (lastTimeStamp < Instant.now().getEpochSecond()) {
            List<Candle> candles = mockExchangeService(step, lastTimeStamp);
            collection.insertMany(candles);
            lastTimeStamp = candles.get(candles.size()-1).getTimestamp();

        }
    }

    private Candle getLastCandle(MongoCollection<Candle> collection) {
        return collection.find().sort(new BasicDBObject("_id", -1)).first();
    }

    private List<Candle> mockExchangeService(Integer step, long timestamp) {
        Exchange broker = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
        KrakenBaseService service = new KrakenBaseService(broker);
        KrakenMarketDataServiceRaw test = new KrakenMarketDataServiceRaw(broker);
        List<Candle> candles = new ArrayList<>();

        try {
            KrakenOHLCs krakenOHLC = test.getKrakenOHLC(CurrencyPair.BTC_EUR, step, timestamp);
            List<KrakenOHLC> ohlCs = krakenOHLC.getOHLCs();
            ohlCs.forEach(ohlc -> {
                candles.add(new Candle(
                        ohlc.getTime(),
                        ohlc.getOpen(),
                        ohlc.getHigh(),
                        ohlc.getLow(),
                        ohlc.getClose(),
                        ohlc.getVolume()));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return candles;
    }



    private void addCandle(String collectionName, Candle candle) {
        dataStore.getCollection(collectionName, Candle.class).insertOne(candle);
    }

    //private final CandleRepository repository;
    /*public void testing() {
        Candle candle = new Candle(
                1325379600L,new BigDecimal("1"),new BigDecimal("1"),
                new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("1"));

        repository.save(candle);

        // fetch all customers
        System.out.println("-------------------------------");
        for (Candle customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("--------------------------------");
        System.out.println(repository.findById("603bdd4a3cbb5fa41078d0d4"));
    }*/
}
