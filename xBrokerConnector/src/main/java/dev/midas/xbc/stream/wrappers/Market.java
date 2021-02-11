package dev.midas.xbc.stream.wrappers;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.reactivex.disposables.Disposable;
import org.knowm.xchange.currency.CurrencyPair;

public class Market {

    public static class Builder {
        private CurrencyPair pair;
        private Disposable tradeListener;
        private Disposable orderBookListener;

        public Builder() {
        }


        public Builder setPair(CurrencyPair pair) {
            this.pair = pair;
            return this;
        }

        public Builder setTradeListener(Disposable tradeListener) {
            this.tradeListener = tradeListener;
            return this;
        }

        public Builder setOrderBookListener(Disposable orderBookListener) {
            this.orderBookListener = orderBookListener;
            return this;
        }

        public Market build() {
            Market market = new Market();
            market.pair = this.pair;
            market.tradeListener = this.tradeListener;
            market.orderBookListener = this.orderBookListener;
            return market;
        }
    }

    private CurrencyPair pair;
    private Disposable tradeListener;
    private Disposable tickerListener;
    private Disposable orderBookListener;

    private Market() {
    }

    public CurrencyPair getPair() {
        return pair;
    }

    public void setPair(CurrencyPair pair) {
        this.pair = pair;
    }

    public Disposable getTradeListener() {
        return tradeListener;
    }

    public void setTradeListener(Disposable tradeListener) {
        this.tradeListener = tradeListener;
    }

    public Disposable getOrderBookListener() {
        return orderBookListener;
    }

    public void setOrderBookListener(Disposable orderBookListener) {
        this.orderBookListener = orderBookListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Market that = (Market) o;

        return Objects.equal(pair, that.pair) &&
                Objects.equal(tradeListener, that.tradeListener) &&
                Objects.equal(orderBookListener, that.orderBookListener);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pair, tradeListener, orderBookListener);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pair", pair)
                .add("tradeListener", tradeListener)
                .add("orderBookListener", orderBookListener)
                .toString();
    }
}
