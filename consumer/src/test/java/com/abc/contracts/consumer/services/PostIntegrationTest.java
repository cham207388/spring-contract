package com.abc.contracts.consumer.services;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;

import com.abc.contracts.consumer.client.RestfulClient;
import com.abc.contracts.consumer.domains.Post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
        ids = {"com.abc.contracts:producer:0.0.1-RELEASE:stubs:14255"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@ActiveProfiles("test")
class PostIntegrationTest {

    private final RestfulClient restfulClient;

    @Autowired
    public PostIntegrationTest(RestfulClient restfulClient) {
        this.restfulClient = restfulClient;
    }

    @Test
    @DisplayName("get all posts")
    void getAllPosts() {
        List<Post> posts = restfulClient.getAllPosts();
        assertThat(posts).isNotNull().hasSize(3);
    }

    @Test
    @DisplayName("get all user posts")
    void getPostsByUserId() {
        List<Post> userPosts = restfulClient.getPostsByUserId(19);
        System.out.println(userPosts);
        assertThat(userPosts).isNotNull().hasSize(2);
    }

    @Test
    @DisplayName("get all user posts exception")
    void getPostsByUserIdException() {
        Exception exception = assertThrows(Exception.class, ()-> {
            restfulClient.getPostsByUserId(100);
        });
        System.err.println(exception.getMessage());
    }

    @Test
    @DisplayName("get a post by post_id and user_id")
    void getPostByUserIdAndPostId() {
        Post userPosts = restfulClient.getPostByUserIdAndPostId(4, 99);
        assertThat(userPosts).isNotNull();
        assertThat(userPosts.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("save a post")
    void savePost() {
        Post savePost = restfulClient.savePost(new Post(null, "Title", "Content", 1, null));
        assertThat(savePost).isNotNull();
        assertThat(savePost.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("save multiple posts")
    void saveMultiplePost() {
        List<Post> postList = restfulClient.saveAllPost(List.of(
                new Post(null, "Title", "Content", 1, null),
                new Post(null, "Title 1", "Content 1", 1, null)
        ));
        assertThat(postList).isNotNull().hasSize(2);
    }
}
