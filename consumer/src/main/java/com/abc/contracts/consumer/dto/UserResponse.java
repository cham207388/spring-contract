package com.abc.contracts.consumer.dto;

import lombok.Data;

import java.util.List;

import com.abc.contracts.consumer.domains.Post;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String fullName;
    private String email;
    private List<Post> posts;
}