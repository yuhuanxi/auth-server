package com.ysp.auth.service.impls;

import com.ysp.auth.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JPAUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(JPAUserDetailsService.class);

  private final IUserRepository repository;

  @Autowired
  public JPAUserDetailsService(IUserRepository repository) {this.repository = repository;}

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    log.info("username:{}", s);

    return repository.findByUsername(s);
  }

}
