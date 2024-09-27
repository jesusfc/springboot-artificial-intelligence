package com.jesusfc.springbootartificialintelligence.controllers;

import com.jesusfc.springbootartificialintelligence.model.*;
import com.jesusfc.springbootartificialintelligence.services.OpenAIService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/askRagQuestion")
    public Answer askRagQuestion(@RequestBody Question question) {
        return openAIService.getRagAnswer(question);
    }

    @PostMapping("/askCelebrityCityQuestion")
    public Answer askNinjaApiCelebrityQuestion(@RequestBody CelebrityRQ celebrityRQ) {
        return openAIService.getNinjaApiCelebrityAnswer(celebrityRQ);
    }

    @PostMapping(value = "/askForImage64", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] askForImageB64Encode(@RequestBody ImageRQ image) {
        return openAIService.getImageB64Encode(image);
    }

    @PostMapping(value = "/askForDescriptionImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> upload(@Validated @RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
        return ResponseEntity.ok(openAIService.getFileDescription(file));
    }

    @PostMapping(value ="/talk", produces = "audio/mpeg")
    public byte[] assToTalk(@RequestBody Question question) {
        return openAIService.getSpeechAudio(question);
    }
}
