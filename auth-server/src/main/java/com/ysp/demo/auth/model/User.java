package com.ysp.demo.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

  private static final Long serialVersionUID = 2142838377311328467L;
  /**
   * 一定要加，不然无法创建 bean
   */
  @Id
  @GeneratedValue
  private Long id;

  //  @Column(name = "username")
  private String username;

  //  @Column(name = "password")
  private String password;

  //  @Column(name = "role")
  private String role;

  private boolean enabled;

  public User() {
  }

  public User(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> list = new ArrayList<>();
    GrantedAuthority ga = new SimpleGrantedAuthority(this.role);
    list.add(ga);
    return list;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
