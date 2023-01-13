package com.currency.comparator.controller;

import com.currency.comparator.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/v1/currency/compare/")
@RequiredArgsConstructor
public class Controller {
    private final CurrencyService currencyService;

    @GetMapping(value = "/{currency}", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getResult(@PathVariable String currency) {
        return currencyService.compareRate(currency);
    }

}
