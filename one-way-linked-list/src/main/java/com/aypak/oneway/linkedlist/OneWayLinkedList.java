package com.aypak.oneway.linkedlist;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单向链表的链接方向是单向的，对链表的访问要通过从头部开始，依序往下读取
 */
public class OneWayLinkedList<E> {

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
     * 链表的头
     * - 链表中第一个元素：head.next
     * - head.data == null
     */
    Node<E> head;

    /**
     * 链表的尾，为了每次添加时候不进行循环查找最后一个元素的位置
     * - last.next == null
     */
    private Node<E> last;

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
    public OneWayLinkedList() {
        this(Integer.MAX_VALUE);
    }

    /**
     * 初始化链表
     *
     * @param capacity 容量
     */
    public OneWayLinkedList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        //初始化头尾数据
        last = head = new Node<>(null);
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
     * 获取指定索引的值
     *
     * @param index 索引，索引从0开始
     * @return 元素数据
     */
    public E getIndex(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        Node<E> x = head.next;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x.data;
    }

    /**
     * 获取倒数指定索引的值
     *
     * @param index 倒数索引 从1开始，1：倒数第一个数据
     * @return 元素数据
     */
    public E getLastIndex(int index) {
        return getIndex(length() - index);
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
        Node temp = new Node<>(x);
        //先把新加的元素添加到最后next中
        last.next = temp;
        //再把最后一个元素指向添加后的最后一个元素
        last = temp;
        //有效元素length添加+1
        count.getAndIncrement();
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
                unlink(p, trail);
                count.getAndDecrement();
                return true;
            }
        }
        return false;
    }

    /**
     * 将内部节点p与上一节点断开连接
     */
    void unlink(Node<E> p, Node<E> trail) {
        p.data = null;
        trail.next = p.next;
        //如果最后一个元素被删除，则last需要指向上一节点数据
        if (last == p) {
            last = trail;
        }
    }

    /**
     * 打印链表数据
     */
    public void print() {
        if (head.next == null) {
            System.out.println("链表数据为空");
            return;
        }
        Node v = head.next;
        int index = 0;
        while (true) {
            System.out.println("Index: " + index + ",Data: " + v.data);
            if (v.next == null) {
                break;
            }
            index++;
            v = v.next;
        }
    }

    /**
     * 手写反转链表
     * - Head->A->B->C-D   ===>>  Head->D->C->B->A
     * - 取出Head的第一个有效元素 cur = head.next
     * - 如果cur不是空元素，则把当前元素的后一个元素所有都给保存起来，next = cur.next
     * - 将当前节点插入到rHead链表中的第一个节点位置， cur.next = rHead.next ; rHead.next = cur;
     * - 让下一次遍历的时候，是取next进行遍历，因为next是当前节点的后一个节点，当前节点已经插入到了rHead中。
     */
    public void reverseNode() {
        if (length() <= 1) {
            return;
        }
        //创建一个新队列头
        Node<E> rHead = new Node<>(null);
        //获取第一个数据
        Node<E> cur = head.next;
        //定义一个值来临时保存cur的下一个节点指向
        Node<E> next;
        while (cur != null) {
            //先暂时保存当前节点的下一个节点，因为后面需要使用
            next = cur.next;
            //下面两步是把节点插入到read的第一个H节点位置
            //将cur的下一个节点指向新的链表的最前端
            cur.next = rHead.next;
            //将cur 连接到新的链表上
            rHead.next = cur;
            //让cur后移
            cur = next;
        }
        head.next = rHead.next;
        return ;
    }

    /**
     * 利用栈进行反向打印
     */
    public void reversePrint() {
        if (head.next == null) {
            System.out.println("链表数据为空");
            return;
        }
        //创建一个栈，将各个节点压入栈
        Stack<Node<E>> stack = new Stack<>();
        Node<E> cur = head.next;
        //将链表的所有节点压入栈
        while (cur != null) {
            stack.push(cur);
            //cur后移，这样就可以压入下一个节点
            cur = cur.next;
        }
        int index = stack.size();
        //将栈中的节点进行打印,pop 出栈
        while (stack.size() > 0) {
            //stack的特点是先进后出
            System.out.println("Index: " + (--index) + ",Data: " + stack.pop().data);
        }
    }

    /**
     * 超出边界消息
     *
     * @param index 索引
     * @return 异常提示
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + length();
    }
}
