package com.soneca.poc.qrcode.webapp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by andre on 18/10/16.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.soneca.poc.qrcode.webapp")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
