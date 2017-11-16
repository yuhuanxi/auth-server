package com.ysp.demo.servera;

import com.ysp.demo.servera.service.CustomUserInfoTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@SpringBootApplication
@ServletComponentScan
@EnableDiscoveryClient
@EnableAsync
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServiceApplication extends ResourceServerConfigurerAdapter {

  private final ResourceServerProperties sso;

  @Autowired
  public ServiceApplication(ResourceServerProperties sso) {this.sso = sso;}

  public static void main(String[] args) {
    SpringApplication.run(ServiceApplication.class, args);
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.client")
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    return new ClientCredentialsResourceDetails();
  }

//  @Bean
//  public RequestInterceptor oauth2FeignRequestInterceptor() {
//    return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(),
//                                             clientCredentialsResourceDetails());
//  }

  @Bean
  public OAuth2RestTemplate clientCredentialsRestTemplate() {
    return new OAuth2RestTemplate(clientCredentialsResourceDetails());
  }

  @Bean
  public ResourceServerTokenServices tokenServices() {
    return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/user")
            .permitAll()
//            .antMatchers()
//            .access("#oauth2.hasScope('server')")
            .antMatchers(HttpMethod.GET)
            .permitAll()
            .anyRequest().authenticated();
  }
}