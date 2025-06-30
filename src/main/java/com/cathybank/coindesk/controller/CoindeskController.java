package com.cathybank.coindesk.controller;

import com.cathybank.coindesk.entity.Currency;
import com.cathybank.coindesk.service.CoindeskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CoindeskController {

    private final CoindeskService service;

    public CoindeskController(CoindeskService service) {
        this.service = service;
    }

    @GetMapping("/currency")
    public List<Currency> getAll() {
        return service.getAllCurrencies();
    }

    @PostMapping("/currency")
    public Currency create(@RequestBody Currency currency) {
        return service.save(currency);
    }

    @PutMapping("/currency/{code}")
    public Currency update(@PathVariable String code, @RequestBody Currency currency) {
        currency.setCode(code);
        return service.save(currency);
    }

    @DeleteMapping("/currency/{code}")
    public void delete(@PathVariable String code) {
        service.delete(code);
    }

    @GetMapping("/coindesk/original")
    public String getOriginal() {
        return service.getOriginalJson();
    }

    @GetMapping("/coindesk/transformed")
    public Map<String, Object> getTransformed() {
        return service.getTransformed();
    }
}
