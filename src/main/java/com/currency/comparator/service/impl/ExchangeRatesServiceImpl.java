package com.currency.comparator.service.impl;

import com.currency.comparator.client.ExchangeRatesClient;
import com.currency.comparator.model.exception.ValidateCurrencyException;
import com.currency.comparator.model.CurrencyRate;
import com.currency.comparator.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private final ExchangeRatesClient exchangeRatesClient;

    @Value(("${currency-exchange.currency}"))
    private String baseCurrency;

    @Value("${currency-exchange.apiId}")
    private String apiId;

    @Override
    public BigDecimal getTodayRate(String userCurrency) {
        validateCurrency(userCurrency);
        return baseCurrency.equalsIgnoreCase("USD")
                ? resolveCurrencyRate(exchangeRatesClient.getAllCurrency(apiId), userCurrency)
                : resolveCurrencyRate(exchangeRatesClient.getAllCurrency(apiId, baseCurrency), userCurrency);
    }

    @Override
    public BigDecimal getPrevDateRate(String userCurrency) {
        validateCurrency(userCurrency);
        String date = LocalDate.now().minusDays(2).toString();

        return baseCurrency.equalsIgnoreCase("USD")
                ? resolveCurrencyRate(exchangeRatesClient.getHistoricalRates(date, apiId), userCurrency)
                : resolveCurrencyRate(exchangeRatesClient.getHistoricalRates(date, apiId, baseCurrency), userCurrency);
    }

    private BigDecimal resolveCurrencyRate(CurrencyRate currencyRate, String userCurrency) {
        return BigDecimal.valueOf(currencyRate.getRates().get(userCurrency));
    }

    private void validateCurrency(String targetCurrency) {
        try {
            Currency.getInstance(targetCurrency);
        } catch (IllegalArgumentException e) {
            throw new ValidateCurrencyException("Provided argument is not valid currency", e);
        }
    }
}
