package com.example.belhafiane;

import io.github.cdimascio.dotenv.Dotenv;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

public class test1 {

    private final String key;

    public test1() {
        Dotenv dotenv = Dotenv.load();
        this.key = dotenv.get("GEMINI_KEY");
    }

    public void test() {

        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(key)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

//        String reponse = modele.chat("Bonjour, je suis Belrayy");
        String reponse = modele.chat("Quel est mon nom?");

        System.out.println("Réponse du modèle : " + reponse);
    }

    public static void main(String[] args) {
        test1 test = new test1();
        test.test();
    }
}
