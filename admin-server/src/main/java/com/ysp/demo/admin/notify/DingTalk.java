package com.ysp.demo.admin.notify;

import java.util.List;

/**
 * 功能描述.
 *
 * @author shipengfish
 * @version V1.0
 * @date 2017-12-01 14:36
 * @since V1.0
 */
public class DingTalk {
  /**
   * msgtype : text
   * text : {"content":"我就是我, 是不一样的烟火"}
   * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
   */

  private String msgtype;
  private TextBean text;
  private AtBean at;

  public String getMsgtype() { return msgtype;}

  public void setMsgtype(String msgtype) { this.msgtype = msgtype;}

  public TextBean getText() { return text;}

  public void setText(TextBean text) { this.text = text;}

  public AtBean getAt() { return at;}

  public void setAt(AtBean at) { this.at = at;}

  public static class TextBean {
    /**
     * content : 我就是我, 是不一样的烟火
     */

    private String content;

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content;}
  }

  public static class AtBean {
    /**
     * atMobiles : ["156xxxx8827","189xxxx8325"]
     * isAtAll : false
     */

    private boolean isAtAll;
    private List<String> atMobiles;

    public boolean isIsAtAll() { return isAtAll;}

    public void setIsAtAll(boolean isAtAll) { this.isAtAll = isAtAll;}

    public List<String> getAtMobiles() { return atMobiles;}

    public void setAtMobiles(List<String> atMobiles) { this.atMobiles = atMobiles;}
  }
}
