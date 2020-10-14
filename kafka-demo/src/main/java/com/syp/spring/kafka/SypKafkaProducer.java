package com.syp.spring.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: SYP
 * @Date: 2020/8/19
 * @Description:
 */
@Component
public class SypKafkaProducer{

    KafkaTemplate<String,String> kafkaTemplate;
    public void send() {
       kafkaTemplate.send("test","msgkey","msg");
    }
}
