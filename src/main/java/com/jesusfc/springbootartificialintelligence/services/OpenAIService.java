package com.jesusfc.springbootartificialintelligence.services;

import com.jesusfc.springbootartificialintelligence.model.Answer;
import com.jesusfc.springbootartificialintelligence.model.CapitalRQ;
import com.jesusfc.springbootartificialintelligence.model.Question;
import org.springframework.ai.chat.prompt.Prompt;

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

    Answer getMilvusRagAnswer(Question question);
}
