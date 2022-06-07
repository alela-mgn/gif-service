package com.currency.comparator.service;

import java.math.BigDecimal;

public interface ExchangeRatesService {
    BigDecimal getTodayRate(String userCurrency);

    BigDecimal getPrevDateRate(String userCurrency);

}
