package com.teamwork.mq;

import com.teamwork.service.GroupChatService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupChatServerReceiver {

    @Autowired
    private GroupChatService groupChatService;

    /**
     * 接收客户端的群聊消息，保存后再群发给其他客户端
     */
    @RabbitListener(queues = "queue_group_chat_server")
    public void receive(String msg) {
        System.out.println("[x] Received " + msg);
        try {
            this.groupChatService.receivedMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
