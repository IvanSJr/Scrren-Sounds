package com.github.ivansjr.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ChatGPTApiConsumer {
    public static String getSummaryToArtist(String text) {
        OpenAiService service = new OpenAiService(System.getenv("API_KEY_OPEN_AI"));

        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("Traga um resumo sobre esse artista: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
