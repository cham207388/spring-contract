package com.abc.contracts.consumer.client;

import com.abc.contracts.consumer.domains.Post;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import org.springframework.core.ParameterizedTypeReference;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class RestfulClient {

    private final RestClient restClient;

    @Getter
    @Value("${producer.port}")
    private int port;

    @Getter
    @Value("${producer.host}")
    private String producerHost;

        public List<Post> getAllPosts() {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(producerHost)
                .port(port)
                .path("/posts")
                .toUriString();
        return restClient.get()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
    }

    public List<Post> getPostsByUserId(int userId) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(producerHost)
                .port(port)
                .path("/posts/users")
                .queryParam("userId", userId)
                .toUriString();
        return restClient.get()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
    }

    public Post getPostByUserIdAndPostId(int id, int userId) {
        return restClient.get()
                .uri("http://{host}:{PORT}/posts/{id}/users/{userId}", producerHost, port, id, userId)
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(Post.class);
    }

    public Post savePost(Post post) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(producerHost)
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

    public List<Post> saveAllPost(List<Post> post) {
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(producerHost)
                .port(port)
                .path("/posts/all")
                .toUriString();
        return restClient.post()
                .uri(URI.create(uri))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(post)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
    }
}
