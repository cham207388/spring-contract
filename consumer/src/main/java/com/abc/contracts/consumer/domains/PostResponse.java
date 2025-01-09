package com.abc.contracts.consumer.domains;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private Integer userId;
    private List<Post> posts;
}