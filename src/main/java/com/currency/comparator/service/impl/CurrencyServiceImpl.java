package com.currency.comparator.service.impl;

import com.currency.comparator.model.exception.RateValidationException;
import com.currency.comparator.service.CurrencyService;
import com.currency.comparator.service.ExchangeRatesService;
import com.currency.comparator.service.GifService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final ExchangeRatesService exchangeRatesService;
    private final GifService gifService;

    @Override
    public byte[] compareRate(String userCurrency) {
        BigDecimal prevRate = exchangeRatesService.getPrevDateRate(userCurrency);
        BigDecimal todayRate = exchangeRatesService.getTodayRate(userCurrency);
        validationRate(prevRate, todayRate);

        return todayRate.compareTo(prevRate) > 0
                ? gifService.getRichGif() : gifService.getBrokeGif();
    }

    private void validationRate(BigDecimal prevRate, BigDecimal todayRate) {
        if (prevRate == null || todayRate == null) {
            throw new RateValidationException("Exchange rate data was not received correctly");
        }
    }

}
