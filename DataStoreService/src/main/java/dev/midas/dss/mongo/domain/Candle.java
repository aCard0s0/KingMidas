package dev.midas.dss.mongo.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "candle")
public class Candle {

    //private final String pattern = "yyyy-MM-dd HH:mm:ss.SSSZ";

    @Id
    @BsonProperty()
    @BsonId
    private ObjectId id;
    private Long timestamp;
    private Date date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;

    public Candle() {}

    public Candle(Long timestamp, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal volume) {
        this.timestamp = timestamp;
        this.date = new Date(this.timestamp * 1000);
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    /*public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }*/

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Candle that = (Candle) o;

        return Objects.equal(timestamp, that.timestamp) &&
                Objects.equal(open, that.open)  &&
                Objects.equal(high, that.high)  &&
                Objects.equal(low, that.low) &&
                Objects.equal(close, that.close)  &&
                Objects.equal(volume, that.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(timestamp, open, high, low, close);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("open", open)
                .add("high", high)
                .add("low", low)
                .add("close", close)
                .add("volume", volume)
                .toString();
    }
}
