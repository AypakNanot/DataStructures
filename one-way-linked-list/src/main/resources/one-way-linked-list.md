# 单向链表 One-way LinkedList

> [返回README](../../../../README.md)


单向链表:

- 单向链表（又名单链表、线性链表）是链表的一种，其特点是链表的链接方向是单向的，对链表的访问要通过从头部开始，依序往下读取。
- 如下图：
![one-way-linked-list-1.png](one-way-linked-list-1.png)

- 初始化

head元素始终为空数据元素，next指向第一个有效元素，为链表的第一个元素。

Last元素，始终指向最后一个元素，初始化的时候指向head。Last是为了让添加的时候不需要进行遍历就可以添加。

- 添加数据

    
    Node temp = new Node(x);
    //先把新加的元素添加到最后next中
    last.next = temp;
    //再把最后一个元素指向添加后的最后一个元素
    last = temp;
    //有效元素length添加+1
    count.getAndIncrement();
   
- 删除数据


    //trail 为p的上一节点元素，断开连接的时候需要此节点的引用，p为需要判断是否是删除的元素
    for (Node<E> trail = head, p = trail.next; p != null; trail = p, p = p.next) {
        if (o.equals(p.data)) {
            unlink(p, trail);
            count.getAndDecrement();
            return true;
        }
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