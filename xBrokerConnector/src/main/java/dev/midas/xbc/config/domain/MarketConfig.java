package dev.midas.xbc.config.domain;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class MarketConfig {

    public static class Builder {
        private String pair;
        private boolean isListeningTrades;
        private boolean isListeningTicker;
        private boolean isListeningOrderBook;

        public Builder() { }

        public Builder setPair(String pair) {
            this.pair = pair;
            return this;
        }

        public Builder setListeningTrades(boolean listeningTrades) {
            this.isListeningTrades = listeningTrades;
            return this;
        }

        public Builder setListeningTicker(boolean listeningTicker) {
            this.isListeningTicker = listeningTicker;
            return this;
        }

        public Builder setListeningOrderBook(boolean listeningOrderBook) {
            this.isListeningOrderBook = listeningOrderBook;
            return this;
        }

        public MarketConfig build() {
            MarketConfig config = new MarketConfig();
            config.pair = this.pair;
            config.isListeningTrades = this.isListeningTrades;
            config.isListeningTicker = this.isListeningTicker;
            config.isListeningOrderBook = this.isListeningOrderBook;
            return config;
        }
    }

    private String pair;
    private boolean isListeningTrades;
    private boolean isListeningTicker;
    private boolean isListeningOrderBook;

    public MarketConfig() {}

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public boolean isListeningTrades() {
        return isListeningTrades;
    }

    public void setListeningTrades(boolean listeningTrades) {
        isListeningTrades = listeningTrades;
    }

    public boolean isListeningTicker() {
        return isListeningTicker;
    }

    public void setListeningTicker(boolean listeningTicker) {
        this.isListeningTicker = listeningTicker;
    }

    public boolean isListeningOrderBook() {
        return isListeningOrderBook;
    }

    public void setListeningOrderBook(boolean listeningOrderBook) {
        isListeningOrderBook = listeningOrderBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarketConfig that = (MarketConfig) o;

        return Objects.equal(pair, that.pair) &&
                Objects.equal(isListeningTrades, that.isListeningTrades) &&
                Objects.equal(isListeningTicker, that.isListeningTicker) &&
                Objects.equal(isListeningOrderBook, that.isListeningOrderBook);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pair, isListeningTrades, isListeningTicker, isListeningOrderBook);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pair", pair)
                .add("isListeningTrades", isListeningTrades)
                .add("isListeningTicker", isListeningTicker)
                .add("isListeningOrderBook", isListeningOrderBook)
                .toString();
    }
}
