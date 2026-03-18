package com.aruuke.todoshttp.todo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToDoClientTest {

    //System Under Test (sut)
    ToDoClient client = new ToDoClient();

    @Test
    void findAll() throws IOException, InterruptedException {
        List<ToDo> toDos = client.findAll();
        assertEquals(200, toDos.size());
    }

    @Test
    void shouldReturnTodoGivenValueValid() throws IOException, InterruptedException, TodoNotFoundException {
        ToDo todo = client.findById(1);
        assertEquals(1, todo.userId());
        assertEquals(1, todo.id());
        assertEquals("delectus aut autem", todo.title());
        assertFalse(todo.completed());
    }

    @Test
    void shouldReturnTodoGivenValueInvalidId() {
        TodoNotFoundException todoNotFoundException = assertThrows(TodoNotFoundException.class, () -> client.findById(999));
        assertEquals("todo. Todo not found", todoNotFoundException.getMessage());
    }

    @Test
    void shouldCreateNewTodo() throws IOException, InterruptedException, TodoNotFoundException {
        ToDo todo = new ToDo(1, 1, "LEARN JAVA", false);
        HttpResponse<String> response = client.create(todo);
        assertEquals(201, response.statusCode());
    }

    @Test
    void shouldUpdateExistingTodo() throws IOException, InterruptedException, TodoNotFoundException {
        ToDo todo = new ToDo(1, 1, "NEW TITLE", true);
        HttpResponse<String> response = client.update(todo);
        assertEquals(200, response.statusCode());
    }

    @Test
    void shouldDeleteExistingTodo() throws IOException, InterruptedException, TodoNotFoundException {
        ToDo todo = client.findById(1);
        HttpResponse<String> response = client.delete(todo);
        assertEquals(200, response.statusCode());
    }
}