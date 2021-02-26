package dev.midas.xbc.stream.managers;

import dev.midas.xbc.stream.wrappers.Exchange;
import dev.midas.xbc.stream.wrappers.ExchangeMapper;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.Optional;

/**
 *  This manager ensures that only exist one connection to one Exchange House.
 */
@Component
public class ExchangeManager extends HashMap<Exchange, StreamingExchange> {

    private static final Logger LOG = LogManager.getLogger(StreamManager.class.getName());

    public ExchangeManager() {
    }

    public StreamingExchange connectExchange(Exchange exchange) {
        if (this.containsKey(exchange)) {
            return this.get(exchange);
        }

        StreamingExchange brokerConnection = StreamingExchangeFactory.INSTANCE.createExchange(exchange.getExchangeMap().getxStreamingExchange());
        brokerConnection.connect().blockingAwait(); // thread safe?

        this.put(exchange, brokerConnection);

        LOG.debug(String.format("Exchange %s connected", exchange.getExchangeMap()));
        return brokerConnection;
    }

    public StreamingExchange disconnectExchange(Exchange exchange) {
        if (!this.containsKey(exchange)) {
            throw new NotFoundException(String.format("Exchange %s not found", exchange.getExchangeMap().getExchangeId()));
        }

        StreamingExchange removed = this.remove(exchange);
        disconnect(removed);
        LOG.debug(String.format("Exchange %s disconnected", exchange.getExchangeMap().getExchangeId()));
        return removed;
    }

    public void disconnectAll() {
        values().forEach(ExchangeManager::disconnect);
        this.clear();
        LOG.debug(() -> "All Exchanges disconnected");
    }

    public boolean isConnected(Exchange exchange){
        if (!containsKey(exchange)) {
            LOG.info(String.format("Exchange %s not subscribed", exchange.getExchangeMap().getExchangeId()));
            return false;
        }
        return true;
    }

    public boolean isConnected(ExchangeMapper enumId) {
        if (keySet().stream().anyMatch(exchange -> exchange.getExchangeMap().equals(enumId))) {
            return true;
        }
        LOG.info(String.format("Exchange %s not subscribed", enumId.getExchangeId()));
        return false;
    }

    public Exchange findExchangeByEnumId(ExchangeMapper enumId) {
        Optional<Exchange> optionalExchange = keySet().stream().filter(exchange -> exchange.getExchangeMap().equals(enumId)).findFirst();

        if (optionalExchange.isEmpty()) {
            throw new NotFoundException(enumId.getExchangeId() +" not found on ExchangeManager");
        }
        return optionalExchange.get();
    }

    public StreamingExchange get(ExchangeMapper enumId) {
        return this.get( this.findExchangeByEnumId(enumId) );
    }

    private static void disconnect(StreamingExchange broker) {
        broker.disconnect().blockingAwait(); // thread sage??
    }

}
