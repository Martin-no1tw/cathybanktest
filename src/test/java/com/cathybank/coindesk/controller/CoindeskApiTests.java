package com.cathybank.coindesk.controller;

import com.cathybank.coindesk.service.CoindeskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CoindeskApiTests {

    @Autowired
    private CoindeskService service;

    @Test
    void testGetOriginalJson() {
        String json = service.getOriginalJson();
        assertNotNull(json);
        assertTrue(json.contains("bpi"));
    }
}
