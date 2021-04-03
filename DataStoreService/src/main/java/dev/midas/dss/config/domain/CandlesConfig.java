package dev.midas.dss.config.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.knowm.xchange.currency.CurrencyPair;

public class CandlesConfig {

    private String pair;
    private long beginning;
    private String[] steps;

    public CandlesConfig() {}

    @JsonCreator
    public CandlesConfig(String pair) {
        this.pair = pair;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public long getBeginning() {
        return beginning;
    }

    public void setBeginning(long beginning) {
        this.beginning = beginning;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandlesConfig that = (CandlesConfig) o;

        return Objects.equal(pair, that.pair) &&
                Objects.equal(beginning, that.beginning) &&
                Objects.equal(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pair, beginning, steps);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pair", pair)
                .add("beginning", beginning)
                .add("steps", steps)
                .toString();
    }
}
