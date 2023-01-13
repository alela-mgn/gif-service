package com.currency.comparator.service.impl;

import com.currency.comparator.model.exception.RateValidationException;
import com.currency.comparator.service.ExchangeRatesService;
import com.currency.comparator.service.GifService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    @Mock
    ExchangeRatesService exchangeRatesService;
    @Mock
    GifService gifService;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    byte[] richGif = new byte[]{10, 25, 30};
    byte[] brokeGif = new byte[]{55};

    @Test
    void testServiceReturningRichGif() {
        Mockito.when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(5.5));
        Mockito.when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        Mockito.when(gifService.getRichGif()).thenReturn(richGif);

        byte[] actual = currencyService.compareRate("EUR");
        assertEquals(actual, richGif);
        Mockito.verify(gifService).getRichGif();
    }

    @Test
    void testServiceReturningBrokeGif() {
        Mockito.when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(50.5));
        Mockito.when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        Mockito.when(gifService.getBrokeGif()).thenReturn(brokeGif);

        byte[] actual = currencyService.compareRate("EUR");
        assertEquals(actual, brokeGif);
        Mockito.verify(gifService).getBrokeGif();
    }

    @Test
    void testRateValidation() {
        Mockito.when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(null);
        Mockito.when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        RateValidationException ex = assertThrows(RateValidationException.class, () -> currencyService.compareRate("USD"));
        assertEquals("Exchange rate data was not received correctly", ex.getMessage());
    }
}