package com.abc.contracts.consumer.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private LocalDateTime createdAt;

    public Post(String title, String content, Integer userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
