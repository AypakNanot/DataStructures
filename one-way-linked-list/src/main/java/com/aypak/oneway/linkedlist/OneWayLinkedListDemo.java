package com.aypak.oneway.linkedlist;


/**
 * 单向链表
 */
public class OneWayLinkedListDemo {

    public static void main(String[] args) {

        OneWayLinkedList<String> list = new OneWayLinkedList<>(5);
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.remove("D");
        System.out.println("正序打印");
        list.print();

        System.out.println("链表进行反转");
        list.reverseNode();
        System.out.println("链表反转完成");

        System.out.println("利用栈特效先进后出反向打印数据");
        list.reversePrint();
        //获取链表有效数据长度
        System.out.println("单向链表有效数据长度：" + list.length());
        //获取指定index的值,获取第三个元素的值，索引从0开始
        System.out.println("获取索引为2元素的值：" + list.getIndex(2));

        //获取倒数第二个数据的值
        System.out.println("获取倒数第2个数据的值：" + list.getLastIndex(2));

    }


}
