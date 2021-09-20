package top.mowang.common;

/**
 * MoChatServer
 * 消息类型
 *
 * @author : Xuan Li
 * @date : 2021-09-20 19:46
 **/
public interface MessageType {
    /**
     * 登录成功
     */
    String MESSAGE_LOGIN_SUCCEED = "200";

    /**
     * 登录失败
     */
    String MESSAGE_LOGIN_FAIL = "400";

    /**
     * 注册失败
     */
    String MESSAGE_REGISTER_FAIL= "401";

    /**
     * 注册成功
     */
    String MESSAGE_REGISTER_SUCCEED= "201";

    /**
     * 登录行为
     */
    String MESSAGE_LOGIN = "1";

    /**
     * 注册行为
     */
    String MESSAGE_REGISTER= "2";

    /**
     * 普通消息
     */
    String MESSAGE_COMMON_MES = "3";

    /**
     * 要求返回在线用户列表
     */
    String MESSAGE_GET_ONLINE_LIST = "4";

    /**
     * 返回在线用户列表
     */
    String MESSAGE_RET_ONLINE_LIST = "5";

    /**
     * 客户端请求退出
     */
    String MESSAGE_CLIENT_EXIT = "40";
}
