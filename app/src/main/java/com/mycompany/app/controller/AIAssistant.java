package com.mycompany.app.controller;

import com.mycompany.app.model.LibraryCatalog;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class AIAssistant {

    private final LibraryCatalog libraryCatalog;
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String apiUrl = "http://127.0.0.1:8000/assistant/chat";

    public AIAssistant(LibraryCatalog libraryCatalog) {
        this.libraryCatalog = libraryCatalog;
    }

    public String respond(String userInput) {
        String cleaned = userInput == null ? "" : userInput.trim();
        if (cleaned.isEmpty()) {
            return "Hello! Ask me about the EVSU Library collection.";
        }

        try {
            String payload = mapper.writeValueAsString(Map.of("query", cleaned));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("Assistant call failed: " + response.statusCode() + " -> " + response.body());
                return "⚠️ Assistant service error (" + response.statusCode() + ").";
            }

            JsonNode json = mapper.readTree(response.body());
            String answer = json.path("response").asText("").trim();
            if (answer.isEmpty()) {
                answer = "⚠️ Empty reply from assistant.";
            }
            StringBuilder result = new StringBuilder(answer);
            JsonNode matches = json.path("matches");
            if (matches.isArray() && matches.size() > 0) {
                result.append("\n\n📚 Matched catalog entries:");
                for (JsonNode match : matches) {
                    result.append("\n• ").append(match.asText());
                }
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ Unable to reach the EVSU AI assistant right now.";
        }
    }

    private String extractBookTitle(String message) {
        String[] parts = message.split(" ", 2);
        if (parts.length > 1) {
            return parts[1].trim();
        }
        return null;
    }
}
