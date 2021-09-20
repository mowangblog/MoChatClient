package top.mowang.server;

import top.mowang.common.MessageType;
import top.mowang.common.User;
import top.mowang.common.Message;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
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
}
