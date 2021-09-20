package top.mowang.common;

import com.sun.deploy.net.HttpUtils;

/**
 * MoChatServer
 * 消息类型
 *
 * @author : Xuan Li
 * @date : 2021-09-20 19:46
 **/
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "200";
    String MESSAGE_LOGIN_FAIL = "400";
    String MESSAGE_LOGIN = "1";
    String MESSAGE_REGISTER= "2";
    String MESSAGE_REGISTER_FAIL= "401";
    String MESSAGE_REGISTER_SUCCEED= "20";
}
