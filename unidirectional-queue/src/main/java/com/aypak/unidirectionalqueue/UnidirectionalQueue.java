package com.aypak.unidirectionalqueue;

/**
 * 单向队列
 */
public class UnidirectionalQueue {

    /**
     * 队列
     */
    private final int[] queue;

    /**
     * 起始位置，默认为-1，标记为没有元素被取
     */
    private int front = -1;
    /**
     * 结束位置，默认为-1，标记为没有元素添加进来
     */
    private int rear = -1;

    /**
     * 队列最大存储数据量
     */
    private final int capacity;

    /**
     * 初始化队列
     * @param capacity 容量大小
     */
    public UnidirectionalQueue(final int capacity){
        this.capacity = capacity;
        queue = new int[capacity];
    }

    /**
     * 是否装满
     * @return true：满，false：未满
     */
    public boolean isFull(){
        return capacity <= rear;
    }

    /**
     * 是否为空，
     * @return true：空，false：有数据
     */
    public boolean isEmpty(){
        return rear == front;
    }

    /**
     * 添加一个元素
     * @param v 元素
     */
    public void add(int v){
        if(isFull()){
//            throw new IndexOutOfBoundsException("queue is full.");
            System.out.println("queue is full.");
            return;
        }
        //先把索引往后移动再添加
        queue[++rear] = v;
    }

    /**
     * 取出一个元素
     * @return 元素
     */
    public Integer get(){
        if(isEmpty()){
//            throw new NullPointerException("queue is empty.");
            System.out.println("queue is empty.");
            return null;
        }
        //先把索引往后移动再取数据
        return queue[++front];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = front +1; i <= rear; i++){
            sb.append(String.format("%d\n",queue[i]));
        }
        return sb.toString();
    }
}
