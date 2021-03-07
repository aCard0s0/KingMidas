package dev.midas.dss.config.domain;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class PairConfig {

    public static class Builder {
        private String pair;
        private long beginning;
        private String[] steps;

        public Builder() { }

        public Builder setPair(String pair) {
            this.pair = pair;
            return this;
        }

        public Builder setBeginning(long beginning) {
            this.beginning = beginning;
            return this;
        }

        public Builder setSteps(String[] steps) {
            this.steps = steps;
            return this;
        }

        public PairConfig build() {
            PairConfig config = new PairConfig();
            config.pair = this.pair;
            config.beginning = this.beginning;
            config.steps = this.steps;
            return config;
        }
    }

    private String pair;
    private long beginning;
    private String[] steps;

    public PairConfig() {}

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

        PairConfig that = (PairConfig) o;

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
