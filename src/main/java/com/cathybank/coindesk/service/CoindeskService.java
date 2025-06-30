package com.cathybank.coindesk.service;

import com.cathybank.coindesk.entity.Currency;
import com.cathybank.coindesk.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoindeskService {

    @Autowired
    private CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public CoindeskService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public void delete(String code) {
        currencyRepository.deleteById(code);
    }

    public String getOriginalJson() {
        return restTemplate.getForObject("https://kengp3.github.io/blog/coindesk.json", String.class);
    }

    public Map<String, Object> getTransformed() {
        Map<String, Object> result = new LinkedHashMap<>();
        String json = getOriginalJson();

        try {
            // 解析JSON
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> root = mapper.readValue(json, Map.class);
            Map<String, Object> time = (Map<String, Object>) root.get("time");
            Map<String, Object> bpi = (Map<String, Object>) root.get("bpi");

            String updated = (String) time.get("updatedISO");
            SimpleDateFormat srcFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            SimpleDateFormat dstFmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formattedTime = dstFmt.format(srcFmt.parse(updated));

            // 使用 Stream 處理 bpi -> List<Map<String, String>>
            List<Map<String, String>> currencyList = bpi.entrySet().stream()
                    .map(entry -> {
                        String code = entry.getKey();
                        Map<String, Object> value = (Map<String, Object>) entry.getValue();
                        String rate = (String) value.get("rate");
                        String zh = currencyRepository.findById(code)
                                .map(Currency::getNameZh)
                                .orElse("未查詢到相關幣別");

                        Map<String, String> row = new LinkedHashMap<>();
                        row.put("code", code);
                        row.put("nameZh", zh);
                        row.put("rate", rate);
                        return row;
                    })
                    .collect(Collectors.toList());

            result.put("updateTime", formattedTime);
            result.put("currencyRates", currencyList);
            return result;

        } catch (Exception e) {
            return Collections.singletonMap("ERROR_Message", e.getMessage());
        }
    }
}
