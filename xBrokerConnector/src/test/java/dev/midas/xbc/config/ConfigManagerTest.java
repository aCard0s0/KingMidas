package dev.midas.xbc.config;

import dev.midas.xbc.config.domain.ExchangeConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class ConfigManagerTest {

    private ConfigManager victim;

    @BeforeMethod
    public void setUp() {
        victim = new ConfigManager();
    }

    @Test(description = "Must succeed in loading the default configuration file")
    public void mustSucceedTheDefaultLoadingFile() {
        List<ExchangeConfig> exchangeConfigs = victim.loadDefaultExchangeConfig();

        assertTrue(exchangeConfigs.size() > 0);
    }

    @Test(description = "Must succeed in loading a custom path file well formatted")
    public void mustSucceedLoadFileWellFormatted() {
        List<ExchangeConfig> exchangeConfigs = victim.loadExchangeConfig("goodExchangeConfig.yaml");

        assertEquals(exchangeConfigs.size(), 2);
        assertEquals(exchangeConfigs.get(0).getExchangeId(), "kraken");
        assertEquals(exchangeConfigs.get(0).getName(), "TradeObserver Kraken Exchange");
        assertEquals(exchangeConfigs.get(0).getMarkets().size(), 1);
        assertEquals(exchangeConfigs.get(0).getMarkets().get(0).getPair(), "BTCEUR");
        assertTrue(exchangeConfigs.get(0).getMarkets().get(0).isListeningTrades());
        assertTrue(exchangeConfigs.get(0).getMarkets().get(0).isListeningTicker());
        assertTrue(exchangeConfigs.get(0).getMarkets().get(0).isListeningOrderBook());

        assertEquals(exchangeConfigs.get(1).getExchangeId(), "bitstamp");
        assertEquals(exchangeConfigs.get(1).getName(), "TradeObserver Bitstamp Exchange");
        assertEquals(exchangeConfigs.get(1).getMarkets().size(), 2);
        assertEquals(exchangeConfigs.get(1).getMarkets().get(0).getPair(), "ETHEUR");
        assertTrue(exchangeConfigs.get(1).getMarkets().get(0).isListeningTrades());
        assertTrue(exchangeConfigs.get(1).getMarkets().get(0).isListeningTicker());
        assertTrue(exchangeConfigs.get(1).getMarkets().get(0).isListeningOrderBook());
        assertEquals(exchangeConfigs.get(1).getMarkets().get(1).getPair(), "LTCEUR");
        assertFalse(exchangeConfigs.get(1).getMarkets().get(1).isListeningTrades());
        assertFalse(exchangeConfigs.get(1).getMarkets().get(1).isListeningTicker());
        assertFalse(exchangeConfigs.get(1).getMarkets().get(1).isListeningOrderBook());
    }

    // TODO: has bad configuration test. (e.g not existing and repeated ids)
    // TODO: test builder pattern on ExchangeConfig and MarketConfig
}
