package com.ysp.auth.servera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-11-15 20:14
 * @since V1.0
 */
@RestController
public class UserController {

  private final RestTemplate restTemplate;

  @Autowired
  public UserController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

  @GetMapping(value = "/")
  public String index() {
    return "index";
  }

  @PostMapping("/user")
  public String saveUser(User user) {

    if (user == null) {
      return "FAILURE";
    }

    // TODO 校验 username password role 是否为空，如果 role 为空，则 role 为 normal

    if (user.getRole() == null) {
      user.setRole("normal");
    }

    // 调用授权服务，创建账户
    restTemplate.postForObject("http://localhost:5000/uaa/users", user, String.class);

    return "SUCCESS";
  }

  /**
   * 退出登录，清空 token
   *
   * @param userToken
   * @return
   */
  @DeleteMapping("/user")
  public String logout(@RequestParam("user_token") String userToken) {

    restTemplate.delete("http://localhost:5000/uaa/users/token/clear?user_token=" + userToken);

    return "SUCCESS";
  }

  /**
   * 只允许具有 super 权限的用户访问
   *
   * @param name
   * @return
   */
  @PreAuthorize("hasAuthority('super')")
  @RequestMapping(value = "/super", method = RequestMethod.GET)
  public String roleSuper(String name) {
    return "Hello World super" + name;
  }

  /**
   * 只允许具有 normal 权限的用户访问
   *
   * @param name
   * @return
   */
  @PreAuthorize("hasAuthority('normal')")
  @RequestMapping(value = "/normal", method = RequestMethod.GET)
  public String roleNormal(String name) {
    return "Hello World NORMAL" + name;
  }

  /**
   * 允许 super 及 normal 访问
   *
   * @param name
   * @return
   */
//  @PreAuthorize("hasAuthority('super') or hasAuthority('normal')")
  @PreAuthorize("hasAnyAuthority('super','normal')")
  @RequestMapping(value = "/super-normal", method = RequestMethod.GET)
  public String roleSuperOrNormal(String name) {
    return "Hello World SUPER OR NORMAL" + name;
  }

  /**
   * scope 为 server 的用户可以访问
   *
   * @param name
   * @return
   */
  @PreAuthorize("#oauth2.hasScope('server')")
  @RequestMapping(value = "/scope", method = RequestMethod.GET)
  public String scopeTest(String name) {
    return "Hello World SUPER OR NORMAL" + name;
  }

}

class User {

  private String username;

  private String password;

  private String role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
