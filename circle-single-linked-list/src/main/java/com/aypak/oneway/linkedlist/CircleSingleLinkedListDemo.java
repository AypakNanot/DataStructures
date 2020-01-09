package com.aypak.oneway.linkedlist;


/**
 * 单向链表
 * @author lihua
 */
public class CircleSingleLinkedListDemo {

    public static void main(String[] args) {

        CircleSingleLinkedList<String> list = new CircleSingleLinkedList<>(5);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.remove("E");
        System.out.println("正序打印");
        list.print();

        //获取链表有效数据长度
        System.out.println("单向链表有效数据长度：" + list.length());

    }


}
