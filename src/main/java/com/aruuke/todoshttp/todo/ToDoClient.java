package com.aruuke.todoshttp.todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ToDoClient {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ToDoClient() {
        client = HttpClient.newHttpClient(); //instance of the client
        objectMapper = new ObjectMapper();
    }

    public List<ToDo> findAll() throws IOException, InterruptedException {
       HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .GET()
                    .build();
       HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
       return objectMapper.readValue(response.body(), new TypeReference<List<ToDo>>() {
       });
    }

    public ToDo findById(int i) throws IOException, InterruptedException, TodoNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + i))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new TodoNotFoundException("todo. Todo not found");
        }

        return objectMapper.readValue(response.body(), ToDo.class);
    }

    public HttpResponse<String> create(ToDo todo) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(todo)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> update(ToDo todo) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + todo.id()))
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(todo)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> delete(ToDo todo) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + todo.id()))
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
