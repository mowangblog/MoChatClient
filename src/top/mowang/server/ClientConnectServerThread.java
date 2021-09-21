package top.mowang.server;

import top.mowang.common.Message;
import top.mowang.common.MessageType;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * MoChatClient
 * 客户端和服务端通信线程
 *
 * @author : Xuan Li
 * @date : 2021-09-20 20:46
 **/
public class ClientConnectServerThread extends Thread {

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
        while (true) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送信息过来，线程会阻塞在这里
                Message message = (Message) ois.readObject();
                //根据服务端返回的数据消息进行相应的处理
                if (MessageType.MESSAGE_RET_ONLINE_LIST.equals(message.getMessageType())) {
                    String[] s = message.getContent().split(" ");
                    System.out.println("==========用户列表==========");
                    for (int i = 0; i < s.length; i++) {
                        System.out.println("用户" + (i + 1) + ":" + s[i]);
                    }
                } else if (MessageType.MESSAGE_COMMON_MES.equals(message.getMessageType())) {
                    System.out.println("收到一个消息，" + message);
                } else if (MessageType.MESSAGE_COMMON_PUBLIC_MES.equals(message.getMessageType())) {
                    System.out.println("收到一个群发消息，" + message);
                } else if (MessageType.MESSAGE_FILE_MES.equals(message.getMessageType())) {
                    //收到文件
                   saveFile(message);
                } else if (MessageType.MESSAGE_COMMON_SERVER_MES.equals(message.getMessageType())) {
                    System.out.println("收到一个系统消息，" + message);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("和服务器的连接中断,退出系统");
                break;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void saveFile(Message message) {
        File file = new File("src/file/" + message.getContent());
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(message.getBytes());
            bos.flush();
            System.out.println("收到一个文件，以保存在"+file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
