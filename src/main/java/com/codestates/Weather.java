package com.codestates;

import lombok.Getter;

import java.util.Map;

@Getter
public class Weather {
    private Map<String, Double> coord;
    private Map<String, Double> main;
    private String name;
}
