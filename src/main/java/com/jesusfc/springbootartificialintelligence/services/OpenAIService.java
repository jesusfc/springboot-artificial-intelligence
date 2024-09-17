package com.jesusfc.springbootartificialintelligence.services;

import com.jesusfc.springbootartificialintelligence.model.Answer;
import com.jesusfc.springbootartificialintelligence.model.CapitalRQ;
import com.jesusfc.springbootartificialintelligence.model.Question;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on may - 2024
 */
public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapital(CapitalRQ capitalRQ);

}
