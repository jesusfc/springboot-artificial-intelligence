package com.jesusfc.springbootartificialintelligence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CelebrityRS(
        @JsonProperty("name") String name,
        @JsonProperty("gender") String gender,
        @JsonProperty("nationality") String nationality) {
}

/*
[
    {"name": "michael jordan", "net_worth": 2200000000, "gender": "male", "nationality": "us", "occupation": ["basketball_player", "athlete", "spokesperson", "entrepreneur", "actor"], "height": 1.98, "birthday": "1963-02-17", "age": 61, "is_alive": true}
]
 */