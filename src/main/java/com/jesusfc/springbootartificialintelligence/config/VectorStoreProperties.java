package com.jesusfc.springbootartificialintelligence.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rag.aiapp")
public class VectorStoreProperties {

    private String vectorStorePath;
    private List<Resource> documentsToLoad;

}
