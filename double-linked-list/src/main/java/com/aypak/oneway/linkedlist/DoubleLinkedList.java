package com.aypak.oneway.linkedlist;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 双向链表，又称为双链表，是链表的一种，它的每个数据结点中都有两个指针，分别指向直接后继和直接前驱。
 * 所以，从双向链表中的任意一个结点开始，都可以很方便地访问它的前驱结点和后继结点。一般我们都构造双向循环链表
 *
 * @author lihua
 */
public class DoubleLinkedList<E> {

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
        Node<E> next;

        /**
         * 下一个：
         * - 上一节点
         * - null，表示第一个head节点
         */
        Node<E> pre;

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
     * - head.pre == null
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
    public DoubleLinkedList() {
        this(Integer.MAX_VALUE);
    }

    /**
     * 初始化链表
     *
     * @param capacity 容量
     */
    public DoubleLinkedList(int capacity) {
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
     * 获取指定索引的值，正序查询
     *
     * @param index 索引，索引从0开始
     * @return 元素数据
     */
    public E getByHead(int index) {
        checkIndex(index);
        Node<E> x = head.next;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x.data;
    }

    /**
     * 获取指定索引的值，索引为反序，倒数
     *
     * @param index 倒数索引，从1开始
     * @return 元素数据
     */
    public E getByLast(int index) {
        checkIndex(index - 1);
        Node<E> x = last;
        for (int i = 1; i < index; i++) {
            x = x.pre;
        }
        return x.data;
    }

    /**
     * 获取指定位置元素
     *
     * @param index 索引 从0开始
     * @return 元素对象
     */
    public E get(int index) {
        if (priorityStrategy(index)) {
            return getByHead(index);
        } else {
            return getByLast(length() - index);
        }
    }

    /**
     * 获取倒数指定索引的值
     *
     * @param index 倒数索引 从1开始，1：倒数第一个数据
     * @return 元素数据
     */
    public E getLastIndex(int index) {
        if (!priorityStrategy(index)) {
            return getByHead(length() - index);
        } else {
            return getByLast(index);
        }
    }

    /**
     * 获取节点数据对应的索引
     *
     * @param x 节点数据
     * @return 索引 从0开始
     */
    public int getIndex(E x) {
        Node<E> point = head.next;
        int length = length();
        for (int i = 0; i < length; i++) {
            if (x.equals(point.data)) {
                return i;
            }
            point = point.next;
        }
        return -1;
    }

    /**
     * 添加元素数据到链表中，默认是往后添加
     *
     * @param x 元素数据
     */
    public void add(E x) {
        if (isFull()) {
//            throw new IllegalStateException("Double LinkedList full");
            System.out.println("链表满，不能添加数据");
            return;
        }
        Node temp = new Node<>(x);
        temp.pre = last;
        //先把新加的元素添加到最后next中
        last.next = temp;
        //再把最后一个元素指向添加后的最后一个元素
        last = temp;
        //有效元素length添加+1
        count.getAndIncrement();
    }

    /**
     * 向指定位置插入数据
     *
     * @param x     元素数据
     * @param index 索引
     */
    public void add(E x, int index) {
        if (isFull()) {
//            throw new IllegalStateException("Double LinkedList full");
            System.out.println("链表满，不能添加数据");
            return;
        }
        if (index < 0 || index > length()) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        //如果插入的索引是最后一个位置，直接添加到链表的末尾
        if (index == length()) {
            add(x);
            return;
        }
        Node temp = new Node<>(x);
        //point 节点就是需要插入节点的位置。
        Node<E> point = head;
        for (int i = 0; i < index; i++) {
            point = point.next;
        }
        //point节点的下一个节点的上一个节点就是temp
        point.next.pre = temp;
        //当前插入节点的下一个节点就是当前point节点的下一个节点
        temp.next = point.next;
        //point节点的下一个节点就是当前节点temp
        point.next = temp;
        //当前节点的上一节点就是point节点
        temp.pre = point;
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
        if (Objects.isNull(o)) {
            return false;
        }

        //trail 为p的上一节点元素，断开连接的时候需要此节点的引用，p为需要判断是否是删除的元素
        for (Node<E> p = head.next; p != null; p = p.next) {
            if (o.equals(p.data)) {
                unlink(p);
                count.getAndDecrement();
                return true;
            }
        }
        return false;
    }

    /**
     * 删除指定节点的数据
     *
     * @param index 索引 从0开始
     * @return true：删除成功，false：未删除成功
     */
    public boolean remove(int index) {
        checkIndex(index);
        E e = get(index);
        remove(e);
        return false;
    }

    /**
     * 更新节点数据
     *
     * @param x 需要被更新的节点
     * @param p 更新节点数据
     */
    public void update(E x, E p) {
        Node<E> point = head.next;
        int length = length();
        for (int i = 0; i < length; i++) {
            if (x.equals(point.data)) {
                point.data = p;
                return;
            }
            point = point.next;
        }
    }

    /**
     * 更新指定索引值的元素数据
     *
     * @param index 索引从 0 开始
     * @param x     更新的数据
     */
    public void update(int index, E x) {
        checkIndex(index);
        //如果索引在前一半数据中就去head开始遍历否则从last开始遍历
        if (priorityStrategy(index)) {
            Node<E> point = head.next;
            for (int i = 0; i < index; i++) {
                point = point.next;
            }
            point.data = x;
        } else {
            int rIndex = length() - index;
            Node<E> point = last;
            for (int i = 0; i < rIndex; i++) {
                point = point.pre;
            }
            point.data = x;
        }
    }


    /**
     * 将内部节点p与上一节点断开连接,将p节点与下一节点断开连接
     */
    void unlink(Node<E> p) {
        //手动把数据清除弱引用
        p.data = null;
        //p节点的上一节点的下一节点将变为p节点的下一节点
        p.pre.next = p.next;
        //如果是最后一个节点就不需要设置上一节点
        if (p.next != null) {
            //p节点的下一节点的上一节点将变为p节点的上一节点
            p.next.pre = p.pre;
        }
        //如果最后一个元素被删除，则last需要指向上一节点数据
        if (last == p) {
            last = p.pre;
        }
    }

    /**
     * 正序打印
     */
    public void positivePrint() {
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
     * 反序打印
     */
    public void transPrint() {
        Node<E> tail = new Node<>(null);
        tail.pre = last;
        if (tail.pre == head) {
            System.out.println("链表数据为空");
            return;
        }
        Node v = tail.pre;
        int index = 0;
        while (true) {
            System.out.println("Index: " + index + ",Data: " + v.data);
            if (v.pre == head) {
                break;
            }
            index++;
            v = v.pre;
        }
    }

    /**
     * 校验索引是否正确
     *
     * @param index 索引位置，索引第一位为0
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
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

    /**
     * 查找的优先策略
     *
     * @param index 如果是在前半段则使用head查询，否则last查询
     * @return true：head查询，false：last查询
     */
    private boolean priorityStrategy(int index) {
        int slice = 2;
        return length() / slice > index;
    }
}
