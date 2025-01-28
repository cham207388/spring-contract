package com.abc.contracts.producer.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.abc.contracts.producer.controllers.PostController;
import com.abc.contracts.producer.services.PostService;

@Configuration
public abstract class HttpConfig {

  @Bean
  public PostService postService() {
    return mock(PostService.class); // Provide a mocked PostService
  }

  @Bean
  public PostController postController(PostService postService) {
    return new PostController(postService); // Provide PostController using mocked PostService
  }
}