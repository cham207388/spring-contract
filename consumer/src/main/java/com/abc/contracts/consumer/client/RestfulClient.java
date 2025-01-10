package com.abc.contracts.consumer.client;

import com.abc.contracts.consumer.domains.Post;
import com.abc.contracts.consumer.domains.PostResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class RestfulClient {

    private final RestClient restClient;

    @Getter
    @Value("${producer.port}")
    private int port;

    private static final String HOST = "localhost";

    public PostResponse getAllPosts() {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(HOST)
                .port(port)
                .path("/posts")
                .toUriString();
        return restClient.get()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PostResponse.class);
    }

    public PostResponse getPostsByUserId(int userId) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(HOST)
                .port(port)
                .path("/posts/users")
                .queryParam("userId", userId)
                .toUriString();
        return restClient.get()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(PostResponse.class);
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        return restClient.get()
                .uri("http://localhost:{PORT}/posts/{id}/users/{userId}", port, id, userId)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(Post.class);
    }

    public Post addPost(Post post) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(HOST)
                .port(port)
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
