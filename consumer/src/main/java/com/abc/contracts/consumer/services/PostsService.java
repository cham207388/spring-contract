package com.abc.contracts.consumer.services;

import com.abc.contracts.consumer.domains.Post;
import com.abc.contracts.consumer.domains.PostResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class PostsService {

    public PostResponse fetchPosts(Optional<Integer> userId) {
        RestClient restClient = RestClient.create();
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(14257)
                .path("/posts")
                .queryParamIfPresent("userId", userId)
                .toUriString();
        return restClient.get()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PostResponse.class);
    }

    public Post addPost(Post post) {
        RestClient restClient = RestClient.create();
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(14257)
                .path("/posts")
                .toUriString();
        return restClient.post()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(post)
                .retrieve()
                .body(Post.class);
    }
}
