package dev.midas.xbc.converters;

import com.google.common.base.Converter;
import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.stream.wrappers.Market;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class MarketConverter extends Converter<MarketConfig, Market>  {

    @Override
    protected Market doForward(MarketConfig config) {
        return new Market.Builder()
                .setPair(CurrencyPairDeserializer.getCurrencyPairFromString(config.getPair()))
                //.setListeningTrades(config.isListeningTrades())
                .build();
    }

    @Override
    protected MarketConfig doBackward(Market market) {
        return null;
    }

}
