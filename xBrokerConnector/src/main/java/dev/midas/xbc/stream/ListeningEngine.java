package dev.midas.xbc.stream;

import dev.midas.xbc.config.ConfigManager;
import dev.midas.xbc.config.domain.ExchangeConfig;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.kraken.KrakenStreamingExchange;
import io.reactivex.disposables.Disposable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class ListeningEngine {

    private static final Logger LOG = LogManager.getLogger();

    private ConfigManager configurator;
    private DisposableFactory subscriptionFactory;

    public ListeningEngine(ConfigManager configurator, DisposableFactory subscriptionFacotry) {
        this.configurator = configurator;
        this.subscriptionFactory = subscriptionFacotry;
    }

    public void startDefaultListeners() {
        ExchangeConfig[] localConfig = configurator.loadExchangeConfig();

        Arrays.stream(localConfig).forEach(exchange -> {
            // TODO: create class deserializer
            StreamingExchange stream = subscriptionFactory.subscribeExchange(KrakenStreamingExchange.class);

            exchange.getMarkets().stream().forEach(market -> {
                subscriptionFactory.subscribeMarket(
                        stream,
                        CurrencyPairDeserializer.getCurrencyPairFromString(market.getMarketId()));
            });
        });
    }

    public void startListener(Class xStreamingExchange, CurrencyPair pair) {
        StreamingExchange stream = subscriptionFactory.getStreamFromClass(xStreamingExchange);

        subscriptionFactory.subscribeMarket(stream, pair);
    }

    public void stopAllListeners() {
        subscriptionFactory.unsubscribeAll();
    }

    public void stopListener(Class xStremingExchange, CurrencyPair pair) {
        StreamingExchange stream = subscriptionFactory.getStreamFromClass(xStremingExchange);
        Disposable subscription = subscriptionFactory.getDisposableFromPair(stream, pair);

        subscriptionFactory.unsubscribeMarket(stream, subscription);
    }

}
