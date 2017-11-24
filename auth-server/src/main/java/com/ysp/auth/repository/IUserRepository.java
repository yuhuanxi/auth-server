package com.ysp.auth.repository;

import com.ysp.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
