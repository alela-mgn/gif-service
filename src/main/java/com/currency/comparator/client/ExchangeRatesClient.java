package com.currency.comparator.client;

import com.currency.comparator.model.CurrencyRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ExchangeRates", url = "${currency-exchange.url}")
public interface ExchangeRatesClient {
    @GetMapping("/latest.json")
    CurrencyRate getAllCurrency(@RequestParam("app_id") String apiId, @RequestParam("base") String base);

    @GetMapping("/latest.json")
    CurrencyRate getAllCurrency(@RequestParam("app_id") String apiId);

    @GetMapping("/historical/{date}.json")
    CurrencyRate getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String apiId, @RequestParam("base") String base);

    @GetMapping("/historical/{date}.json")
    CurrencyRate getHistoricalRates(@PathVariable String date, @RequestParam("app_id") String apiId);
}
