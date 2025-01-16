package com.abc.contracts.consumer.services;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void getAllPosts() {
        List<Post> posts = restfulClient.getAllPosts();
        assertThat(posts).isNotNull().hasSize(3);
    }

    @Test
    void getPostsByUserId() {
        List<Post> userPosts = restfulClient.getPostsByUserId(19);
        assertThat(userPosts).isNotNull().hasSize(2);
    }

    @Test
    void getPostsByUserIdException() {
        Exception exception = assertThrows(Exception.class, ()-> {
            restfulClient.getPostsByUserId(100);
        });
        System.err.println(exception.getMessage());
    }

    @Test
    void getPostByUserIdAndPostId() {
        Post userPosts = restfulClient.getPostByUserIdAndPostId(4, 99);
        assertThat(userPosts).isNotNull();
        assertThat(userPosts.getId()).isEqualTo(1);
    }

    @Test
    void savePost() {
        Post savePost = restfulClient.savePost(new Post(null, "Title", "Content", 1));
        assertThat(savePost).isNotNull();
        assertThat(savePost.getId()).isEqualTo(1);
    }

    @Test
    void saveMultiplePost() {
        List<Post> postList = restfulClient.saveAllPost(List.of(
                new Post(null, "Title", "Content", 1),
                new Post(null, "Title 1", "Content 1", 1)
        ));
        assertThat(postList).isNotNull().hasSize(2);
    }
}
