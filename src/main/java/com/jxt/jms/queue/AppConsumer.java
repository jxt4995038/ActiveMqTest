package com.jxt.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {
    //mq的地址
    private static final String url = "tcp://47.93.247.41:61616";
    //队列的名字
    private static final String queueName = "queue-test";

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //创建连接
        try {
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建会话  第一个参数是否使用事务处理  第二个参数 连接的应答模式 自动应答
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建目的地 （队列）
            Queue queue = session.createQueue(queueName);
            //创建消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //创建一个监听器
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        System.out.println("接收到了"+((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });


            //不能关闭连接 应为接收消息是异步的，连接关闭就接收不到消息了
            //connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
