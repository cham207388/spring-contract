package com.abc.contracts.consumer.services;

import com.abc.contracts.consumer.domains.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PostsService postsService;

    public PostResponse fetchUserPost(Optional<Integer> id) {
        return postsService.fetchPosts(id);
    }
}
