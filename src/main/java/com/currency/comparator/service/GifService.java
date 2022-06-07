package com.currency.comparator.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GifService {
    byte[] getRichGif();

    byte[] getBrokeGif();
}
