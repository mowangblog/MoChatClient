package top.mowang.server;

import top.mowang.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * MoChatClient
 * 客户端和服务端通信线程
 *
 * @author : Xuan Li
 * @date : 2021-09-20 20:46
 **/
public class ClientConnectServerThread extends Thread{

    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * 建立线程和服务端保持通信
     */
    @Override
    public void run() {
        //无限循环保持和服务端通信
        while (true){
            System.out.println("客户端和服务端保持连接中");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送信息过来，线程会阻塞在这里
                Message message = (Message) ois.readObject();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("和服务器的连接中断,退出系统");
                break;
            }
        }
        System.exit(0);
    }
}
