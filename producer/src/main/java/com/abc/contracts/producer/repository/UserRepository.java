package com.abc.contracts.producer.repository;


import com.abc.contracts.producer.domains.Post;

import java.util.List;

public class UserRepository {

    private UserRepository() {
        throw new IllegalStateException("Cannot create");
    }

    public static final List<Post> POSTS = List.of(
            new Post(1, "Tool", "Gradle", 1),
            new Post(2, "Test", "Spring Cloud Contract", 1),
            new Post(3, "Test", "Contract Testing", 2)
    );
}
