package dev.midas.xbc.stream.managers;

import com.google.common.collect.Lists;
import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.converters.MarketConverter;
import dev.midas.xbc.pubsub.Publisher;
import dev.midas.xbc.stream.managers.observers.OrderBookObserver;
import dev.midas.xbc.stream.managers.observers.TickerObserver;
import dev.midas.xbc.stream.managers.observers.TradeObserver;
import dev.midas.xbc.stream.wrappers.Market;
import info.bitrich.xchangestream.core.StreamingExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.*;

/**
 *  This manager ensures that only exist one unique Market in each Exchange House.
 */
@Component
public class StreamManager extends HashMap<StreamingExchange, List<Market>> {

    private static final Logger LOG = LogManager.getLogger(StreamManager.class.getName());

    private final MarketConverter converter;
    private final Publisher publisher;

    public StreamManager() {
        this.converter = new MarketConverter();
        this.publisher = new Publisher();   // Hot fix
    }

    public void subscribeMarket(StreamingExchange stream, List<MarketConfig> configs) {
        for (MarketConfig config : configs) this.subscribeMarket(stream, config);
    }

    public Market subscribeMarket(StreamingExchange stream, MarketConfig config) {
        if (!this.containsKey(stream)) {
            this.put(stream, Lists.newArrayList());
        }

        Market market = converter.convert(config);
        assert market != null;  // what a hell?

        if (config.isListeningTrades()) {
            market.setTradeListener(
                stream.getStreamingMarketDataService().getTrades(market.getPair()).subscribeWith(new TradeObserver(publisher))
            );
        }
        if (config.isListeningTicker()) {
            market.setTradeListener(
                stream.getStreamingMarketDataService().getTicker(market.getPair()).subscribeWith(new TickerObserver())
            );
        }
        if (config.isListeningOrderBook()) {
            market.setTradeListener(
                stream.getStreamingMarketDataService().getOrderBook(market.getPair()).subscribeWith(new OrderBookObserver())
            );
        }

        this.get(stream).add(market);
        LOG.debug(String.format("Market %s subscribed in Exchange %s", market.getPair(), stream.getExchangeSpecification().getExchangeName()));
        return market;
    }

    public void unsubscribeMarket(StreamingExchange stream, List<MarketConfig> configs) {
        for (MarketConfig config : configs) this.unsubscribeMarket(stream, config);
    }

    public void unsubscribeMarket(StreamingExchange stream, MarketConfig config) {
        Market market = converter.convert(config);
        assert market != null;  // what a hell?

        this.unsubscribeMarket(stream, market.getPair());
    }

    public void unsubscribeMarket(StreamingExchange stream, CurrencyPair pair) {
        if (!this.containsKey(stream)) {
            throw new NotFoundException(String.format("Exchange %s not found", stream.getExchangeSpecification().getExchangeName()));
        }

        List<Market> markets = this.get(stream);
        Optional<Market> market = markets.stream().filter(m -> Objects.equals(m.getPair(), pair)).findFirst();

        if(market.isPresent()) {
            market.get().disposeAll();
            markets.remove(market.get());
            LOG.debug(String.format("Market %s unsubscribed in Exchange %s", pair, stream.getExchangeSpecification().getExchangeName()));

        } else {
            throw new NotFoundException(String.format("Market %s not found in Exchange %s", pair, stream.getExchangeSpecification().getExchangeName()));
        }
    }

    public void unsubscribeAll() {
        for (List<Market> markets : this.values()) {
            markets.forEach(Market::disposeAll);
        }
    }

}
