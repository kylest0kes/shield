package com.shield.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {
        System.out.println("Shield Server is running...");
        SpringApplication.run(Server.class, args);
    }

}
