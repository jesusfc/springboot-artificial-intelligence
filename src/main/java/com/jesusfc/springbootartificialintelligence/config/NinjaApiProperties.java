package com.jesusfc.springbootartificialintelligence.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on sept - 2024
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ninja-api")
public class NinjaApiProperties {
    private String apiUrl;
    private String apiKey;
}
