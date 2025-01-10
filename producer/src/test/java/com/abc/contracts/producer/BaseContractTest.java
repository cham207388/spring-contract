package com.abc.contracts.producer;


import com.abc.contracts.producer.controllers.PostController;
import com.abc.contracts.producer.services.PostService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

public class BaseContractTest {
    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(new PostController(new PostService()));
    }
}