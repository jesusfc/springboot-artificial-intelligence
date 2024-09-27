package com.jesusfc.springbootartificialintelligence.services;

import com.jesusfc.springbootartificialintelligence.model.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on may - 2024
 */
public interface OpenAIService {

    String getAnswer(String question);

    String getAnswer(Prompt prompt);

    Answer getAnswer(Question question);

    Answer getCapital(CapitalRQ capitalRQ);

    Answer getRagAnswer(Question question);

    Answer getNinjaApiCelebrityAnswer(CelebrityRQ celebrityRQ);

    byte[] getImageB64Encode(ImageRQ imageRQ);

    String getFileDescription(MultipartFile file) throws IOException;
}
