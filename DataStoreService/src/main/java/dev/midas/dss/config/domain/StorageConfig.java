package dev.midas.dss.config.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

public class StorageConfig {

    public static class Builder {
        private String exchangeId;
        private List<CandlesConfig> pairs;

        public Builder() {
        }

        public Builder setExchangeId(String exchangeId) {
            this.exchangeId = exchangeId;
            return this;
        }

        public Builder setMarkets(List<CandlesConfig> pairs) {
            this.pairs = pairs;
            return this;
        }

        public StorageConfig build() {
            StorageConfig config = new StorageConfig();
            config.exchangeId = this.exchangeId;
            config.pairs = this.pairs;
            return config;
        }
    }

    private String exchangeId;
    private List<CandlesConfig> pairs;

    private StorageConfig() {
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public List<CandlesConfig> getPairs() {
        return pairs;
    }

    public void setPairs(List<CandlesConfig> pairs) {
        this.pairs = pairs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StorageConfig that = (StorageConfig) o;

        return Objects.equal(exchangeId, that.exchangeId) &&
                Objects.equal(pairs, that.pairs);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exchangeId, pairs);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exchangeId", exchangeId)
                .add("pairs", pairs)
                .toString();
    }
}
