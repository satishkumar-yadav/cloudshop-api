package com.sky.cloudshop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sky.cloudshop_api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
