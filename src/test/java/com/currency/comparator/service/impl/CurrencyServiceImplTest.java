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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {
    private static final byte[] RICH_GIF = new byte[]{10, 25, 30};
    private static final byte[] BROKE_GIF = new byte[]{55};

    @Mock
    ExchangeRatesService exchangeRatesService;

    @Mock
    GifService gifService;

    @InjectMocks
    CurrencyServiceImpl currencyService;

    @Test
    void testServiceReturningRichGif() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(5.5));
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        when(gifService.getRichGif()).thenReturn(RICH_GIF);

        byte[] actual = currencyService.compareRate("EUR");

        assertEquals(actual, RICH_GIF);
        verify(gifService).getRichGif();
    }

    @Test
    void testServiceReturningBrokeGif() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(50.5));
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));
        when(gifService.getBrokeGif()).thenReturn(BROKE_GIF);

        byte[] actual = currencyService.compareRate("EUR");

        assertEquals(actual, BROKE_GIF);
        verify(gifService).getBrokeGif();
    }

    @Test
    void testRateValidationDateRateNull() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(null);
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(10.5));

        RateValidationException ex = assertThrows(RateValidationException.class, () -> currencyService.compareRate("USD"));

        assertEquals("Exchange rate data was not received correctly", ex.getMessage());
    }

    @Test
    void testRateValidationTodayRateNull() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(BigDecimal.valueOf(5.5));
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(null);

        RateValidationException ex = assertThrows(RateValidationException.class, () -> currencyService.compareRate("USD"));

        assertEquals("Exchange rate data was not received correctly", ex.getMessage());
    }

    @Test
    void testRateValidationRateNull() {
        when(exchangeRatesService.getPrevDateRate(Mockito.anyString())).thenReturn(null);
        when(exchangeRatesService.getTodayRate(Mockito.anyString())).thenReturn(null);

        RateValidationException ex = assertThrows(RateValidationException.class, () -> currencyService.compareRate("USD"));

        assertEquals("Exchange rate data was not received correctly", ex.getMessage());
    }
}