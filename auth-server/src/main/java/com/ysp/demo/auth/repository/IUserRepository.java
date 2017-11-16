package com.ysp.demo.auth.repository;

import com.ysp.demo.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
