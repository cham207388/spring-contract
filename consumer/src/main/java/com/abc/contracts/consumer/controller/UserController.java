package com.abc.contracts.consumer.controller;

import java.util.List;

import com.abc.contracts.consumer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.contracts.consumer.domains.Post;
import com.abc.contracts.consumer.dto.UserRequest;
import com.abc.contracts.consumer.dto.UserResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Fetch all posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(userService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("/posts/users")
    public ResponseEntity<UserResponse> getUserPosts(@RequestParam Integer userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @PostMapping("/posts/users")
    public ResponseEntity<Integer> saveUser(@RequestBody UserRequest userInfo) {
        return new ResponseEntity<>(userService.saveUser(userInfo), HttpStatus.OK);
    }
}
