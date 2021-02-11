package dev.midas.xbc.stream.managers;

import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.converters.MarketConverter;
import dev.midas.xbc.stream.wrappers.Exchange;
import dev.midas.xbc.stream.wrappers.Market;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This factory currently creates and destroys markets streams.
 */
@Component
public class StreamManager extends HashMap<StreamingExchange, List<Market>>{

    private static final Logger LOG = LogManager.getLogger(StreamManager.class.getName());

    private MarketConverter converter;

    public StreamManager() {
        this.converter = new MarketConverter();
    }

    public void subscribeMarket(StreamingExchange stream, MarketConfig config) {
        if (!this.containsKey(stream)) {
            this.put(stream, Collections.emptyList());
        }

        StreamingMarketDataService streamService = stream.getStreamingMarketDataService();

        Market market = converter.convert(config);

        if (config.isListeningTrades()) {
            streamService.getTrades(market.getPair())
                    .subscribe(trade -> {
                                LOG.info("Trade: {}", trade);
                                // next-step: publish to rabbitMQ
                            },
                            throwable -> LOG.info("Error in trade subscription", throwable));
        }
        if (config.isListeningTicker()) {
            streamService.getTicker(market.getPair())
                    .subscribe();
        }
        if (config.isListeningOrderBook()) {
            streamService.getOrderBook(market.getPair())
                    .subscribe();
        }
    }

    public void subscribeMarkets(StreamingExchange stream, List<MarketConfig> marketConfigs) {
        if (!this.containsKey(stream)) {
            this.put(stream, Collections.emptyList());
        }

        StreamingMarketDataService streamService = stream.getStreamingMarketDataService();

        Market market;  // converter logic?
        for(MarketConfig config: marketConfigs) {
            market = converter.convert(config);

            if (config.isListeningTrades()) {
                streamService.getTrades(market.getPair())
                        .subscribe(trade -> {
                                    LOG.info("Trade: {}", trade);
                                    // next-step: publish to rabbitMQ
                                },
                                throwable -> LOG.info("Error in trade subscription", throwable));
            }
            if (config.isListeningTicker()) {
                streamService.getTicker(market.getPair())
                        .subscribe();
            }
            if (config.isListeningOrderBook()) {
                streamService.getOrderBook(market.getPair())
                        .subscribe();
            }
        }

        // List<Market> markets = exchange.getMarkets().stream().map(converter::convert).collect(Collectors.toList());
        // merge values

    }

    public void unsubscribeMarkets(StreamingExchange stream, List<MarketConfig> marketConfigs) {
        
    }
    
    public void unsubscribePair(StreamingExchange stream, CurrencyPair pair) {

        if (!containsKey(stream)) {
            Optional<Market> market = get(stream).stream().filter(m -> Objects.equals(m.getPair(), pair)).findFirst();

            if(market.isPresent()) {
                market.get().getTradeListener().dispose();
                //market.get().getTickerListener().dispose();
                market.get().getOrderBookListener().dispose();
                // subtract two lists
                this.get(stream).remove(market);
                LOG.debug(String.format("Exchange %s unsubscribe market %s", stream.getExchangeSpecification().getExchangeName(), pair));
            }
        } else {
            LOG.info(String.format("Exchange %s not subscribed", stream.getExchangeSpecification().getExchangeName()));
        }
    }

    public List<Market> getMarketsForPair(CurrencyPair pair) {
        return new ArrayList<>();
    }

    public void unsubscribeAll() {
    }
}
