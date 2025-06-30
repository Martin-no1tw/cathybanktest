package com.cathybank.coindesk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCurrencyCrudOperations() throws Exception {
        String jsonCreate = "{\"code\":\"JPY\",\"nameZh\":\"日圓\"}";
        String jsonUpdate = "{\"code\":\"JPY\",\"nameZh\":\"日本圓\"}";

        // 測試POST:/api/currency
        mockMvc.perform(post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreate))
                .andExpect(status().isOk());

        // 測試GET:/api/currency
        mockMvc.perform(get("/api/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.code=='JPY')].nameZh").value("日圓"));

        // 測試PUT:/api/currency/JPY
        mockMvc.perform(put("/api/currency/JPY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk());

        //驗證名稱是否被更新
        mockMvc.perform(get("/api/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.code=='JPY')].nameZh").value("日本圓"));

        //測試DELETE:/api/currency/JPY
        mockMvc.perform(delete("/api/currency/JPY"))
                .andExpect(status().isOk());

        //驗證已刪除
        mockMvc.perform(get("/api/currency"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.code=='JPY')]").doesNotExist());
    }
}
