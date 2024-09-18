package com.jesusfc.springbootartificialintelligence.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on may - 2024
 */
@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIService openAIService;

    @Test
    void testGetAnswer() {
        String cuentaMeUnChiste = openAIService.getAnswer("Cuenta me un chiste");
        System.out.println("La respuesta es: " + openAIService.getAnswer(cuentaMeUnChiste));
    }


}