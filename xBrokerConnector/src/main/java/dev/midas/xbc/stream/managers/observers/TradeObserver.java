package dev.midas.xbc.stream.managers.observers;

import dev.midas.xbc.pubsub.Publisher;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.dto.marketdata.Trade;

/**
 *  For trades considers pipelining
 */
public class TradeObserver extends DisposableObserver<Trade> {

    private static final Logger LOG = LogManager.getLogger(TradeObserver.class.getName());

    private final Publisher publisher;

    public TradeObserver(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void onNext(@NonNull Trade trade) {
        publisher.publish(trade.toString());
        LOG.info("Trade: {}", trade);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LOG.info("Error in trade subscription", e);
    }

    @Override
    public void onComplete() {
        LOG.info("TradeObserver complete");
    }
}
