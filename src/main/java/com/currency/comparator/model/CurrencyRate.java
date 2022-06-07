package com.currency.comparator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class CurrencyRate {
    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, Double> rates;
}
