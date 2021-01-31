package dev.midas.xbc.stream;

import com.google.common.collect.FluentIterable;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  This factory currently creates and destroys markets streams.
 */
@Component
public class DisposableFactory {

    private static final Logger LOG = LogManager.getLogger();

    private Map<StreamingExchange, List<Disposable>> activeExchangeAndMarkets;

    public DisposableFactory() {
        activeExchangeAndMarkets = new HashMap<>();
    }

    public StreamingExchange subscribeExchange(Class T) {
        StreamingExchange stream = StreamingExchangeFactory.INSTANCE.createExchange(T);
        stream.connect().blockingAwait();

        activeExchangeAndMarkets.put(stream, Collections.emptyList());
        return stream;
    }

    public Disposable subscribeMarket(StreamingExchange stream, CurrencyPair pair) {

        Disposable newSubscription = stream.getStreamingMarketDataService()
                .getTrades(pair)
                .subscribe(
                        trade -> {
                            LOG.info("Trade: {}", trade);
                            // next-step: publish to rabbitMQ
                        },
                        throwable -> LOG.info("Error in trade subscription", throwable));

        activeExchangeAndMarkets.get(stream).add(newSubscription);
        return newSubscription;
    }

    /**
     *  ATTENTION: All stream will stop
     */
    public void unsubscribeAll() {
        activeExchangeAndMarkets.keySet().stream().forEach(this::unsubscribeExchange);
    }

    /**
     *  Unsubscribing from an Exchange also imply unsubscribe from correspondent markets
     * @param stream
     */
    public void unsubscribeExchange(StreamingExchange stream) {
        activeExchangeAndMarkets.get(stream).forEach(Disposable::dispose);
        stream.disconnect().blockingAwait();
        activeExchangeAndMarkets.remove(stream);

        LOG.info(() -> String.format(
                "Unsubscribed from exchange=%s and all related markets", stream.getExchangeSpecification().getExchangeName()));
    }

    public void unsubscribeMarket(StreamingExchange stream, Disposable subscription) {
        Optional<Disposable> first = activeExchangeAndMarkets.get(stream)
                .stream().filter(marketSub -> marketSub == subscription).findFirst();

        if (first.isPresent()) {
            subscription.dispose();
            activeExchangeAndMarkets.get(stream).remove(subscription);

            LOG.info(() -> String.format("market=%s removed from exchange=%s",
                    //subscription. ,
                    stream.getExchangeSpecification().getExchangeName()));

        } else {
            LOG.info(() -> String.format("market=%s not found in exchange=%s",
                    //subscription. ,
                    stream.getExchangeSpecification().getExchangeName()));
        }
    }

    public StreamingExchange getStreamFromClass(Class xStreamingExchange) {
        // com.google.common.base.Optional first = FluentIterable.from(activeExchangeAndMarkets.keySet()).filter(xStreamingExchange).first();

        List<StreamingExchange> collect = activeExchangeAndMarkets.keySet().stream()
                .filter(streamingExchange -> streamingExchange.getClass() == xStreamingExchange)
                .collect(Collectors.toList());

        return collect.get(0); // TODO: remove HOTFIX
    }

    public Disposable getDisposableFromPair(StreamingExchange stream, CurrencyPair pair) {
        activeExchangeAndMarkets.get(stream).stream().forEach(x -> System.out.println(x));
        return Disposables.empty();
    }
}
