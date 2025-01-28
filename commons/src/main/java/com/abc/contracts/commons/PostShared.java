package com.abc.contracts.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostShared {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private LocalDateTime createdAt;
}