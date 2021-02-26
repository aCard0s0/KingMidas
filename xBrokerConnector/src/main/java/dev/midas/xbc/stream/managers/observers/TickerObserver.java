package dev.midas.xbc.stream.managers.observers;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.dto.marketdata.Ticker;

public class TickerObserver extends DisposableObserver<Ticker> {

    private static final Logger LOG = LogManager.getLogger(TickerObserver.class.getName());

    @Override
    public void onNext(@NonNull Ticker ticker) {
        LOG.info("Ticker: {}", ticker);
        // next-step: publish to redis
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LOG.info("Error in ticker subscription", e);
    }

    @Override
    public void onComplete() {
        LOG.info("TickerObserver complete");
    }
}