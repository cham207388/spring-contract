package com.abc.contracts.consumer.domains;

import lombok.Data;

@Data
public class Post {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
}
