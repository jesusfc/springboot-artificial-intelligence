package com.jesusfc.springbootartificialintelligence.services;

import com.jesusfc.springbootartificialintelligence.config.NinjaApiProperties;
import com.jesusfc.springbootartificialintelligence.iaFunctions.CelebrityServiceFunction;
import com.jesusfc.springbootartificialintelligence.model.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on may - 2024
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;
    private final SimpleVectorStore simpleVectorStore;
    private final OpenAiChatModel openAiChatModel;
    private final NinjaApiProperties ninjaApiProperties;
    private final OpenAiImageModel openAiImageModel;
    private final OpenAiAudioSpeechModel openAiAudioSpeechClient;

    // SIN metadatos
    //@Value("classpath:templates/rag-prompt-template.st")
    //private Resource getRagPromptTemplate;

    // Usamos la definición de las columnas para proporcionar más información, metadatos
    @Value("classpath:templates/rag-prompt-template-metadata.st")
    private Resource getRagPromptTemplate;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder, SimpleVectorStore simpleVectorStore, OpenAiChatModel openAiChatModel, NinjaApiProperties ninjaApiProperties, OpenAiImageModel openAiImageModel, OpenAiAudioSpeechModel openAiAudioSpeechClient) {
        this.chatClient = chatClientBuilder.build();
        this.simpleVectorStore = simpleVectorStore;
        this.openAiChatModel = openAiChatModel;
        this.ninjaApiProperties = ninjaApiProperties;
        this.openAiImageModel = openAiImageModel;
        this.openAiAudioSpeechClient = openAiAudioSpeechClient;
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

    @Override
    public Answer getRagAnswer(Question question) {
        List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(4));
        List<String> contentList = documents.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate = new PromptTemplate(getRagPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents", String.join("\n", contentList)));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getNinjaApiCelebrityAnswer(CelebrityRQ celebrityRQ) {

        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new CelebrityServiceFunction(ninjaApiProperties))
                        .withName("Celebrity")
                        .withDescription("Get information of this celebrity")
                        .withResponseConverter((response) -> {
                            String schema = ModelOptionsUtils.getJsonSchema(CelebrityRS.class, false);
                            String json = ModelOptionsUtils.toJsonString(response);
                            return schema + "\n" + json;
                        })
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(celebrityRQ.celebrityName()).createMessage();

        Message systemMessage = new SystemPromptTemplate("You are a celebrity service. Provide us the most relevance information about the celebrity.").createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(userMessage, systemMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());

    }

    @Override
    public byte[] getImageB64Encode(ImageRQ imageRQ) {
        var options = ImageOptionsBuilder.builder()
                .withHeight(1024).withWidth(1024)
                .withResponseFormat("b64_json")
                .withModel("dall-e-3")
                .withStyle("vivid")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(imageRQ.image(), options);

        var imageResponse = openAiImageModel.call(imagePrompt);
        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }

    @Override
    public String getFileDescription(MultipartFile file) {
        try {
            ImageOptions imageOptions = OpenAiImageOptions.builder()
                    .withModel(OpenAiApi.ChatModel.GPT_3_5_TURBO.getValue())
                    .withHeight(1792)
                    .withWidth(1024)
                    .build();

            ImageMessage imageMessage = new ImageMessage("Explain what do you see in this picture?");

            return openAiImageModel.call(new ImagePrompt(imageMessage, imageOptions)).getResult().toString();
        } catch (Exception e) {
            return "Error processing the image: " + e.getMessage();
        }
    }

    @Override
    public byte[] getSpeechAudio(Question question) {

        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .withSpeed(1.0f)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(question.question(), speechOptions);
        SpeechResponse response = openAiAudioSpeechClient.call(speechPrompt);

        return response.getResult().getOutput();
    }


}
