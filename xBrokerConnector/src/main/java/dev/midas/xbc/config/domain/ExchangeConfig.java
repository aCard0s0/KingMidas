package dev.midas.xbc.config.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

public class ExchangeConfig {

    public static class Builder {
        private String exchangeId;
        private String name;
        private String xbcCode;
        private List<MarketConfig> markets;

        public Builder(String exchangeId) {
            this.exchangeId = exchangeId;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setXbcCode(String xbcCode) {
            this.xbcCode = xbcCode;
            return this;
        }

        public Builder setMarkets(List<MarketConfig> markets) {
            this.markets = markets;
            return this;
        }

        public ExchangeConfig build() {
            ExchangeConfig config = new ExchangeConfig(this.exchangeId);
            config.name = this.name;
            config.xbcCode = this.xbcCode;
            config.markets = this.markets;
            return config;
        }
    }

    private String exchangeId;
    private String name;
    private String xbcCode;
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

    public String getXbcCode() {
        return xbcCode;
    }

    public void setXbcCode(String xbcCode) {
        this.xbcCode = xbcCode;
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
                Objects.equal(xbcCode, that.xbcCode) &&
                Objects.equal(markets, that.markets);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, xbcCode, markets);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeId", exchangeId)
                .add("name", name)
                .add("xbcCode", xbcCode)
                .add("markets", markets)
                .toString();
    }
}
