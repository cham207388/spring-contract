package com.abc.contracts.consumer.controller;

import java.util.List;

import com.abc.contracts.shared.domains.Post;
import com.abc.contracts.consumer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abc.contracts.consumer.dto.UserRequest;
import com.abc.contracts.consumer.dto.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // Fetch all posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(userService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserPosts(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> saveUser(@RequestBody UserRequest userInfo) {
        return new ResponseEntity<>(userService.saveUser(userInfo), HttpStatus.CREATED);
    }

    @PostMapping("/rabbit")
    public ResponseEntity<Integer> saveUserRabbit(@RequestBody UserRequest userInfo) {
        return new ResponseEntity<>(userService.saveUserRabbit(userInfo), HttpStatus.CREATED);
    }
}
