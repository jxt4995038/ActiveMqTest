package com.jxt.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppProducer {
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
            //创建生产者
            MessageProducer producer = session.createProducer(queue);

            for (int i = 0; i < 100; i++) {
                //创建消息并由生产者发送到队列中
                TextMessage textMessage = session.createTextMessage("hello,"+i);
                producer.send(textMessage);

                System.out.println("发送第"+i+"次消息"+textMessage.getText());
            }

            //关闭连接
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
