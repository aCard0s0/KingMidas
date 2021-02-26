package dev.midas.xbc.converters;

import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.stream.wrappers.Market;
import org.knowm.xchange.currency.CurrencyPair;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MarketConverterTest {

    private MarketConverter victim;

    @BeforeMethod
    public void setUp() {
        victim = new MarketConverter();
    }

    @Test(description = "")
    public void mustSucceedConvertingMarketConfig() {
        MarketConfig config = new MarketConfig.Builder()
                .setPair("BTCEUR")
                .setListeningTrades(true)
                .setListeningOrderBook(false)
                .build();

        Market market = victim.convert(config);
        Assert.assertEquals(market.getPair(), CurrencyPair.BTC_EUR);

    }
}
