package com.jesusfc.springbootartificialintelligence.prompt;

/**
 * Author Jesús Fdez. Caraballo
 * jfcaraballo@gmail.com
 * Created on sept - 2024
 */
public class ChainOfThoughtTests extends BaseTestClass {

    //@Test
    void testTraditionalPrompt() {
        String prompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now?
                """;
        chat(prompt);
        System.out.println(chat(prompt));
    }

    //@Test
    void testChainOfThroughPrompt() {
        String chainOfThoughtPrompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now?
                
                A: Roger started with 5 balls. 2 cans of 3 balls each is 6 balls. 5 + 6 = 11. So Roger has 11 tennis balls.
                
                Q: The cafeteria had 23 apples originally. They used 20 apples to make lunch and bought 6 more. How many \s
                apples does the cafeteria have now?
                """;

        chat(chainOfThoughtPrompt);
        System.out.println(chat(chainOfThoughtPrompt));
    }

    //@Test
    void testTraditionalPrompt2() {
        
        String prompt = """
                Q: Roger has 5 tennis balls. He buys 2 more cans of tennis balls, each containing 3 balls. \s
                How many tennis balls does Roger have now? Answer in 1 word.
                """;
        chat(prompt);
        System.out.println(chat(prompt));
    }
}
