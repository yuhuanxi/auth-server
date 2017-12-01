package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-12-01 11:34
 * @since V1.0
 */
@RestController
public class T {

  @Autowired
  private RestTemplate restTemplate;

  @PostMapping("/ding")
  public String ding() {
    String url = "https://free-api.heweather.com/v5/forecast?city=CN101080101&key=5c043b56de9f4371b0c7f8bee8f5b75e";
    String resp = restTemplate.getForObject(url, String.class);
    System.out.println(resp);

    return "OK";
  }

  @PostMapping("/talk")
  public String dingTalk() {
    String url = "https://oapi.dingtalk" +
            ".com/robot/send?access_token=811ccb2a35520a3e0b83040f215005f0a584103b99833e2727b9c6d534d610a7";
    restTemplate.postForEntity(url, createMessage(), Void.class);
    return "OK";
  }

  private HttpEntity<Map<String, Object>> createMessage() {
    Map<String, Object> messageJson = new HashMap<>();
    messageJson.put("access_token", "811ccb2a35520a3e0b83040f215005f0a584103b99833e2727b9c6d534d610a7");
    messageJson.put("msgtype", "text");
    Map<String, Object> map = new HashMap<>();
    map.put("content", "今天周五");
    messageJson.put("text", map);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(messageJson, headers);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
