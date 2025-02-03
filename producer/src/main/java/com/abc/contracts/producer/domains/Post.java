package com.abc.contracts.producer.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private Integer userId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    public Post(int id, String title, String content, Integer userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

//    public Post(int id, String title, String content, Integer userId, LocalDateTime createdAt) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.userId = userId;
//        this.createdAt = createdAt;
//    }
}
