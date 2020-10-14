package com.syp.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author: SYP
 * @Date: 2020/8/19
 * @Description:
 */
public class SypKafkaConsumer2 extends Thread{

    KafkaConsumer<Integer,String> consumer;
    String topic;

    public SypKafkaConsumer2(String topic){
        this.topic = topic;
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.10:9092,192.168.56.11:9092,192.168.56.12:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "syp-consumer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "syp-gid-2");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        consumer = new KafkaConsumer<Integer, String>(properties);
    }

    @Override
    public void run() {
        consumer.subscribe(Collections.singleton(this.topic));
        while(true){
            ConsumerRecords<Integer, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            consumerRecords.forEach(record ->
                    System.out.println(record.key()+"->"+record.value()+"->"+record.offset()));
        }
    }

    public static void main(String[] args) {
        new SypKafkaConsumer2("test").start();
    }

}
