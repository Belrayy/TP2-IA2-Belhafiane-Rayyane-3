package com.example.belhafiane;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import io.github.cdimascio.dotenv.Dotenv;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

public class Test2Couts {
    private final String key;

    private static final double INPUT_PRICE = 0.30;
    private static final double OUTPUT_PRICE = 2.50;

    public Test2Couts() {
        Dotenv dotenv = Dotenv.load();
        this.key = dotenv.get("GEMINI_KEY");
    }

    public void test() {
        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(key)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        String question = "Bonjour; je suis Belrayy";

        ChatResponse response = modele.chat(
                UserMessage.from(question)
        );
        System.out.println("Réponse : " + response.aiMessage().text());

        TokenUsage usage = response.tokenUsage();

        if (usage == null
                || usage.inputTokenCount() == null
                || usage.outputTokenCount() == null) {
            System.out.println("Informations sur les tokens indisponibles.");
            return;
        }

        int inputTokens = usage.inputTokenCount();
        int outputTokens = usage.outputTokenCount();
        int totalTokens = usage.totalTokenCount();

        System.out.println("Tokens en entrée : " + inputTokens);
        System.out.println("Tokens en sortie : " + outputTokens);
        System.out.println("Total des tokens : " + totalTokens);

        double inputCost = inputTokens * INPUT_PRICE / 1_000_000;
        double outputCost = outputTokens * OUTPUT_PRICE / 1_000_000;

        double totalCost = inputCost + outputCost;

        System.out.printf("Coût entrée : $%.10f%n", inputCost);
        System.out.printf("Coût sortie : $%.10f%n", outputCost);
        System.out.printf("Coût total : $%.10f%n", totalCost);
    }

    public static void main(String[] args) {
        Test2Couts test = new Test2Couts();
        test.test();
    }
}
