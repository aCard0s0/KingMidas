package dev.midas.xbc.config.domain;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class MarketConfig {

    public static class Builder {
        private String marketId;
        private String name;
        private boolean isListeningTrades;
        private boolean isListeningOrderBook;

        public Builder(String marketId) {
            this.marketId = marketId;
        }

        public Builder setMarketId(String marketId) {
            this.marketId = marketId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setListeningTrades(boolean listeningTrades) {
            this.isListeningTrades = listeningTrades;
            return this;
        }

        public Builder setListeningOrderBook(boolean listeningOrderBook) {
            this.isListeningOrderBook = listeningOrderBook;
            return this;
        }

        public MarketConfig build() {
            MarketConfig config = new MarketConfig(this.marketId);
            config.name = this.name;
            config.isListeningTrades = this.isListeningTrades;
            config.isListeningOrderBook = this.isListeningOrderBook;
            return config;
        }
    }

    private String marketId;
    private String name;
    private boolean isListeningTrades;
    private boolean isListeningOrderBook;

    public MarketConfig() {}

    private MarketConfig(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isListeningTrades() {
        return isListeningTrades;
    }

    public void setListeningTrades(boolean listeningTrades) {
        isListeningTrades = listeningTrades;
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

        return Objects.equal(marketId, that.marketId) &&
                Objects.equal(name, that.name) &&
                Objects.equal(isListeningTrades, that.isListeningTrades) &&
                Objects.equal(isListeningOrderBook, that.isListeningOrderBook);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(marketId, name, isListeningTrades, isListeningOrderBook);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("marketId", marketId)
                .add("name", name)
                .add("isListeningTrades", isListeningTrades)
                .add("isListeningOrderBook", isListeningOrderBook)
                .toString();
    }
}
