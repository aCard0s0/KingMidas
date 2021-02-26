package dev.midas.xbc.converters;

import com.google.common.base.Converter;
import dev.midas.xbc.config.domain.ExchangeConfig;
import dev.midas.xbc.stream.wrappers.Exchange;
import dev.midas.xbc.stream.wrappers.ExchangeMapper;
import org.springframework.stereotype.Component;

@Component
public class ExchangeConverter extends Converter<ExchangeConfig, Exchange> {

    @Override
    protected Exchange doForward(ExchangeConfig exchangeConfig) {
        return new Exchange.Builder()
                .setExchangeId(ExchangeMapper.valueOf(exchangeConfig.getExchangeId().toUpperCase()))
                .setName(exchangeConfig.getName())
                .setMarkets(exchangeConfig.getMarkets())
                .build();
    }

    @Override
    protected ExchangeConfig doBackward(Exchange exchange) {
        return null;
    }

    // ref: https://www.javaguides.net/2018/08/converter-design-pattern-in-java.html
}
