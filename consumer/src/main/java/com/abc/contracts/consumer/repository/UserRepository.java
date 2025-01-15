package com.abc.contracts.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.contracts.consumer.domains.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {

}
