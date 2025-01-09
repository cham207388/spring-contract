package com.abc.contracts.producer.controllers;

import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.domains.PostResponse;
import com.abc.contracts.producer.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/users")
    public ResponseEntity<PostResponse> getPostsByUserId(@RequestParam Integer userId) {
        return ResponseEntity.ok(postService.getPostsByUserid(userId));
    }

    @GetMapping("/posts/{id}/users/{userId}")
    public ResponseEntity<Post> getPostByUserIdAndPostId(@PathVariable Integer id, @PathVariable Integer userId) {
        return ResponseEntity.ok(postService.getPostByUserIdAndPostId(id, userId));
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> post(@RequestBody Post post) {
        return ResponseEntity.ok(postService.save(post));
    }
}