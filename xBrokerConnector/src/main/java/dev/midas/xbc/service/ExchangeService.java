package dev.midas.xbc.service;

import dev.midas.xbc.stream.ListeningEngine;
import info.bitrich.xchangestream.kraken.KrakenStreamingExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class ExchangeService {

    private ListeningEngine engine;

    public ExchangeService(ListeningEngine engine) {
        this.engine = engine;
    }

    public void stopAllListeners() {
        this.engine.stopAllListeners();
    }

    public void startListener(String exchangeId, String marketId) {
        // Deserilaztion
        //this.engine.startListener(KrakenStreamingExchange.class, CurrencyPair.BTC_EUR);
    }

    public void stopListener(String exchangeId, String marketId) {
        // Deserilaztion
        //this.engine.stopListener(KrakenStreamingExchange.class, CurrencyPair.BTC_EUR);
    }

    public void stopListener(String exchangeId) {
        // Deserilaztion
        //this.engine.stopListener(KrakenStreamingExchange.class);
    }

    public static void testGetCurrentPrice() {
        Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class);
        MarketDataService marketDataService = bitstamp.getMarketDataService();
        Ticker ticker = null;
        try {
            ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ticker.toString());
    }
}
