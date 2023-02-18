package com.codestates.coffee.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/coffees")
public class CoffeeControllerV1 {

    @PostMapping
    public ResponseEntity postCoffee(@RequestHeader("user-agent") String userAgent,
                                     @RequestParam String korName,
                                     @RequestParam String engName,
                                     @RequestParam int price) {
        System.out.println("userAgent = " + userAgent);
        HashMap<String, Object> map = new HashMap<>();
        map.put("korName", korName);
        map.put("engName", engName);
        map.put("price", price);
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.add("my-header", "hello");
        return new ResponseEntity<>(map, newHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{coffee-id}")
    public String getCoffee(@PathVariable("coffee-id") long coffeeId) {
        return coffeeId + "";
    }

    @GetMapping
    public String getCoffees(HttpEntity httpEntity) {
        HttpHeaders headers = httpEntity.getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.println(entry);
        }
        return "getCoffees";
    }
}
