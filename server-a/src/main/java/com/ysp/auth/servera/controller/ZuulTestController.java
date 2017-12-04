package com.ysp.auth.servera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-11-20 12:45
 * @since V1.0
 */
@RequestMapping("/zuul")
@RestController
public class ZuulTestController {

  private static final Logger log = LoggerFactory.getLogger(ZuulTestController.class);

  @Autowired
  private OAuth2RestTemplate oAuth2RestTemplate;

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/main")
  public String index() {
    log.info("main:{}", "main");
    return "main";
  }

  @GetMapping("/ios-app-version")
  public String version() {
    log.warn("version:{}", "version");
    return "v4.3.4";
  }

  @GetMapping("/mall-ads")
  public String mallAds() {
    log.debug("mallAds:{}", "mallAds");
    return "mal-ads";
  }

  @GetMapping("/test")
  public String testHeader(@RequestHeader("user-agent") String userAgent) {
    log.error("testHeader:{}", "testHeader");
    return userAgent;
  }
}
