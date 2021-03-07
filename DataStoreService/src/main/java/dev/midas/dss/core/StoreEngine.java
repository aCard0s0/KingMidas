package dev.midas.dss.core;

import dev.midas.dss.config.ConfigManager;
import dev.midas.dss.config.domain.StorageConfig;
import dev.midas.dss.mongo.MongoManager;
import dev.midas.dss.mongo.domain.Candle;
import dev.midas.dss.mongo.repositories.CandleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class StoreEngine {

    private static final Logger LOG = LogManager.getLogger(StoreEngine.class.getName());

    private final ConfigManager configurator;
    private final MongoManager mongo;

    public StoreEngine(ConfigManager configurator, MongoManager mongo) {
        this.configurator = configurator;
        this.mongo = mongo;
    }

    public void startDefaultConfigs() {
        List<StorageConfig> localConfig = configurator.loadDefaultExchangeConfig();
        LOG.info(() -> "Default configuration loaded.");

        for (StorageConfig config : localConfig) {
            mongo.updateDB(config);
            //mongo.createCollection(config.getExchangeId());
        }
        
        this.listeningPairs();
    }

    private void listeningPairs() {
    }


}
