package com.abc.contracts.producer;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.abc.contracts.producer.controllers.PostController;
import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.services.PostService;
import static com.abc.contracts.producer.utils.TestUtils.getAllPostsResponse;
import static com.abc.contracts.producer.utils.TestUtils.getPostByUserIdAndPostIdResponse;
import static com.abc.contracts.producer.utils.TestUtils.getPostsByUseridResponse;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
public abstract class BaseContractTest {

    @Autowired
    private PostController postController;
    
    @MockitoBean
    private PostService postService;


    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(postController);

        when(postService.getAllPosts()).thenReturn(getAllPostsResponse());
        when(postService.getPostsByUserid(anyInt())).thenReturn(getPostsByUseridResponse());
        when(postService.getPostByUserIdAndPostId(anyInt(), anyInt())).thenReturn(getPostByUserIdAndPostIdResponse());
        when(postService.save(any(Post.class))).thenAnswer(i -> i.getArguments()[0]);
        when(postService.savePosts(any(List.class))).thenAnswer(i -> i.getArguments()[0]);
    }
}
