package com.cristian.crudproducts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.cristian.crudproducts.controller.HelloWorldController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HelloWorldController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HelloWorldController helloWorldController;

    @Test
    public void helloWorld_returnsHelloWorld() throws Exception {
        mockMvc.perform(get("/api/v1/hello-world/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    public void itShouldSayHelloWorld() throws Exception {
        String result = helloWorldController.helloWorld();
        assertEquals("Hello World!", result);
    }

}
