package top.mowang.view;

import org.junit.Test;
import top.mowang.common.Message;
import top.mowang.common.MessageType;
import top.mowang.common.User;
import top.mowang.server.ClientConnectServerThread;
import top.mowang.server.ClientService;
import top.mowang.server.ManageClientThread;
import top.mowang.server.MessageService;
import top.mowang.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * MoChatClient
 * 客户端页面
 *
 * @author : Xuan Li
 * @date : 2021-09-20 19:57
 **/
public class ClientView {
    /**
     * 菜单循环条件
     */
    private boolean loop = true;
    /**
     * 用户输入内容
     */
    private String key = "";

    /**
     * 客户端服务
     */
    private ClientService clientService = new ClientService();

    /**
     * 消息服务
     */
    MessageService messageService = new MessageService();

    /**
     * 行为消息
     */
    Message actionMessage = new Message();


    public void mainMenu(){
        String userName;
        String passWord;
        while (loop) {
            System.out.println("==========MoChat通讯系统==========");
            System.out.println("\t\t\t1 登录系统");
            System.out.println("\t\t\t2 注册账号");
            System.out.println("\t\t\t9 退出系统");
            System.out.println("请选择菜单：");
            key = Utility.readString(1);

            switch (key){
                case "1":
                    System.out.println("请输入用户名：");
                    userName = Utility.readString(50);
                    System.out.println("请输入密码：");
                    passWord = Utility.readString(50);
                    if(clientService.login(userName,passWord)){
                        System.out.println("=========="+userName+"登录成功==========");
                        menu(userName);
                    }else {
                        System.out.println("==========登录失败==========");
                    }
                    break;
                case "2":
                    System.out.println("请输入用户名：");
                    userName = Utility.readString(50);
                    System.out.println("请输入密码：");
                    passWord = Utility.readString(50);
                    if(clientService.register(userName,passWord)){
                        System.out.println("=========="+userName+"注册成功==========");
                    }else {
                        System.out.println("=========="+userName+"已存在==========");
                        System.out.println("=========="+userName+"注册失败==========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("==========已退出系统==========");
    }

    public void menu(String userName){
        while (loop) {
            System.out.println("==========MoChat通讯系统（" + userName + "）==========");
            //二级菜单
            System.out.println("\t\t\t1 在线用户");
            System.out.println("\t\t\t2 私发消息");
            System.out.println("\t\t\t3 群发消息");
            System.out.println("\t\t\t4 发送文件");
            System.out.println("\t\t\t9 退出系统");
            System.out.println("请选择菜单：");
            key = Utility.readString(1);
            switch (key){
                case "1":
                    System.out.println("在线用户");
                    clientService.getOnlineUserList(userName);
                    break;
                case "2":
                    System.out.println("私发消息");
                    System.out.println("请输入你想要私聊的用户(在线):");
                    String receiver = Utility.readString(20);
                    System.out.println("请输入你要说的内容:");
                    String content = Utility.readString(100);
                    messageService.sendPrivateMessage(userName,receiver,content);
                    System.out.println(userName+"给"+receiver+"发送消息:"+content);
                    break;
                case "3":
                    System.out.println("群发消息");
                    break;
                case "4":
                    System.out.println("发送文件");
                    break;
                case "9":
                    loop = false;
                    clientService.logout(userName);
                    System.out.println("已退出系统");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new ClientView().mainMenu();
    }
}
