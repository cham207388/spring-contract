package com.abc.contracts.producer.controllers;

import java.util.List;

import com.abc.contracts.producer.domains.Post;
import com.abc.contracts.producer.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/users")
    public ResponseEntity<List<Post>> getPostsByUserId(@RequestParam Integer userId) {
        return ResponseEntity.ok(postService.getPostsByUserid(userId));
    }

    @GetMapping("/posts/{id}/users/{userId}")
    public ResponseEntity<Post> getPostByUserIdAndPostId(@PathVariable Integer id, @PathVariable Integer userId) {
        return ResponseEntity.ok(postService.getPostByUserIdAndPostId(id, userId));
    }

    @PostMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> post(@RequestBody Post post) {

        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }

    @PostMapping("/posts/all")
    public ResponseEntity<List<Post>> posts(@RequestBody List<Post> posts) {
        return new ResponseEntity<>(postService.savePosts(posts), HttpStatus.CREATED);
    }
}
