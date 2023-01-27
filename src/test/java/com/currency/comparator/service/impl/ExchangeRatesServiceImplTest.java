package com.currency.comparator.service.impl;

import com.currency.comparator.client.ExchangeRatesClient;
import com.currency.comparator.model.CurrencyRate;
import com.currency.comparator.model.exception.ValidateCurrencyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceImplTest {
    private static final BigDecimal EUR_RATE = BigDecimal.valueOf(5.5);
    private static final String API_KEY = "%$fhFv&";
    private static final String BASE_CURRENCY = "USD";

    @Mock
    ExchangeRatesClient feignClient;

    @InjectMocks
    ExchangeRatesServiceImpl service;

    @BeforeEach()
    void init() {
        ReflectionTestUtils.setField(service, "apiId", API_KEY);
        ReflectionTestUtils.setField(service, "baseCurrency", BASE_CURRENCY);
    }

    @Test
    void getTodayRate() {
        when(feignClient.getAllCurrency(API_KEY)).thenReturn(getCurrencyRate());

        BigDecimal actual = service.getTodayRate("EUR");

        assertEquals(EUR_RATE, actual);
        verify(feignClient).getAllCurrency(API_KEY);
    }

    @Test
    void getTodayBaseRate() {
        ReflectionTestUtils.setField(service, "baseCurrency", "EUR");

        when(feignClient.getAllCurrency(API_KEY, "EUR")).thenReturn(getCurrencyRate());

        BigDecimal actual = service.getTodayRate("EUR");

        assertEquals(EUR_RATE, actual);
        verify(feignClient).getAllCurrency(API_KEY, "EUR");
    }

    @Test
    void getPrevDateRate() {
        String date = LocalDate.now().minusDays(2).toString();

        when(feignClient.getHistoricalRates(date, API_KEY)).thenReturn(getCurrencyRate());

        BigDecimal actual = service.getPrevDateRate("EUR");

        assertEquals(EUR_RATE, actual);
        verify(feignClient).getHistoricalRates(date, API_KEY);
    }

    @Test
    void getPrevDateBaseRate() {
        String date = LocalDate.now().minusDays(2).toString();
        ReflectionTestUtils.setField(service, "baseCurrency", "EUR");

        when(feignClient.getHistoricalRates(date, API_KEY, "EUR")).thenReturn(getCurrencyRate());

        BigDecimal actual = service.getPrevDateRate("EUR");

        assertEquals(EUR_RATE, actual);
        verify(feignClient).getHistoricalRates(date, API_KEY, "EUR");
    }

    @Test
    void testValidateCurrencyToday() {
        ValidateCurrencyException ex = assertThrows(ValidateCurrencyException.class, () -> service.getTodayRate("GJDHY"));

        assertEquals("Provided argument is not valid currency", ex.getMessage());
    }

    @Test
    void testValidateCurrencyDateRate() {
        ValidateCurrencyException ex = assertThrows(ValidateCurrencyException.class, () -> service.getPrevDateRate("GJDHY"));

        assertEquals("Provided argument is not valid currency", ex.getMessage());
    }

    private CurrencyRate getCurrencyRate() {
        CurrencyRate currencyRate = new CurrencyRate();
        Map<String, Double> map = new HashMap<>();
        map.put("EUR", 5.5);
        currencyRate.setRates(map);
        return currencyRate;
    }
}