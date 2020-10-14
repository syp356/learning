package com.syp.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: SYP
 * @Date: 2020/8/22
 * @Description:
 * 设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。缓存应该从键映射到值(允许你插入和检索特定键对应的值)，并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。
 * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。

示例:

LRUCacheWithLinkedHashMap cache = new LRUCacheWithLinkedHashMap( 2 //缓存容量  );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // 返回  1
cache.put(3, 3);    // 该操作会使得密钥 2 作废
cache.get(2);       // 返回 -1 (未找到)
cache.put(4, 4);    // 该操作会使得密钥 1 作废
cache.get(1);       // 返回 -1 (未找到)
cache.get(3);       // 返回  3
cache.get(4);       // 返回  4
 */
public class LRUCacheWithLinkedHashMap {
    int capacity;//容量
    Map<Integer, Integer> cache;
    public LRUCacheWithLinkedHashMap(int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap<>(capacity);
    }

    public int get(int key) {
        if(!cache.containsKey(key)){
            return -1;
        }
        Integer value = cache.remove(key);
        cache.put(key,value);
        return value;
    }

    public void put(int key, int value) {
        //如果key已经存在，则删除旧位置的数据，然后加到链表尾部
        if(cache.containsKey(key)){
            cache.remove(key);
            cache.put(key,value);
            System.out.println("放入"+key + ": "+value);
            return;
        }
        //如果缓存大小超过限定值，则删除最近最少使用的，即链表第一个 然后把数据添加到末尾
        if(cache.size() >= this.capacity){
            Map.Entry<Integer, Integer> first = cache.entrySet().iterator().next();
            cache.remove(first.getKey());
            System.out.println("移除"+first.getKey() + ": "+first.getValue());
        }
        cache.put(key,value);
        System.out.println("放入"+key + ": "+value);
    }

}
