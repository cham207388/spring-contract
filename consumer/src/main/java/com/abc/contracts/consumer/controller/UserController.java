package com.abc.contracts.consumer.controller;

import com.abc.contracts.consumer.domains.PostResponse;
import com.abc.contracts.consumer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Fetch all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts() {
        return new ResponseEntity<>(userService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("/posts/users")
    public ResponseEntity<PostResponse> getUserPosts(@RequestParam Integer userId) {
        return new ResponseEntity<>(userService.getUserPosts(userId), HttpStatus.OK);
    }
}
