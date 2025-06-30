package com.cathybank.coindesk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransformedApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTransformedData() throws Exception {
        //正確轉換資料格式（包含更新時間與幣別匯率清單）
        //符合預期的 JSON 結構
        mockMvc.perform(get("/api/coindesk/transformed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateTime").exists())
                .andExpect(jsonPath("$.currencyRates").isArray());
    }
}

