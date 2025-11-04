package com.example.crudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudappApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudappApplication.class, args);
        System.out.println("🚀 CRUD Application started successfully!");
        System.out.println("📡 Server running on: http://localhost:8080");
        System.out.println("📚 API endpoints available at: http://localhost:8080/api/products");
    }
}