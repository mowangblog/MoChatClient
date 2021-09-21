package top.mowang.server;

import top.mowang.common.MessageType;
import top.mowang.common.User;
import top.mowang.common.Message;
import top.mowang.utils.Utility;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * MoChatClient
 * 客户端服务
 *
 * @author : Xuan Li
 * @date : 2021-09-20 20:21
 **/
@SuppressWarnings("all")
public class ClientService {

    private User user = new User();
    private Socket socket;

    /**
     * 从Properties配置文件里读取服务端地址并返回建立的连接
     * @return Socket
     * @throws Exception
     */
    public Socket initSocket() throws Exception{
        //从Properties配置文件里读取服务端地址并建立连接
        Properties properties = new Properties();
        properties.load(new FileReader("src/set.properties"));
        String serverIP = (String) properties.get("serverIP");
        String port = (String) properties.get("port");
        System.out.println("服务器地址:"+serverIP+":"+port);
        return new Socket(InetAddress.getByName(serverIP), Integer.parseInt(port));
    }

    /**
     * 获取在线用户列表
     * @param userName 用户名
     */
    public void getOnlineUserList(String userName){
        Message actionMessage = new Message();
        actionMessage.setMessageType(MessageType.MESSAGE_GET_ONLINE_LIST);
        actionMessage.setSender(userName);
        actionMessage.setReceiver("服务器");
        actionMessage.setSendTime(Utility.getTime());
        ClientConnectServerThread clientConnectServerThread = ManageClientThread.getClientConnectServerThread(userName);
        socket = clientConnectServerThread.getSocket();
        try {
            //向服务端发送请求用户列表
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(actionMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录接口
     * @param userName
     * @param passWord
     * @return
     */
    public boolean login(String userName,String passWord){
        boolean isLoginOk = false;
        user.setUserName(userName);
        user.setPassWord(passWord);

        try {
            socket = initSocket();
            //给服务器发送user对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message actionMessage = new Message();
            actionMessage.setMessageType(MessageType.MESSAGE_LOGIN);
            oos.writeObject(actionMessage);
            oos.writeObject(user);
            oos.flush();

            //读取服务器返回的信息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();

            if(message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                //登录成功
                //创建一个和服务端保持通信的线程
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                ManageClientThread.addClientConnectServerThread(userName,ccst);
                isLoginOk = true;
            }else {
                System.out.println(message);
                if (socket != null) {
                    socket.close();
                }
                isLoginOk = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isLoginOk;
    }

    public boolean register(String userName,String passWord){
        boolean isRegister = false;
        user.setUserName(userName);
        user.setPassWord(passWord);

        try {
            socket = initSocket();
            //给服务器发送user对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message actionMessage = new Message();
            actionMessage.setMessageType(MessageType.MESSAGE_REGISTER);
            oos.writeObject(actionMessage);
            oos.writeObject(user);
            oos.flush();

            //读取服务器返回的信息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();

            if(message.getMessageType().equals(MessageType.MESSAGE_REGISTER_SUCCEED)){
                //注册成功
                isRegister = true;
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRegister;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void logout(String userName) {
        //给服务器发送user对象
        try {
            Socket socket = ManageClientThread.getClientConnectServerThread(userName).getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //发送退出系统的消息
            Message actionMessage = new Message();
            actionMessage.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
            oos.writeObject(actionMessage);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
