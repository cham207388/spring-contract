package com.abc.contracts.producer.utils;

import java.util.Arrays;

import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.domains.PostResponse;

public class TestUtils {

  public static PostResponse getAllPostsResponse() {
    return new PostResponse(1, Arrays.asList(getPost(1, "Tool", "Gradle", 1),
        getPost(2, "Test", "Spring Cloud Contract", 1),
        getPost(3, "Test", "Contract Testing", 2)));
  }

  public static PostResponse getPostsByUseridResponse() {
    return new PostResponse(1, Arrays.asList(getPost(1, "Tool", "Gradle", 1),
        getPost(2, "Test", "Spring Cloud Contract", 1)));
  }

  public static Post getPostByUserIdAndPostIdResponse() {
    return getPost(1, "Tool", "Gradle", 1);
  }

  private static Post getPost(int id, String title, String content, int userId) {
    return new Post(id, title, content, userId);
  }
}
