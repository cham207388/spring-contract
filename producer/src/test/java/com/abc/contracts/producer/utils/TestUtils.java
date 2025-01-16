package com.abc.contracts.producer.utils;

import java.util.Arrays;
import java.util.List;

import com.abc.contracts.producer.domains.Post;

public class TestUtils {

  public static List<Post> getAllPostsResponse() {
    return Arrays.asList(getPost(1, "Tool", "Gradle", 1),
        getPost(2, "Test", "Spring Cloud Contract", 1),
        getPost(3, "Test", "Contract Testing", 2));
  }

    public static List<Post> getPostsByUseridResponse() {
    return Arrays.asList(getPost(1, "Tool", "Gradle", 1),
        getPost(2, "Test", "Spring Cloud Contract", 1));
  }

  public static Post getPostByUserIdAndPostIdResponse() {
    return getPost(1, "Tool", "Gradle", 1);
  }

  private static Post getPost(int id, String title, String content, int userId) {
    return new Post(id, title, content, userId);
  }
}
