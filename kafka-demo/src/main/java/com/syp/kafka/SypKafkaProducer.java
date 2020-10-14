package com.syp.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: SYP
 * @Date: 2020/8/19
 * @Description:
 */
public class SypKafkaProducer extends Thread{

    KafkaProducer<Integer,String> producer;
    String topic;

    public SypKafkaProducer(String topic){
        this.topic = topic;
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.10:9092,192.168.56.11:9092,192.168.56.12:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"syp-consumer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,MyPartition.class);

        producer = new KafkaProducer<Integer, String>(properties);
    }

    @Override
    public void run() {
        int num = 0;
        String msg = "mymsg"+num;
        while(num < 20){
            try{
                RecordMetadata recordMetadata = producer.send(new ProducerRecord<Integer, String>(topic,msg)).get();
                System.out.println(recordMetadata.offset()+"->"+recordMetadata.partition()+"->"+recordMetadata.topic());
                TimeUnit.SECONDS.sleep(2);
                ++num;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new SypKafkaProducer("test").start();
    }


}
