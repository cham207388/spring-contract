package com.abc.contracts.producer.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
}
