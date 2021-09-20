package top.mowang.common;

import java.io.Serializable;

/**
 * MoChatServer
 * 用户对象
 *
 * @author : Xuan Li
 * @date : 2021-09-20 19:45
 **/
public class User implements Serializable {
    private static final long serialVersionUID = -5266891793422958636L;
    private String UserName;
    private String PassWord;

    public User() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public User(String userName, String passWord) {
        UserName = userName;
        PassWord = passWord;
    }
}
