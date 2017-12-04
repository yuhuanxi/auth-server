package com.ysp.demo.admin.notify;

import com.wozaijia.util.JsonUtil;
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
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "dingding.notify")
public class DingTalkNotifier extends AbstractStatusChangeNotifier {
  private static final String DEFAULT_MESSAGE = "*#{application.name}* (#{application.id}) is *#{to.status}*";
  private final SpelExpressionParser parser = new SpelExpressionParser();
  private RestTemplate restTemplate = new RestTemplate();
  private String[] needAtState;
  private URI webHookUrl;
  private String accessToken;

  private String username = "Spring Boot Admin";
  private List<String> mobiles;

  private Expression message;

  public DingTalkNotifier() {
    this.message = parser.parseExpression(DEFAULT_MESSAGE, ParserContext.TEMPLATE_EXPRESSION);
  }

  @Override
  protected void doNotify(ClientApplicationEvent event) throws Exception {
    restTemplate.postForEntity(webHookUrl + "?access_token=" + accessToken, createMessage(event), Void.class);
  }

  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private HttpEntity<String> createMessage(ClientApplicationEvent event) {

    DingTalk dingTalk = new DingTalk();

    DingTalk.TextBean textBean = new DingTalk.TextBean();
    String text = getText(event);

    textBean.setContent(text);
    dingTalk.setText(textBean);

    dingTalk.setMsgtype(DingTalk.MsgTypeEnum.TEXT.getType());

    DingTalk.AtBean atBean = new DingTalk.AtBean();

    atBean.setAtMobiles(mobiles);

    for (String s : needAtState) {
      if (text.contains(s)) {
        atBean.setIsAtAll(true);
        break;
      }
    }

    dingTalk.setAt(atBean);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(JsonUtil.toJson(dingTalk), headers);
  }

  public URI getWebHookUrl() {
    return webHookUrl;
  }

  public void setWebHookUrl(URI webHookUrl) {
    this.webHookUrl = webHookUrl;
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

  public String[] getNeedAtState() {
    return needAtState;
  }

  public void setNeedAtState(String[] needAtState) {
    this.needAtState = needAtState;
  }

  public List<String> getMobiles() {
    return mobiles;
  }

  public void setMobiles(List<String> mobiles) {
    this.mobiles = mobiles;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
