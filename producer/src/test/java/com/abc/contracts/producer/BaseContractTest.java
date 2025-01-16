package com.abc.contracts.producer;

import com.abc.contracts.producer.config.TestConfig;
import com.abc.contracts.producer.controllers.PostController;
import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.services.PostService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static com.abc.contracts.producer.utils.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public abstract class BaseContractTest {

    private final PostController postController;
    private final PostService postService;

    public BaseContractTest(PostController postController, PostService postService) {
        this.postController = postController;
        this.postService = postService;
    }

    public BaseContractTest() {
        this.postService = Mockito.mock(PostService.class); // Mock PostService
        this.postController = new PostController(postService); // Use mocked PostService
    }

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(postController);

        when(postService.getAllPosts()).thenReturn(getAllPostsResponse());
        when(postService.getPostsByUserid(anyInt())).thenReturn(getPostsByUseridResponse());
        when(postService.getPostByUserIdAndPostId(anyInt(), anyInt())).thenReturn(getPostByUserIdAndPostIdResponse());
        when(postService.save(any(Post.class))).thenAnswer(i -> i.getArguments()[0]);
    }
}
