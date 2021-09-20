package top.mowang.common;

import java.io.Serializable;

/**
 * MoChatServer
 * 消息对象
 *
 * @author : Xuan Li
 * @date : 2021-09-20 19:45
 **/
public class Message implements Serializable {
    private static final long serialVersionUID = 1075825259812205550L;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接收者
     */
    private String receiver;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 消息类型
     */
    private String messageType;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Message() {
    }

    public Message(String sender, String receiver, String content, String sendTime, String messageType) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sendTime = sendTime;
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "收到一个消息，发送者:"+sender+"，发送时间："+sendTime+"，内容："+content;
    }
}
