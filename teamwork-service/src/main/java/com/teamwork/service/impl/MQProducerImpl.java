package com.teamwork.service.impl;

import com.teamwork.service.MQProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQProducerImpl implements MQProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /* (non-Javadoc)
     * @see com.stnts.tita.rm.api.mq.MQProducer#sendDataToQueue(java.lang.String, java.lang.Object)
     */
    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        amqpTemplate.convertAndSend(object);

    }
}
