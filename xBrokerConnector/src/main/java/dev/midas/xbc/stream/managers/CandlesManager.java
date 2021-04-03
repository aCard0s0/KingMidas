package dev.midas.xbc.stream.managers;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLC;
import org.knowm.xchange.kraken.service.KrakenBaseService;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class CandlesManager {

    public void testing() throws IOException {

        Exchange broker = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

        MarketDataService marketDataService = broker.getMarketDataService();
        Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

        KrakenBaseService service = new KrakenBaseService(broker);
        KrakenMarketDataServiceRaw test = new KrakenMarketDataServiceRaw(broker);
        //test.getKrakenOHLC()
    }
}

/*
Candle candle = new Candle(1325379600L,new BigDecimal("1"),new BigDecimal("1"),
                new BigDecimal("1"),new BigDecimal("1"),new BigDecimal("1"));
 */