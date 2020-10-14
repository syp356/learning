package com.syp.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: SYP
 * @Date: 2020/8/22
 * @Description:
 */
public class LRUCacheWithCustomDoubleList {
    int capacity;
    Map<Integer,CustomDoubleList.Node> map;
    CustomDoubleList cache;

    public LRUCacheWithCustomDoubleList(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        cache = new CustomDoubleList();
    }

    public int get(int key){
        if(!map.containsKey(key)){
            return -1;
        }
        //移动到最后
        CustomDoubleList.Node node = map.get(key);
        cache.addTail(node);
        System.out.println("获取元素 "+node.key);
        return node.value;
    }

    public void put(int key, int value){
        if(get(key) != -1){
            map.get(key).value = value;
            //如果已经存在，交换位置
            return;
        }
        CustomDoubleList.Node node = new CustomDoubleList.Node(key,value);
        //如果缓存数量大于指定容量，则移除最近最久未使用的数据，即第一个元素头节点
        if(map.size() >= capacity){
            System.out.println("移除元素 "+cache.head.key);
            map.remove(cache.head.key);
            cache.head = cache.head.next;
        }

        map.put(key,node);
        //新节点添加到链表最后
        cache.addTail(node);
//        node.pre = cache.tail.pre;
//        cache.tail.pre.next = node;
//        node.next = cache.tail;
    }

}

class CustomDoubleList{
    Node head;
    Node tail;
    int size;

    public CustomDoubleList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     *@Author: SYP
     *@Date: 2020/9/2
     *@Description:
     * 在链表结尾插入元素，这里使用无头结点的链表，在进行添加和删除操作时需要对头结点和尾结点做特殊处理，为了简化，可以使用哨兵机制
     * 即默认初始化一个头结点，和尾结点，不做数据存储，从第二个节点开始存储数据
     */
    public void addTail(CustomDoubleList.Node node){
        if(head == null || head == null){
            head = node;
            tail = node;
        }else{
            tail.next = node;
            node.pre = tail;
            tail = node;
        }
        size++;
        System.out.println("添加元素 "+node.key);
    }

    static class Node{
        Node pre;
        Node next;
        int key;
        int value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

