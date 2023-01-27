package com.currency.comparator.controller;

import com.currency.comparator.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@WebMvcTest(Controller.class)
class ControllerTest {
    private static final byte[] RICH_GIF = new byte[]{10, 25, 30};

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CurrencyService gifService;

    @Test
    public void getResult() throws Exception {
        when(gifService.compareRate("EUR")).thenReturn(RICH_GIF);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currency/compare/EUR"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(RICH_GIF));

        verify(gifService).compareRate("EUR");
    }
}