package com.abc.contracts.producer.services;


import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.domains.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.abc.contracts.producer.repository.UserRepository.POSTS;

@Slf4j
@Service
public class PostService {


    public PostResponse getAllPosts() {
        log.info("Fetching all posts");
        return new PostResponse(0, POSTS);
    }

    public PostResponse getPostsByUserid(int userId) {
        log.info("Fetching user: {} posts", userId);
        List<Post> posts = POSTS.stream()
                .filter(post -> post.getUserId() == userId)
                .toList();
        return new PostResponse(userId, posts);
    }

    public Post save(Post post) {
        POSTS.add(post);
        return post;
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        return POSTS.stream()
                .filter(post -> post.getUserId() == userId && post.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
