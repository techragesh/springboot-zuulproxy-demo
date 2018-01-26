package com.techragesh.stockservice.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/stock")
public class StockResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{username}")
    public List<Quote> getStock(@PathVariable String username){
        String URI = "http://localhost:8501/api/dbservice/rest/db/" + username;
        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        });

        List<String> quotes = responseEntity.getBody();
        System.out.println(quotes);
        return quotes
                .stream()
                .map(s -> {
                    BigDecimal price = getStockPrice(s);
                    return new Quote(s, price);
                })
                .collect(Collectors.toList());

    }

    private BigDecimal getStockPrice(String quote) {
        return new BigDecimal("200");
    }

    @AllArgsConstructor
    private class Quote {
        @Getter @Setter
        private String quote;
        @Getter @Setter
        private BigDecimal price;
    }
}
