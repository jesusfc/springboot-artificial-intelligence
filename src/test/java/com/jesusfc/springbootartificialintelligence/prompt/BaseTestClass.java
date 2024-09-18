package com.jesusfc.springbootartificialintelligence.prompt;


import com.jesusfc.springbootartificialintelligence.services.OpenAIService;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@SpringBootTest
public class BaseTestClass {

    @Autowired
    OpenAIService openAIService;

    String chat(String prompt) {
        return openAIService.getAnswer(prompt);
    }

    String chat(Prompt prompt) {
        return openAIService.getAnswer(prompt);
    }

}
