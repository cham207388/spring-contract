package com.abc.contracts.consumer.services;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.abc.contracts.consumer.domains.Post;
import com.abc.contracts.consumer.domains.UserInfo;
import com.abc.contracts.consumer.dto.UserRequest;
import com.abc.contracts.consumer.dto.UserResponse;
import com.abc.contracts.consumer.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PostsService postsService;
    private final UserRepository userRepository;

    public List<Post> getAllPost() {
        return postsService.getAllPosts();
    }

    public UserResponse getUser(Integer id) {
        UserInfo userInfo = userRepository.findById(id).orElse(null);
        if (userInfo == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        response.setId(userInfo.getId());
        response.setFullName(userInfo.getFullName());
        response.setEmail(userInfo.getEmail());
        response.setPosts(postsService.getPostsByUserId(id));
        return response;
    }

    @Transactional
    public int saveUser(UserRequest request) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(request.getFullName());
        userInfo.setEmail(request.getEmail());
        UserInfo savedUser = userRepository.save(userInfo);
        List<Post> posts = request.getPosts();
        posts.forEach(post -> post.setUserId(userInfo.getId()));
        postsService.saveAllPost(posts);
        return savedUser.getId();
    }
}
