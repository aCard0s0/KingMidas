package dev.midas.xbc.converters;

import dev.midas.xbc.config.domain.ExchangeConfig;
import dev.midas.xbc.stream.wrappers.Exchange;
import info.bitrich.xchangestream.kraken.KrakenStreamingExchange;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ExchangeConverterTest {

    private ExchangeConverter victim;

    @BeforeMethod
    public void setUp() {
        victim = new ExchangeConverter();
    }

    @Test(description = "")
    public void mustSucceedConvertingExchangeConfig() {
        ExchangeConfig testConfig = new ExchangeConfig.Builder()
                .setExchangeId("KRAKEN")
                .setName("TradeObserver name")
                .build();

        Exchange exchange = this.victim.convert(testConfig);

        assertEquals(exchange.getExchangeMap().getExchangeId(), testConfig.getExchangeId());
        assertEquals(exchange.getExchangeMap().getxStreamingExchange(), KrakenStreamingExchange.class);
    }
}
