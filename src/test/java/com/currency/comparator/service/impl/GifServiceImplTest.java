package com.currency.comparator.service.impl;

import com.currency.comparator.client.GifClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GifServiceImplTest {
    private static final String API_KEY = "%$fhFv&";
    private static final String RICH_TAG = "rich";
    private static final String BROKE_TAG = "broke";
    private static final byte[] RICH_GIF = new byte[]{10, 25, 30};
    private static final byte[] BROKE_GIF = new byte[]{55};
    private static final String GIF_DATA = "{\n" +
            "  \"data\": {\n" +
            "    \"images\": {\n" +
            "      \"original\": {\n" +
            "        \"url\": \"https://media2.giphy.com\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    private static final String URL = "https://media2.giphy.com";

    @Mock
    GifClient gifClient;

    @InjectMocks
    GifServiceImpl service;

    @BeforeEach()
    void init() {
        ReflectionTestUtils.setField(service, "apiKey", API_KEY);
        ReflectionTestUtils.setField(service, "richTag", RICH_TAG);
        ReflectionTestUtils.setField(service, "brokeTag", BROKE_TAG);
    }

    @Test
    void getRichGif() {
        when(gifClient.getRandomGif(API_KEY, RICH_TAG)).thenReturn(GIF_DATA);
        when(gifClient.getBytes(URI.create(URL))).thenReturn(RICH_GIF);

        byte[] actual = service.getRichGif();

        assertEquals(RICH_GIF, actual);
        verify(gifClient).getRandomGif(API_KEY, RICH_TAG);
    }

    @Test
    void getBrokeGif() {
        when(gifClient.getRandomGif(API_KEY, BROKE_TAG)).thenReturn(GIF_DATA);
        when(gifClient.getBytes(URI.create(URL))).thenReturn(BROKE_GIF);

        byte[] actual = service.getBrokeGif();

        assertEquals(BROKE_GIF, actual);
        verify(gifClient).getRandomGif(API_KEY, BROKE_TAG);
    }
}