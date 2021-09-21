package top.mowang.server;

import top.mowang.common.Message;
import top.mowang.common.MessageType;
import top.mowang.utils.StreamUtil;
import top.mowang.utils.Utility;

import java.io.*;

/**
 * MoChatClient
 * 文件服务
 *
 * @author : Xuan Li
 * @date : 2021-09-21 14:22
 **/
public class FileServer {

    /**
     * 发送文件给其他用户
     */
    public static void sendFileUser(String path, String receiver,String sender) {
        File file = new File(path);
        if(!file.exists()){
            System.out.println("您指定的文件路径不存在，以取消发送");
            return;
        }
        try {
            byte[] bytes = StreamUtil.streamToByteArray(new FileInputStream(file));
            Message message = new Message();
            message.setSendTime(Utility.getTime());
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setMessageType(MessageType.MESSAGE_FILE_MES);
            message.setContent(file.getName());
            message.setBytes(bytes);
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    ManageClientThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("文件已发送");
    }
}
