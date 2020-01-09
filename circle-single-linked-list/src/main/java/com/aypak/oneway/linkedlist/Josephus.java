package com.aypak.oneway.linkedlist;

/**
 * 著名的Josephus问题
 * - 据说著名犹太历史学家 Josephus有过以下的故事：在罗马人占领乔塔帕特後，39 个犹太人与Josephus及他的朋友躲到一个洞中，
 * 39个犹太人决定宁愿死也不要被人抓到，于是决定了一个自杀方式，41个人排成一个圆圈，
 * 由第1个人开始报数，每报数到第3人该人就必须自杀，然后再由下一个重新报数，直到所有人都自杀身亡为止。
 * 然而Josephus 和他的朋友并不想遵从，Josephus要他的朋友先假装遵从，他将朋友与自己安排在第16个与第31个位置，于是逃过了这场死亡游戏
 *
 * @author lihua
 */
public class Josephus {

    public static void main(String[] args) {
        //一共有41个人
        final int n = 41;
        //每次数3人
        final int m = 3;

        CircleSingleLinkedList<Object> list = new CircleSingleLinkedList<>(n);
        //链表中加入39个人
        for (int i = 1; i <= n; i++) {
            list.add("p:" + i);
        }
        list.print();
        System.out.println("总长度：" + list.length());
        int killIndex = 1;
        while (list.length() > 0) {
            Object cur = list.count(m);
            System.out.println("第" + (killIndex++) + "次,kill:" + cur);
            list.next();
            list.remove(cur);
            if(list.length() < m){
                break;
            }
        }
        System.out.println("存活下来的：");
        list.print();
    }
}
