package com.example.demo.mq.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * 生产者
 */
@Service("producer")
public class Producer {
    @Autowired
    private JmsMessagingTemplate template;

    //  目的地要发送的消息内容
    public void sendMessage(Destination destination, final String message) {
        template.convertAndSend(destination, message);  //调用convertAndSend方法，将信息发送进去
    }
}
