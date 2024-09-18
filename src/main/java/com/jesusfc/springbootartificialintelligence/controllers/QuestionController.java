package com.jesusfc.springbootartificialintelligence.controllers;

import com.jesusfc.springbootartificialintelligence.model.Answer;
import com.jesusfc.springbootartificialintelligence.model.CapitalRQ;
import com.jesusfc.springbootartificialintelligence.model.Question;
import com.jesusfc.springbootartificialintelligence.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/askQuestion")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/askCapitalOf")
    public Answer askCapitalOf(@RequestBody CapitalRQ country) {
        return openAIService.getCapital(country);
    }
}
