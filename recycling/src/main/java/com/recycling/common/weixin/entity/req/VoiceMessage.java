package com.recycling.common.weixin.entity.req;
/**
 * @Title:VoiceMessage.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
/** 
 * 语音消息 
 *  
 * @author Caspar 
 *  
 */  
public class VoiceMessage extends BaseMessage {  
    /** 
     * 媒体ID 
     */  
    private String MediaId;  
    /** 
     * 语音格式 
     */  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
  
}  
