package com.example.demo.mq.test;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * 消费者
 */
@Service
public class Consumer {
    @JmsListener(destination="mytest.queue")   //使用JMSListener 监听在Producer的消息
    public void received(String message) {
        //打印接收到的信息
        System.out.println("customer接收到的信息为： " +message);
    }
}
