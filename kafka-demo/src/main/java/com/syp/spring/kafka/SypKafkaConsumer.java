package com.syp.spring.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;

/**
 * @Author: SYP
 * @Date: 2020/8/19
 * @Description:
 */
@Component
public class SypKafkaConsumer{

    @KafkaListener(topics = {"test"})
    public void listener(ConsumerRecord record){
        Optional msg = Optional.ofNullable(record.value());
        if(msg.isPresent()){
            System.out.println(msg.get());
        }
    }
}
