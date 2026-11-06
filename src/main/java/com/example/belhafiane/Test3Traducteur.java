package com.example.belhafiane;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.TokenUsage;
import io.github.cdimascio.dotenv.Dotenv;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

import java.util.Map;

public class Test3Traducteur {
    private final String key;

    public Test3Traducteur(){
        Dotenv dotenv = Dotenv.load();
        this.key = dotenv.get("GEMINI_KEY");
    }
    public void test() {

        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(key)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        String texte = "Bonjour, je m'appelle Belrayy";

        PromptTemplate template1 = PromptTemplate.from(
                "Traduis le texte suivant en anglais : {{texte}}"
        );

        Prompt prompt1 = template1.apply(
                Map.of("texte", texte)
        );

        String reponse1 = modele.chat(prompt1.text());

        System.out.println("Traduction en anglais");
        System.out.println(reponse1);

        PromptTemplate template2 = PromptTemplate.from(
                "Traduis le texte suivant en {{langue}} : {{texte}}"
        );

        Prompt prompt2 = template2.apply(
                Map.of(
                        "langue", "espagnol",
                        "texte", texte
                )
        );

        String reponse2 = modele.chat(prompt2.text());

        System.out.println("\nTraduction en espagnol");
        System.out.println(reponse2);
    }

    public static void main(String[] args) {
        Test3Traducteur test = new Test3Traducteur();
        test.test();
    }
}
