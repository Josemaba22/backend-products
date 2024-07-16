package com.cristian.crudproducts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.cristian.crudproducts.controller.HelloWorldController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HelloWorldController.class)
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloWorld_returnsHelloWorld() throws Exception {
        mockMvc.perform(get("/api/v1/hello-world/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

}
