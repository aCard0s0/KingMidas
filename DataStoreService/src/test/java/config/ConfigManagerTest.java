package config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.midas.dss.config.ConfigManager;
import dev.midas.dss.config.domain.StorageConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Component
public class ConfigManagerTest {

    private ConfigManager victim;

    @BeforeMethod
    public void setUp() {
        victim = new ConfigManager();
    }

    @Test(description = "Must succeed in loading the default configuration file")
    public void mustSucceedTheDefaultLoadingFile() {
        List<StorageConfig> exchangeConfigs = victim.loadDefaultExchangeConfig();

        assertTrue(exchangeConfigs.size() > 0);
    }

    @Test(description = "Must succeed in loading a custom path file well formatted")
    public void mustSucceedLoadFileWellFormatted() {
        List<StorageConfig> exchangeConfigs = victim.loadExchangeConfig("goodStorageConfig.yaml");

        assertEquals(exchangeConfigs.size(), 2);

        assertEquals(exchangeConfigs.get(0).getPairs().size(), 2);
        assertEquals(exchangeConfigs.get(0).getPairs().get(0).getPair(), CurrencyPair.BTC_EUR);
        assertEquals(exchangeConfigs.get(0).getPairs().get(0).getSteps().length, 4);
        assertEquals(exchangeConfigs.get(0).getPairs().get(1).getPair(), "ETHEUR");
        assertEquals(exchangeConfigs.get(0).getPairs().get(1).getSteps().length, 2);

        assertEquals(exchangeConfigs.get(1).getPairs().size(), 1);
        assertEquals(exchangeConfigs.get(1).getPairs().get(0).getPair(), "BTCEUR");
        assertEquals(exchangeConfigs.get(1).getPairs().get(0).getSteps().length, 1);
    }
}
