package com.ysp.demo.admin;

import com.ysp.demo.admin.notify.DingTalkNotifier;
import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.config.NotifierConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 功能描述.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-11-24 11:16
 * @since V1.0
 */
@Configuration
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableAdminServer
public class SpringBootAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootAdminApplication.class, args);
  }

  // end::configuration-spring-security[]
  @Profile("secure")
  // tag::configuration-spring-security[]
  @Configuration
  public static class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      // Page with login form is served as /login.html and does a POST on /login
      http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
      // The UI does a POST on /logout on logout
      http.logout().logoutUrl("/logout");
      // The ui currently doesn't support csrf
      http.csrf().disable();

      // Requests for the login page and the static assets are allowed
      http.authorizeRequests()
              .antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
              .permitAll();
      // ... and any other request needs to be authorized
      http.authorizeRequests().antMatchers("/**").authenticated();

      // Enable so that the clients can authenticate via HTTP basic for registering
      http.httpBasic();
    }
  }

  @Configuration
  @ConditionalOnProperty(prefix = "dingding.notify", name = "web-hook-url")
  @AutoConfigureBefore({NotifierConfiguration.NotifierListenerConfiguration.class,
          NotifierConfiguration.CompositeNotifierConfiguration.class})
  public static class DingTalkNotifierConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("dingding.notify")
    public DingTalkNotifier dingTalkNotifier() {
      return new DingTalkNotifier();
    }
  }

//  @Configuration
//  public static class NotifierConfig {
//
//    @Bean
//    @Primary
//    public RemindingNotifier remindingNotifier() {
//      RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(loggerNotifier()));
//      notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(1));
//      return notifier;
//    }
//
//    @Scheduled(fixedRate = 1_000L)
//    public void remind() {
//      remindingNotifier().sendReminders();
//    }
//
//    @Bean
//    public FilteringNotifier filteringNotifier(Notifier delegate) {
//      return new FilteringNotifier(delegate);
//    }
//
//    @Bean
//    public LoggingNotifier loggerNotifier() {
//      return new LoggingNotifier();
//    }
//  }
}
