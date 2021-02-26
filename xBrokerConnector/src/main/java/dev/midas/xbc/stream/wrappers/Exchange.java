package dev.midas.xbc.stream.wrappers;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import dev.midas.xbc.config.domain.MarketConfig;

import java.util.List;

public class Exchange {

    public static class Builder {
        private ExchangeMapper exchangeId;
        private String name;
        private List<MarketConfig> markets;

        public Builder() {
        }

        public Exchange.Builder setExchangeId(ExchangeMapper exchangeId) {
            this.exchangeId = exchangeId;
            return this;
        }

        public Exchange.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Exchange.Builder setMarkets(List<MarketConfig> markets) {
            this.markets = markets;
            return this;
        }

        public Exchange build() {
            Exchange exchange = new Exchange();
            exchange.exchangeMap = this.exchangeId;
            exchange.name = this.name;
            exchange.markets = this.markets;
            return exchange;
        }
    }

    private ExchangeMapper exchangeMap;
    private String name;
    private List<MarketConfig> markets;

    private Exchange() {
    }

    public ExchangeMapper getExchangeMap() {
        return exchangeMap;
    }

    public void setExchangeMap(ExchangeMapper exchangeMap) {
        this.exchangeMap = exchangeMap;
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

        Exchange that = (Exchange) o;

        return Objects.equal(exchangeMap, that.exchangeMap) &&
                Objects.equal(name, that.name) &&
                Objects.equal(markets, that.markets);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exchangeMap, name, markets);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeId", exchangeMap)
                .add("name", name)
                .add("markets", markets)
                .toString();
    }
}
