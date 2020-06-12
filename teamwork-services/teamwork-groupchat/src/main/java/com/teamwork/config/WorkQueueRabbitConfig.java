package com.teamwork.config;

import com.teamwork.mq.GroupChatServerReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkQueueRabbitConfig {
    @Bean
    public Queue groupChatQueue() {
        return new Queue("queue_group_chat_server");
    }

    private static class ReceiverConfig {
        @Bean
        public GroupChatServerReceiver receiver() {
            return new GroupChatServerReceiver();
        }
    }
}
