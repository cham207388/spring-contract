package com.abc.contracts.consumer.services;

import com.abc.contracts.consumer.client.RestfulClient;
import com.abc.contracts.consumer.domains.Post;
import com.abc.contracts.consumer.domains.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final RestfulClient restfulClient;


    public PostResponse getAllPosts() {
        return restfulClient.getAllPosts();
    }

    public PostResponse getPostsByUserId(int userId) {
        return restfulClient.getPostsByUserId(userId);
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        return restfulClient.getPostByUserIdAndPostId(id, userId);
    }

    public Post addPost(Post post) {
        return restfulClient.addPost(post);
    }
}
