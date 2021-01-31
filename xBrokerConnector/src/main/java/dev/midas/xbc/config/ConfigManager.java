package dev.midas.xbc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.midas.xbc.config.domain.ExchangeConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ConfigManager {

    private static final Logger LOG = LogManager.getLogger();

    public ConfigManager() {}

    public ExchangeConfig[] loadExchangeConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        ExchangeConfig[] configs = null;

        try {
            configs = mapper.readValue(new File("xBrokerConnector/src/main/resources/exchange.yaml"), ExchangeConfig[].class);
            System.out.println(configs);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return configs;
    }

}
