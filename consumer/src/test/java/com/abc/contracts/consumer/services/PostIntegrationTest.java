package com.abc.contracts.consumer.services;

import com.abc.contracts.consumer.domains.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
        ids = {"com.abc.contracts:producer:0.0.1-RELEASE:stubs:14255"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class PostIntegrationTest {

    @Autowired
    private PostsService postsService;

    @Test
    void fetchPosts() {
        PostResponse recommendation = postsService.fetchPosts(Optional.empty());

        assertThat(recommendation).isNotNull();
        assertThat(recommendation.posts()).isNotNull().hasSizeGreaterThan(0);
    }

    @Test
    void fetchUserPosts() {
        PostResponse recommendation = postsService.fetchPosts(Optional.of(1));
        assertThat(recommendation).isNotNull();
        assertThat(recommendation.posts()).isNotNull().hasSize(2);
    }
}
