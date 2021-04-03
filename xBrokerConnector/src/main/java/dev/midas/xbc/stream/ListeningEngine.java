package dev.midas.xbc.stream;

import com.google.common.collect.Lists;
import dev.midas.xbc.config.ConfigManager;
import dev.midas.xbc.config.domain.ExchangeConfig;
import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.converters.ExchangeConverter;
import dev.midas.xbc.converters.MarketConverter;
import dev.midas.xbc.stream.managers.ExchangeManager;
import dev.midas.xbc.stream.managers.StreamManager;
import dev.midas.xbc.stream.wrappers.Exchange;
import dev.midas.xbc.stream.wrappers.ExchangeMapper;
import dev.midas.xbc.stream.wrappers.Market;
import info.bitrich.xchangestream.core.StreamingExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  This class work with Exchange or ExchangeConfig as well Market or MarketConfig
 *  It ensures that
 */
@Component
public class ListeningEngine {

    private static final Logger LOG = LogManager.getLogger(StreamManager.class.getName());

    private final ConfigManager configurator;
    private final ExchangeManager brokers;
    private final ExchangeConverter exchangeConverter;
    private final StreamManager streams;

    public ListeningEngine(ConfigManager configurator,
                           ExchangeConverter exchangeConverter,
                           ExchangeManager brokers,
                           StreamManager streams) {
        this.configurator = configurator;
        this.exchangeConverter = exchangeConverter;
        this.brokers = brokers;
        this.streams = streams;
    }

    public void startDefaultConfigs() {
        List<ExchangeConfig> localConfig = configurator.loadDefaultExchangeConfig();

        for (ExchangeConfig exchangeConfig : localConfig) {
            startListener(exchangeConfig);
        }
        LOG.info(() -> "Default configuration loaded.");
    }

    public void startListener(ExchangeConfig config) {
        this.startListener(exchangeConverter.convert(config));
    }

    public void stopListener(ExchangeConfig config) {
        this.stopListener(exchangeConverter.convert(config));
    }

    public void startListener(Exchange exchange) {
        StreamingExchange stream = brokers.connectExchange(exchange);
        streams.subscribeMarket(stream, exchange.getMarkets());
    }

    public void stopListener(Exchange exchange) {
        streams.unsubscribeMarket(
                brokers.disconnectExchange(exchange),
                exchange.getMarkets());
                //Lists.newArrayList(marketConverter.convertAll(exchange.getMarkets())));
    }

    public void startListener(ExchangeMapper enumId) {
        brokers.connectExchange(new Exchange.Builder().setExchangeId(enumId).build());
    }

    public void stopListener(ExchangeMapper enumId) {
        brokers.disconnectExchange(new Exchange.Builder().setExchangeId(enumId).build());
    }

    public void stopAllListeners() {
        brokers.disconnectAll();
        streams.unsubscribeAll();
    }

}
