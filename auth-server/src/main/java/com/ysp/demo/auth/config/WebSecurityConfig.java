package com.ysp.demo.auth.config;

import com.ysp.demo.auth.service.impls.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全配置.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-11-15 17:18
 * @since V1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JPAUserDetailsService jpaUserDetailsService;

  @Autowired
  public WebSecurityConfig(JPAUserDetailsService jpaUserDetailsService) {
    this.jpaUserDetailsService = jpaUserDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
			http
				.authorizeRequests()
                    .anyRequest()
                    .permitAll()
//                    .authenticated()
			.and()
				.csrf()
                    .disable();
			// @formatter:on
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(jpaUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
