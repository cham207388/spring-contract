package com.abc.contracts.consumer.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
}
