package com.aypak.unidirectionalqueue;

/**
 * 环形队列
 */
public class CircleQueue<E> {

    /**
     * 队列
     */
    private final Object[] queue;

    /**
     * 起始位置，默认为0，标记为没有元素被取
     */
    private int front = 0;
    /**
     * 结束位置，默认为0，标记为没有元素添加进来
     */
    private int rear = 0;

    /**
     * 队列最大存储数据量
     */
    private final int capacity;

    /**
     * 队列元素个数,为了方便思考可以多加一个变了来记录size，也可以 (rear + capacity - front) % capacity;
     */
    private int size;

    /**
     * 初始化队列
     * @param capacity 容量大小
     */
    public CircleQueue(final int capacity){
        this.capacity = capacity;
        queue = new Object[capacity];
    }

    /**
     * 是否装满
     * @return true：满，false：未满
     */
    public boolean isFull(){
//        return (rear  + 1) % capacity == front;
        return size == capacity;
    }

    /**
     * 是否为空，
     * @return true：空，false：有数据
     */
    public boolean isEmpty(){
//        return rear == front;
        return size == 0;
    }

    /**
     * 获取队列数据大小
     * @return size
     */
    public int size(){
        //return (rear + capacity - front) % capacity;
        return size;
    }

    /**
     * 添加一个元素
     * @param v 元素
     */
    public void add(E v){
        if(isFull()){
//            throw new IndexOutOfBoundsException("queue is full.");
            System.out.println("queue is full.");
            return;
        }
        //先把索引往后移动再添加
        queue[rear] = v;
        //把索引指向下一个
        rear = (rear +1) % capacity;
        size++;
    }

    /**
     * 取出一个元素
     * @return 元素
     */
    public E get(){
        if(isEmpty()){
//            throw new NullPointerException("queue is empty.");
            System.out.println("queue is empty.");
            return null;
        }
        E v = (E) queue[front];
        //先把索引指向下一个
        front = (front +1) % capacity;
        size--;
        return v;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        // 从front开始遍历，遍历size个元素
        for (int i = front; i < front + size() ; i++) {
            sb.append(String.format("arr[%d]=%s\n", i % capacity, queue[i % capacity]));
        }
        return sb.toString();
    }
}
