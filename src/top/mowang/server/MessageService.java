package top.mowang.server;

import top.mowang.common.Message;
import top.mowang.common.MessageType;
import top.mowang.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MoChatClient
 * 消息服务
 *
 * @author : Xuan Li
 * @date : 2021-09-21 02:16
 **/
@SuppressWarnings("all")
public class MessageService {
    /**
     * 私发消息
     */
    public void sendPrivateMessage(String sender,String receiver,String content) {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMMON_MES);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSendTime(Utility.getTime());
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    ManageClientThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     */
    public void sendPublicMessage(String userName, String content) {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMMON_PUBLIC_MES);
        message.setSender(userName);
        message.setReceiver("所有人");
        message.setContent(content);
        message.setSendTime(Utility.getTime());
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    ManageClientThread.getClientConnectServerThread(userName).getSocket().getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
