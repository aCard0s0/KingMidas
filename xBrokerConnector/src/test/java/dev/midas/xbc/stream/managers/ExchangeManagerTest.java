package dev.midas.xbc.stream.managers;

import dev.midas.xbc.stream.managers.ExchangeManager;
import dev.midas.xbc.stream.wrappers.Exchange;
import dev.midas.xbc.stream.wrappers.ExchangeMapper;
import info.bitrich.xchangestream.core.StreamingExchange;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ExchangeManagerTest {

    private ExchangeManager victim;
    private Exchange exchange;

    @BeforeMethod
    public void setUp() {
        this.victim = new ExchangeManager();
    }

    @Test(description = "")
    public void shouldConnectAndDisconnectToExchange() {
        Exchange testExchange = new Exchange.Builder()
                .setExchangeId(ExchangeMapper.KRAKEN)
                .build();

        StreamingExchange stream = this.victim.connectExchange(testExchange);
        assertEquals(this.victim.connectExchange(testExchange), stream);

        assertEquals(this.victim.size(), 1);
        assertEquals(this.victim.findExchangeByEnumId(ExchangeMapper.KRAKEN), testExchange);

        assertEquals(this.victim.get(testExchange), stream);
        assertEquals(this.victim.get(ExchangeMapper.KRAKEN), stream);

        assertThrows(() -> this.victim.findExchangeByEnumId(ExchangeMapper.BITSTAMP));

        assertTrue(this.victim.isConnected(testExchange));
        assertTrue(this.victim.isConnected(ExchangeMapper.KRAKEN));
        assertFalse(this.victim.isConnected(new Exchange.Builder().setExchangeId(ExchangeMapper.BITSTAMP).build()));
        assertFalse(this.victim.isConnected(ExchangeMapper.BITSTAMP));

        assertThrows(() -> this.victim.disconnectExchange(ExchangeMapper.BITSTAMP));
        assertEquals(this.victim.disconnectExchange(ExchangeMapper.KRAKEN), stream);

        stream = this.victim.connectExchange(testExchange);
        assertEquals(this.victim.disconnectExchange(ExchangeMapper.KRAKEN), stream);
        assertEquals(this.victim.size(), 0);
    }

    @Test(description = "")
    public void shouldNotConnectToExchange() {
    }
}
