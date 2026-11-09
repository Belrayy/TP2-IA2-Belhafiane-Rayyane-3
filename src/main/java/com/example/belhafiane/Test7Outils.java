package com.example.belhafiane;

import com.example.belhafiane.outil.meteo.MeteoTool;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import io.github.cdimascio.dotenv.Dotenv;
import com.example.belhafiane.outil.meteo.AssistantMeteo;

import java.util.Scanner;

public class Test7Outils {

    private String llmKey;

    public Test7Outils() {
        Dotenv dotenv = Dotenv.load();
        this.llmKey = dotenv.get("GEMINI_KEY");
    }

    public static void main(String[] args) {

        Test7Outils test = new Test7Outils();

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(test.llmKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.3)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20)) // L'utilisation des outils génère des messages
                        .tools(new MeteoTool())  // Ajout de l'outil
                        .build();

        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {

                System.out.println("Posez votre question :");
                String question = scanner.nextLine();

                if (question.isBlank()) {
                    continue;
                }

                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }

                String reponse = assistant.chat(question);

                System.out.println("Assistant : " + reponse);
            }
        }
    }
}
