package com.ysp.auth.controller;

import com.ysp.auth.model.User;
import com.ysp.auth.service.impls.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {this.userService = userService;}

  @GetMapping("/test")
  public String test(){
    return "HelloWorld";
  }

  @RequestMapping(value = "/current", method = RequestMethod.GET)
  public Principal getUser(Principal principal, HttpServletRequest httpRequest) {

    log.info("principal:{}", principal);

    log.info("httpQueryString:{}", httpRequest.getQueryString());

    return principal;
  }

  @PreAuthorize("#oauth2.hasScope('server')")
  @RequestMapping(method = RequestMethod.POST)
  public void createUser(@Valid @RequestBody User user) {
    userService.createUser(user);
  }

}
