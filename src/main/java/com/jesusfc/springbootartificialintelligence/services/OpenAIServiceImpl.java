package com.jesusfc.springbootartificialintelligence.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on may - 2024
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

  //      String content = chatClient.prompt(prompt).call().content();

       ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
       return response.getResult().getOutput().getContent();
/*
        Flux<String> output = chatClient.prompt()
                .user(question)
                .stream()
                .content();

        return output.blockFirst();
*/
    }
}
