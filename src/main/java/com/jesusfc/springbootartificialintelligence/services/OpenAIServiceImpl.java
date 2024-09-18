package com.jesusfc.springbootartificialintelligence.services;

import com.jesusfc.springbootartificialintelligence.model.Answer;
import com.jesusfc.springbootartificialintelligence.model.CapitalRQ;
import com.jesusfc.springbootartificialintelligence.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on may - 2024
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }

    @Override
    public String getAnswer(Prompt prompt) {
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapital(CapitalRQ capitalRQ) {
        //PromptTemplate promptTemplate = new PromptTemplate("¿Cual es la capital de " + capitalRQ.country() +"?");
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("country", capitalRQ.country()));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return new Answer(response.getResult().getOutput().getContent());
    }
}
