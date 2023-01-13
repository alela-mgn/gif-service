package com.currency.comparator.controller;

import com.currency.comparator.service.GifService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@WebMvcTest(Controller.class)
class ControllerTest {
    private final static byte[] richGif = new byte[]{10, 25, 30};
    private final static byte[] brokeGif = new byte[]{55};

    @Autowired
    private MockMvc mockMvc;

    @Mock
    GifService gifService;

    @Test
    public void getResult() throws Exception {
        when(gifService.getRichGif()).thenReturn(richGif);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currency/compare/"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(richGif));

        verify(gifService).getRichGif();
    }
}