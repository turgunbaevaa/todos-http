package com.aruuke.todoshttp;

import com.aruuke.todoshttp.todo.ToDo;
import com.aruuke.todoshttp.todo.ToDoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TodosHttpApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(TodosHttpApplication.class, args);

        System.out.println("You should check out the tests!");

    }

}
