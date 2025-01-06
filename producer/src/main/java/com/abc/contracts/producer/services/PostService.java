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


    public PostResponse fetchPost(Optional<Integer> userId) {
        List<Post> posts = POSTS.stream()
                .filter(post -> userId.isEmpty() || post.getUserId() == userId.get())
                .toList();
        return new PostResponse(userId.orElse(0), posts);
    }

    public Post save(Post post) {
        POSTS.add(post);
        return post;
    }
}
