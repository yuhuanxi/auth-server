package com.ysp.demo.auth.service.impls;

import com.ysp.demo.auth.model.User;
import com.ysp.demo.auth.repository.IUserRepository;
import com.ysp.demo.auth.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService implements IUserService {

  private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final IUserRepository repository;

  @Autowired
  public UserService(IUserRepository userRepository) {this.repository = userRepository;}

  @Override
  public void createUser(User user) {

    User existing = repository.findByUsername(user.getUsername());
    Assert.isNull(existing, "user already exists: " + user.getUsername());

    String hash = ENCODER.encode(user.getPassword());
    user.setPassword(hash);

    repository.save(user);

    log.info("new user has been created: {}", user.getUsername());
  }

}
