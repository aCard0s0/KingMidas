package dev.midas.xbc.stream.managers.observers;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.dto.marketdata.OrderBook;

public class OrderBookObserver extends DisposableObserver<OrderBook> {

    private static final Logger LOG = LogManager.getLogger(OrderBookObserver.class.getName());


    @Override
    public void onNext(@NonNull OrderBook order) {
        LOG.info("OrderBook: {}", order);
        // next-step: publish to redis
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LOG.info("Error in ticker subscription", e);
    }

    @Override
    public void onComplete() {
        LOG.info("OrderBookObserver complete");
    }
}
