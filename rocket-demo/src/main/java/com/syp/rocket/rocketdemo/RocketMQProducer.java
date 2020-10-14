package com.syp.rocket.rocketdemo;

import com.sun.xml.internal.ws.api.model.MEP;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @Author: SYP
 * @Date: 2020/8/20
 * @Description:
 */
public class RocketMQProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("syp_producer_group");
        producer.setNamesrvAddr("192.168.56.10:9876");
        producer.start();
        int num = 0;
        while(num <20){
            Message message = new Message("syp_test_topic","tags",("Hello rocketMq"+num).getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
            num++;
        }

    }
}
