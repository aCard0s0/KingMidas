package dev.midas.xbc.stream.wrappers;

import info.bitrich.xchangestream.bitstamp.v2.BitstampStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.kraken.KrakenStreamingExchange;

import java.util.HashMap;
import java.util.Map;

public enum ExchangeMapper {

    KRAKEN("KRAKEN", KrakenStreamingExchange.class),
    BITSTAMP("BITSTAMP", BitstampStreamingExchange.class);

    private final String exchangeId;
    private final Class<StreamingExchange> xStreamingExchange;

    private static final Map<String, ExchangeMapper> BY_ID = new HashMap<>();
    private static final Map<Class, ExchangeMapper> BY_CLASS = new HashMap<>();

    static {
        for(ExchangeMapper exchanges : values()) {
            BY_ID.put(exchanges.exchangeId, exchanges);
            BY_CLASS.put(exchanges.xStreamingExchange, exchanges);
        }
    }

    ExchangeMapper(String exchangeId, Class xStreamingExchange) {
        this.exchangeId = exchangeId;
        this.xStreamingExchange = xStreamingExchange;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public Class<StreamingExchange> getxStreamingExchange() {
        return xStreamingExchange;
    }

    public static ExchangeMapper valueOfId(String id) {
        return BY_ID.get(id);
    }

    public static ExchangeMapper valueOfClass(Class xclass) {
        return BY_CLASS.get(xclass);
    }
}
