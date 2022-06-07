package com.currency.comparator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URI;

@FeignClient(name = "gifClient", url = "${gif.url}")
public interface GifClient {
    @GetMapping("/random")
    String getRandomGif(@RequestParam String apiKey, @RequestParam String tag);

    @GetMapping(produces = MediaType.IMAGE_GIF_VALUE)
    byte[] getBytes(URI url);
}
