package com.syp.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: SYP
 * @Date: 2020/8/6
 * @Description:
 * 约瑟夫环问题（丢手绢问题）：
 * N个人围成一圈，从第一个开始报数，第M个将被杀掉，最后剩下一个，其余人都将被杀掉。例如N=6，M=5，被杀掉的顺序是：5，4，6，2，3。
 * 单向循环链表
 * 注意点：
 * 删除节点时，如果删除的是头结点，要维护新的头结点，并且尾结点的下一个节点也要维护
 *
 */


public class Josephus {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        for(int i=0; i<20; i++){
            map.put("a"+i,i);
        }

//        map.put("b",1);
//        map.put("c",1);
//        int[]  a = new int[]{0,1,2,3,4,5,6,7,8,9};
//        CircleLinkedList circleLinkedList = new CircleLinkedList();
//        for(int i=0; i<a.length; i++){
//            circleLinkedList.add(a[i]);
//        }
//        System.out.println(josephs(circleLinkedList,3));
    }

    public static int josephs(CircleLinkedList<Node> list, int n){
        Node node = list.head;
        int i = 0;
        while(node != null && node.next != node){
            i++;
            if(i % n == 0){
                list.remove(node);
                System.out.println(node.index+"被移除");
                i = 0;
            }
            node = node.next;
        }
        return node.index;
    }
}

//单向循环链表
class CircleLinkedList<T>{
    Node head;
    public void add(int n){
        Node node = new Node(n);
        add(node);
    }
    //尾部插入
    public void add(Node newNode){
        if(head == null){
            head = newNode;
            newNode.next = head;
        }else{
            Node  node = head;
            while(node.next != head){
                node = node.next;
            }
            node.next = newNode;
            newNode.next = head;
        }
    }

    public void remove(Node node){
        if(null == head || null == node){
            return;
        }

        Node p = head;
        while(p != null && p.next != node){
            p = p.next;
        }
        p.next = p.next.next;

        //删除的如果是头结点的话，要记得维护新的头结点
        if(head == node){
            head = head.next;
        }
    }

}

class Node{
    //位置
    int index;
    //是否被选中
    boolean alive = false;

    Node next;

    public Node(int index) {
        this.index = index;
    }
}
