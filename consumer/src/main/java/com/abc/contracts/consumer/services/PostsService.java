package com.abc.contracts.consumer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.abc.contracts.consumer.client.RestfulClient;
import com.abc.contracts.consumer.domains.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final RestfulClient restfulClient;

    public List<Post> getAllPosts() {
        return restfulClient.getAllPosts();
    }

    public List<Post> getPostsByUserId(int userId) {
        return restfulClient.getPostsByUserId(userId);
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        return restfulClient.getPostByUserIdAndPostId(id, userId);
    }

    public Post savePost(Post post) {
        return restfulClient.savePost(post);
    }
    
    public List<Post> saveAllPost(List<Post> posts) {
        return restfulClient.saveAllPost(posts);
    }
}
