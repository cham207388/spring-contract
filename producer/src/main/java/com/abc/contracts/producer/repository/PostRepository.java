package com.abc.contracts.producer.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.contracts.producer.domains.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
  
  List<Post> findAllByUserId(int userId);
  
  Post findByIdAndUserId(int id, int userId);
}
