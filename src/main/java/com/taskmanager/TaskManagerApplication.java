package com.taskmanager;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagerApplication {

    public static final Logger logger = LoggerFactory.getLogger(TaskManagerApplication.class);

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .filename("config.env")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(TaskManagerApplication.class, args);

    }
}
