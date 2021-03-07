package dev.midas.dss.exchanges;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.service.KrakenBaseService;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class ExchangeManager {

    public void testing() throws IOException {

        Exchange broker = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

        MarketDataService marketDataService = broker.getMarketDataService();
        Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

        KrakenBaseService service = new KrakenBaseService(broker);
        KrakenMarketDataServiceRaw test = new KrakenMarketDataServiceRaw(broker);
        //test.getKrakenOHLC()
    }
}
