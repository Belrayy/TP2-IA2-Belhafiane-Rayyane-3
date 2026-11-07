package com.example.belhafiane;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.TokenUsage;
import dev.langchain4j.store.embedding.CosineSimilarity;
import io.github.cdimascio.dotenv.Dotenv;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;

import java.util.Map;

public class Test4Embeddings {
    private final String key;

    public Test4Embeddings(){
        Dotenv dotenv = Dotenv.load();
        this.key = dotenv.get("GEMINI_KEY");
    }

    public static void main(String[] args) {

        EmbeddingModel modele = new BgeSmallEnV15EmbeddingModel();

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

            Embedding embedding1 = modele.embed(phrase1).content();
            Embedding embedding2 = modele.embed(phrase2).content();

            double similarite = CosineSimilarity.between(embedding1,embedding2);

            System.out.println("\nCouple " + (i + 1));
            System.out.println("Phrase 1 : " + phrase1);
            System.out.println("Phrase 2 : " + phrase2);
            System.out.printf("Similarité : %.4f%n", similarite);
        }
    }
}
