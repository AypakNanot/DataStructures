package com.aypak.oneway.linkedlist;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单向环形链表
 *
 * @author lihua
 */
public class CircleSingleLinkedList<E> {


    /**
     * Linked list node class
     *
     * @param <E> 定义数据对象类型
     */
    static class Node<E> {

        /**
         * 数据对象
         */
        E data;

        /**
         * 下一个：
         * - 后续节点
         * - null，表示没有后续节点(这是最后一个节点)
         */
        Node next;

        /**
         * 构造方法
         *
         * @param x 元素数据对象
         */
        Node(E x) {
            data = x;
        }
    }


    /**
     * 链表的第一个元素
     */
    Node<E> head;

    /**
     * 链表的尾，为了每次添加时候不进行循环查找最后一个元素的位置
     * - last.next == head;链表中的最后一个元素指向第一个元素
     */
    private Node<E> last;

    /**
     * 数到的位置
     */
    private Node<E> cur;

    /**
     * 链表最大容量
     */
    private final int capacity;

    /**
     * 记录当前链表中元素的个数(有效数据个数)
     */
    private final AtomicInteger count = new AtomicInteger();

    /**
     * 初始化链表，没有指定容量则默认为：Integer.MAX_VALUE
     */
    public CircleSingleLinkedList() {
        this(Integer.MAX_VALUE);
    }

    /**
     * 初始化链表
     *
     * @param capacity 容量
     */
    public CircleSingleLinkedList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        //初始化头尾数据
        cur = last = head = new Node<>(null);
        //让开始节点与下一个节点进行连接形成环，因为初始化只有head，只需要设置一次就形成了环
        head.next = last;
    }

    /**
     * 获取链表有效节点个数
     *
     * @return length
     */
    public int length() {
        return count.get();
    }

    /**
     * 添加元素数据到链表中，默认是往后添加
     *
     * @param x 元素数据
     */
    public void add(E x) {
        if (isFull()) {
//            throw new IllegalStateException("One-way LinkedList full");
            System.out.println("链表满，不能添加数据");
            return;
        }
        //如果是第一个元素，则直接添加到head中
        if (head.data == null) {
            head.data = x;
            count.getAndIncrement();
            return;
        }
        Node temp = new Node<>(x);
        //先把新加的元素添加到最后next中
        last.next = temp;
        //再把最后一个元素指向添加后的最后一个元素
        last = temp;
        last.next = head;
        //有效元素length添加+1
        count.getAndIncrement();
    }

    /**
     * 每次数个次数
     *
     * @param m 每次数的次数，指向其实是往后数m-1次
     */
    public E count(int m) {
        for (int i = 1; i < m; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    /**
     * 将当前指向下一个目标，因为此目标即将被删除
     */
    public void next() {
        cur = cur.next;
    }

    /**
     * 链表是否满
     *
     * @return true：满，false：未满
     */
    public boolean isFull() {
        return count.get() >= capacity;
    }

    /**
     * 删除数据
     *
     * @param o 元素数据
     * @return true：删除成功，false：未删除成功
     */
    public boolean remove(E o) {
        if (o == null) {
            return false;
        }
        //trail 为p的上一节点元素，断开连接的时候需要此节点的引用，p为需要判断是否是删除的元素
        for (Node<E> trail = head, p = trail.next; p != null; trail = p, p = p.next) {
            if (o.equals(p.data)) {
                //删除数据弱引用
                p.data = null;
                trail.next = p.next;
                //如果最后一个元素被删除，则last需要指向上一节点数据
                if (last == p) {
                    last = trail;
                }
                //如果是第一个元素被删除，则head需要指向下一个节点
                if (head == p) {
                    head = p.next;
                }
                //为了最后删除的时候不进行环指向
                p.next = null;
                count.getAndDecrement();
                return true;
            }
        }
        return false;
    }

    /**
     * 打印链表数据
     */
    public void print() {
        if (head.data == null && head.next == null) {
            System.out.println("链表数据为空");
            return;
        }
        Node<E> v = head;
        for (int i = 0; i < length(); i++) {
            System.out.println("Index: " + i + ",Data: " + v.data);
            v = v.next;
        }
    }

}
