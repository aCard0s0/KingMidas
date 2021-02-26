package dev.midas.xbc.stream.managers;

import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.stream.wrappers.Exchange;
import dev.midas.xbc.stream.wrappers.ExchangeMapper;
import info.bitrich.xchangestream.core.StreamingExchange;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class StreamManagerTest {

    private ExchangeManager exchanges;
    private StreamManager victim;

    @BeforeMethod
    public void setUp() {
        this.exchanges = new ExchangeManager();
        this.victim = new StreamManager();
    }

    @Test(description = "")
    public void shouldSubscribeMarket() {
        StreamingExchange stream = connectExchange();
        MarketConfig config = new MarketConfig.Builder()
                .setPair("BTCEUR")
                .setListeningTrades(true)
                .setListeningTicker(false)
                .setListeningOrderBook(false)
                .build();

        this.victim.subscribeMarket(stream, config);
        // How to test observables?
    }

    private StreamingExchange connectExchange() {
        Exchange testExchange = new Exchange.Builder()
                .setExchangeId(ExchangeMapper.KRAKEN)
                .build();

        return this.exchanges.connectExchange(testExchange);
    }
}
