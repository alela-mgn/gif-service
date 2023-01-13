package com.currency.comparator.service.impl;

import com.currency.comparator.client.GifClient;
import com.currency.comparator.service.GifService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class GifServiceImpl implements GifService {
    private final GifClient gifClient;

    @Value("${gif.apiKey}")
    private String apiKey;

    @Value("${gif.rich}")
    private String richTag;

    @Value("${gif.broke}")
    private String brokeTag;

    @Override
    public byte[] getRichGif(){
        String gifData = gifClient.getRandomGif(apiKey, richTag);
        String gifUrl = parseUrlGif(gifData);
        return gifClient.getBytes(URI.create(gifUrl));
    }

    @Override
    public byte[] getBrokeGif(){
        String gifData = gifClient.getRandomGif(apiKey, brokeTag);
        String gifUrl = parseUrlGif(gifData);
        return gifClient.getBytes(URI.create(gifUrl));
    }

    private String parseUrlGif(String gifData) {
        JSONObject response = new JSONObject(gifData);
        return response.getJSONObject("data")
                .getJSONObject("images")
                .getJSONObject("original")
                .getString("url");
    }
}

