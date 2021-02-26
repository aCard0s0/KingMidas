package dev.midas.xbc.converters;

import com.google.common.base.Converter;
import dev.midas.xbc.config.domain.MarketConfig;
import dev.midas.xbc.stream.wrappers.Market;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.springframework.stereotype.Component;

@Component
public class MarketConverter extends Converter<MarketConfig, Market>  {

    @Override
    protected Market doForward(MarketConfig config) {
        CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(config.getPair());

        return new Market.Builder()
                .setPair(pair)
                // .setListeningTrades(config.isListeningTrades())
                .build();
    }

    @Override
    protected MarketConfig doBackward(Market market) {
        return null;
    }

}
