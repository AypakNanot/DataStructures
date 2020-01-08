package com.aypak.oneway.linkedlist;


/**
 * 单向链表
 * @author lihua
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {

        DoubleLinkedList<String> list = new DoubleLinkedList<>(20);
        list.add("A1");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");
        list.add("H");
        list.add("I");
        list.add("J");
        list.add("K");
        list.remove("A1");
        list.remove(0);
        list.add("你好", 0);
        list.add("不好", 10);
        list.update("J", "J-J");
        list.update(2, "szt");
        list.update(9, "szt11");
        System.out.println("正序打印");
        list.positivePrint();

        System.out.println("链表进行反转");
        list.transPrint();
        System.out.println("链表反转完成");

        //获取链表有效数据长度
        System.out.println("单向链表有效数据长度：" + list.length());


        //获取倒数第二个数据的值
        System.out.println("获取倒数第2个数据的值：" + list.getLastIndex(2));
        System.out.println("获取倒数第3个数据的值：" + list.getLastIndex(3));
        System.out.println("获取倒数第9个数据的值：" + list.getLastIndex(9));


        //获取指定index的值,获取第三个元素的值，索引从0开始
        System.out.println("获取第8个元素：" + list.get(7));
        System.out.println("获取第2个元素：" + list.get(1));
        System.out.println("获取第3个元素：" + list.get(3));

    }


}
