package com.cristian.crudproducts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello-world")
public class HelloWorldController {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
