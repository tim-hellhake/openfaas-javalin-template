package com.openfaas.function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @BeforeAll
    static void init() {
        App.main(new String[0]);
    }

    @Test
    void appReturnsHelloWorld() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/"))
                .build();
        String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        assertEquals(body, "Hello World");
    }
}
