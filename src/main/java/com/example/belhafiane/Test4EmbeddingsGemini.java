package com.example.belhafiane;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.TokenUsage;
import dev.langchain4j.store.embedding.CosineSimilarity;
import io.github.cdimascio.dotenv.Dotenv;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;

import java.time.Duration;
import java.util.Map;

public class Test4EmbeddingsGemini {
    private final String key;

    public Test4EmbeddingsGemini(){
        Dotenv dotenv = Dotenv.load();
        this.key = dotenv.get("GEMINI_KEY");
    }

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        String key = dotenv.get("GEMINI_KEY");

        if (key == null || key.isBlank()) {
            throw new IllegalStateException("GEMINI_KEY is missing");
        }

        EmbeddingModel modeleGoogle =
                GoogleGenAiEmbeddingModel.builder()
                        .apiKey(key)
                        .modelName("gemini-embedding-001")
                        .taskType(
                                GoogleGenAiEmbeddingModel.TaskTypeEnum.SEMANTIC_SIMILARITY
                        )
                        .timeout(Duration.ofMillis(1000))
                        .build();

        String[][] phrases = {
                {
                        "The cat is sleeping on the sofa.",
                        "A cat is resting on a couch."
                },
                {
                        "I love programming in Java.",
                        "I hate writing Java code."
                },
                {
                        "The weather is sunny and warm today.",
                        "Today is a hot and sunny day."
                },
                {
                        "I love eating pizza.",
                        "I hate eating pizza."
                },
                {
                        "The car is driving on the road.",
                        "The ocean is full of marine animals."
                },
                {
                        "Artificial intelligence is transforming technology.",
                        "Plants need water and sunlight to grow."
                }
        };


        for (int i = 0; i < phrases.length; i++) {

            String phrase1 = phrases[i][0];
            String phrase2 = phrases[i][1];


            Embedding google1 = modeleGoogle.embed(phrase1).content();
            Embedding google2 = modeleGoogle.embed(phrase2).content();

            double similarite = CosineSimilarity.between(google1, google2);

            System.out.println("\nCouple " + (i + 1));
            System.out.println("Phrase 1 : " + phrase1);
            System.out.println("Phrase 2 : " + phrase2);
            System.out.printf("Similarité : %.4f%n", similarite);
        }
    }
}
