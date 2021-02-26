package dev.midas.xbc.config.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

public class ExchangeConfig {

    public static class Builder {
        private String exchangeId;
        private String name;
        private List<MarketConfig> markets;

        public Builder() { }

        public Builder setExchangeId(String exchangeId) {
            this.exchangeId = exchangeId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setMarkets(List<MarketConfig> markets) {
            this.markets = markets;
            return this;
        }

        public ExchangeConfig build() {
            ExchangeConfig config = new ExchangeConfig();
            config.exchangeId = this.exchangeId;
            config.name = this.name;
            config.markets = this.markets;
            return config;
        }
    }

    private String exchangeId;
    private String name;
    private List<MarketConfig> markets;

    private ExchangeConfig() {}

    private ExchangeConfig(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MarketConfig> getMarkets() {
        return markets;
    }

    public void setMarkets(List<MarketConfig> markets) {
        this.markets = markets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExchangeConfig that = (ExchangeConfig) o;

        return Objects.equal(exchangeId, that.exchangeId) &&
                Objects.equal(name, that.name) &&
                Objects.equal(markets, that.markets);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exchangeId, name, markets);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeId", exchangeId)
                .add("name", name)
                .add("markets", markets)
                .toString();
    }
}
