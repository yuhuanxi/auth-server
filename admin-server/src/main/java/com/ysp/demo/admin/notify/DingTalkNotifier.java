package com.ysp.demo.admin.notify;

import de.codecentric.boot.admin.event.ClientApplicationEvent;
import de.codecentric.boot.admin.notify.AbstractStatusChangeNotifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Notifier submitting events to Slack.
 *
 * @author Artur Dobosiewicz
 */
@Configuration
@ConfigurationProperties(prefix = "dingding")
public class DingTalkNotifier extends AbstractStatusChangeNotifier {
  private static final String DEFAULT_MESSAGE = "*#{application.name}* (#{application.id}) is *#{to.status}*";
  private static final String DOWN = "DOWN";
  private static final String OFFLINE = "OFFLINE";
  private final SpelExpressionParser parser = new SpelExpressionParser();
  private RestTemplate restTemplate = new RestTemplate();

  /**
   * Webhook url for Slack API (i.e. https://hooks.slack.com/services/xxx)
   */
  private URI webhookUrl;

  /**
   * Optional channel name without # sign (i.e. somechannel)
   */
  private String channel;

  /**
   * Optional emoji icon without colons (i.e. my-emoji)
   */
  private String icon;

  /**
   * Optional username which sends notification
   */
  private String username = "Spring Boot Admin";

  /**
   * Message formatted using Slack markups. SpEL template using event as root
   */
  private Expression message;

  public DingTalkNotifier() {
    this.message = parser.parseExpression(DEFAULT_MESSAGE, ParserContext.TEMPLATE_EXPRESSION);
  }

  @Override
  protected void doNotify(ClientApplicationEvent event) throws Exception {
    restTemplate.postForEntity(webhookUrl, createMessage(event), Void.class);
  }

  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private HttpEntity<Map<String, Object>> createMessage(ClientApplicationEvent event) {
    Map<String, Object> messageJson = new HashMap<>();
    messageJson.put("msgtype", "text");
    Map<String, Object> map = new HashMap<>();

    String text = getText(event);

    map.put("content", text);

    // 如果服务离线 或者 DOWN ,则需要 @ 开发人员
    if (text.contains(DOWN) || text.contains(OFFLINE)) {

    }

    messageJson.put("text", map);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(messageJson, headers);
  }

  public URI getWebhookUrl() {
    return webhookUrl;
  }

  public void setWebhookUrl(URI webhookUrl) {
    this.webhookUrl = webhookUrl;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMessage() {
    return message.getExpressionString();
  }

  public void setMessage(String message) {
    this.message = parser.parseExpression(message, ParserContext.TEMPLATE_EXPRESSION);
  }

  private String getText(ClientApplicationEvent event) {
    return message.getValue(event, String.class);
  }
}
