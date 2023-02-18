package com.codestates;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestClientExample01 {
    public static void main(String[] args) {
        // 객체 생성
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        // URI 생성
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host("api.openweathermap.org").path("/data/2.5/weather")
                .queryParam("id", "1835848")
                .queryParam("appid", "e6fedcaf84c503415147fc17e46c659f")
                .encode().build();

        URI uri = uriComponents.toUri();

        // Request 전송(1)
//        Map result = restTemplate.getForObject(uri, Map.class);
        // Request 전송(2)
//        Weather weather = restTemplate.getForObject(uri, Weather.class);
        // Request 전송(3)
//        ResponseEntity<Weather> response = restTemplate.getForEntity(uri, Weather.class);
        // Request 전송(4)
        ResponseEntity<Weather> response = restTemplate.exchange(uri, HttpMethod.GET, null, Weather.class);

        System.out.println("-".repeat(60));
        // 전송 결과 확인(1)
//        for (Object o : result.entrySet()) {
//            System.out.println(o);
//        }
        // 전송 결과 확인(2)
//        System.out.println(weather.getCoord());
//        System.out.println(weather.getMain());
//        System.out.println(weather.getName());

        // 전송 결과 확인(3, 4)
        System.out.println("main : " + response.getBody().getMain());
        System.out.println("status code : " + response.getStatusCode());
        System.out.println("status code value : " + response.getStatusCodeValue());
        System.out.println("content type : " + response.getHeaders().getContentType());
        System.out.println(response.getHeaders().entrySet());
    }
}
