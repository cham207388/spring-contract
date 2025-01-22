package com.abc.contracts.producer.utils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.abc.contracts.producer.domains.Post;

public class TestUtils {

//  private static final LocalDateTime INSTANCE = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
  private static final LocalDateTime INSTANCE = LocalDateTime.now();

  public static List<Post> getAllPostsResponse() {
    return Arrays.asList(getPost(1, "Tool", "Gradle", 1, INSTANCE),
        getPost(2, "Test", "Spring Cloud Contract", 1, INSTANCE),
        getPost(3, "Test", "Contract Testing", 2, INSTANCE));
  }

    public static List<Post> getPostsByUseridResponse() {
    return Arrays.asList(getPost(1, "Tool", "Gradle", 1, INSTANCE),
        getPost(2, "Test", "Spring Cloud Contract", 1, INSTANCE));
  }

  public static Post getPostByUserIdAndPostIdResponse() {
    return getPost(1, "Tool", "Gradle", 1, INSTANCE);
  }

  private static Post getPost(int id, String title, String content, int userId, LocalDateTime createdAt) {
    return new Post(id, title, content, userId, createdAt);
  }
}
