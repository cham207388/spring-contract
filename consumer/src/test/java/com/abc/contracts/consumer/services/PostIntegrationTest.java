package com.abc.contracts.consumer.services;

import com.abc.contracts.consumer.client.RestfulClient;
import com.abc.contracts.consumer.domains.Post;
import com.abc.contracts.consumer.domains.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
        ids = {"com.abc.contracts:producer:0.0.1-RELEASE:stubs:14255"},
        stubsMode = StubRunnerProperties.StubsMode.REMOTE, 
        repositoryRoot = "https://maven.pkg.github.com/cham207388/spring-contract"
)
@ActiveProfiles("test")
class PostIntegrationTest {

    private final RestfulClient restfulClient;
    
    public PostIntegrationTest(RestfulClient restfulClient) {
        this.restfulClient = restfulClient;
    }

    @Test
    void getAllPosts() {
        PostResponse posts = restfulClient.getAllPosts();

        assertThat(posts).isNotNull();
        assertThat(posts.getPosts()).isNotNull().hasSize(3);
    }

    @Test
    void getPostsByUserId() {
        PostResponse userPosts = restfulClient.getPostsByUserId(1);
        assertThat(userPosts).isNotNull();
        assertThat(userPosts.getPosts()).isNotNull().hasSize(2);
    }

    @Test
    void getPostByUserIdAndPostId() {
        Post userPosts = restfulClient.getPostByUserIdAndPostId(1, 1);
        assertThat(userPosts).isNotNull();
        assertThat(userPosts.getId()).isEqualTo(1);
    }
}
