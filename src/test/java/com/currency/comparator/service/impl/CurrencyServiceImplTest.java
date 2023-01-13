package com.currency.comparator.service.impl;

import com.currency.comparator.model.exception.RateValidationException;
import com.currency.comparator.service.ExchangeRatesService;
import com.currency.comparator.service.GifService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    @Mock
    ExchangeRatesService exchangeRatesService;

    @Mock
    GifService gifService;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    private final static byte[] richGif = new byte[]{10, 25, 30};
    private final static byte[] brokeGif = new byte[]{55};

    @Test
    void testServiceReturningRichGif() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(5.5));
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        when(gifService.getRichGif()).thenReturn(richGif);

        byte[] actual = currencyService.compareRate("EUR");

        assertEquals(actual, richGif);
        verify(gifService).getRichGif();
    }

    @Test
    void testServiceReturningBrokeGif() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(50.5));
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        when(gifService.getBrokeGif()).thenReturn(brokeGif);

        byte[] actual = currencyService.compareRate("EUR");

        assertEquals(actual, brokeGif);
        verify(gifService).getBrokeGif();
    }

    @Test
    void testRateValidation() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(null);
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));

        RateValidationException ex = Assertions.assertThrows(RateValidationException.class, () -> currencyService.compareRate("USD"));

        assertEquals("Exchange rate data was not received correctly", ex.getMessage());
    }
}