package com.syp.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author: SYP
 * @Date: 2020/8/19
 * @Description:
 */
public class MyPartition implements Partitioner{
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        System.out.println("enter");
        List<PartitionInfo> partitions = cluster.partitionsForTopic(s);
        int size = partitions.size();
        if(o == null){
            Random random = new Random();
            return random.nextInt(size);
        }
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
