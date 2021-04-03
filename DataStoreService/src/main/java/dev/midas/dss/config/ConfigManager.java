package dev.midas.dss.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.util.EnumResolver;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.midas.dss.config.domain.StorageConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class ConfigManager {

    private static final String DEFAULT_FILE = "storage.yaml";
    private static final Logger LOG = LogManager.getLogger(ConfigManager.class.getName());

    public ConfigManager() {}

    public List<StorageConfig> loadDefaultExchangeConfig() {
        return this.loadExchangeConfig(DEFAULT_FILE);
    }

    /**
     * @param path if empty or null default path is loaded.
     * @return
     */
    public List<StorageConfig> loadExchangeConfig(String path) {
        path = StringUtils.isNotEmpty(path) ? path : DEFAULT_FILE;

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        /*mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public boolean handleUnknownProperty(DeserializationContext ctxt, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {
                System.out.println("enum");
                return false;
            }
        });*/
        //mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        //mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        //mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);


        File file = new File(path);
        List<StorageConfig> configs = Collections.emptyList();

        try {
            file = new ClassPathResource(path).getFile();
            configs = mapper.readValue(file, new TypeReference<List<StorageConfig>>(){});

        } catch (FileNotFoundException e) {
            LOG.error(String.format("File %s not found", file.getName()));
            System.exit(1);

        } catch (IOException e) {
            LOG.error(String.format("Fail to load file %s", file.getName()));
            System.exit(1);
        }
        LOG.info(String.format("File %s successfully loaded", file.getName()));

        // TODO: check for not existing and repeated ids here
        return configs;
    }
}
