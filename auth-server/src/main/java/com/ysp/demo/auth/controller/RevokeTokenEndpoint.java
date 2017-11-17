/*
 * Copyright (c) 2017. www.wozaijia.com Inc. All rights reserved.
 */

package com.ysp.demo.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//@FrameworkEndpoint
@RestController
public class RevokeTokenEndpoint {

  private final ConsumerTokenServices consumerTokenServices;
  @Autowired
  private TokenStore tokenStore;

  @Autowired
  public RevokeTokenEndpoint(@Qualifier("consumerTokenServices") ConsumerTokenServices consumerTokenServices) {
    this.consumerTokenServices = consumerTokenServices;
  }

//  @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
//  @ResponseBody
//  public String revokeToken(String accessToken) {
//    if (consumerTokenServices.revokeToken(accessToken)) {
//      return "注销成功";
//    } else {
//      return "注销失败";
//    }
//  }

  @RequestMapping(value = "/token/clear", method = RequestMethod.DELETE)
  public String logout(@RequestParam("user_token") String token) {
    OAuth2AccessToken auth2AccessToken = tokenStore.readAccessToken(token);
    if (auth2AccessToken != null) {
      tokenStore.removeAccessToken(auth2AccessToken);
    }
    return "SUCCESS";
  }
}
