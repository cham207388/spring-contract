package com.abc.contracts.consumer.domains;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private Integer id;
    private String firstName;
    private List<Post> posts;
}
