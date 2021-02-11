package dev.midas.xbc.stream;

import dev.midas.xbc.config.ConfigManager;
import dev.midas.xbc.config.domain.ExchangeConfig;
import dev.midas.xbc.converters.ExchangeConverter;
import dev.midas.xbc.stream.managers.ExchangeManager;
import dev.midas.xbc.stream.managers.StreamManager;
import dev.midas.xbc.stream.wrappers.Exchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ListeningEngine {

    private static final Logger LOG = LogManager.getLogger(StreamManager.class.getName());

    private ConfigManager configurator;
    private ExchangeManager brokers;
    private ExchangeConverter converter;
    private StreamManager streams;

    public ListeningEngine(ConfigManager configurator,
                           ExchangeConverter converter,
                           ExchangeManager brokers,
                           StreamManager streams) {
        this.configurator = configurator;
        this.converter = converter;
        this.brokers = brokers;
        this.streams = streams;
    }

    public void startDefaultListeners() {
        List<ExchangeConfig> localConfig = configurator.loadDefaultExchangeConfig();

        for (ExchangeConfig exchangeConfig : localConfig) {
            startListener(exchangeConfig);
        }
        LOG.info(() -> "Default configuration started.");
    }

    public void startListener(ExchangeConfig config) {
        this.startListener(converter.convert(config));
    }

    public void startListener(Exchange exchange) {
        StreamingExchange stream = brokers.connectExchange(exchange);
        streams.subscribeMarkets(stream, exchange.getMarkets());
    }

    public void stopListener(ExchangeConfig config) {
        this.stopListener(converter.convert(config));
    }

    public void stopListener(Exchange exchange) {
        StreamingExchange stream = brokers.disconnectExchange(exchange);
        streams.unsubscribeMarkets(stream, exchange.getMarkets());
    }

    public void stopAllListeners() {
        brokers.disconnectAll();
        streams.unsubscribeAll();
    }
}
