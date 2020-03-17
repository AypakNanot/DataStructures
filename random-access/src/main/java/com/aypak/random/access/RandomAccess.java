package com.aypak.random.access;

import java.util.Random;

/**
 * 如何高效产生n以内n个不重复的数
 * @author lihua
 */
public class RandomAccess {


    /**
     * 分成n份并且不重复的数
     * @param n 份
     */
    public static int[] generate(int n){
        int[] arr = new int[n];
        //给数组总填充好数据，正好是n个数据
        for (int i = 0; i < n; i++) {
            arr[i] = i+1;
        }
        //进行随机
        for (int i = 0; i < n; i++) {
            //从i开始到n进行随机取值，取出的值一定在此之中，然后把数组中下标是此r的进行交换位置，下一次循环的时候就不会取到该值。
            //这样一来循环完成即可取出随机数
            int r = getRandomBetween(i, n);
            swap(arr,i,r);
        }
        return arr;
    }

    /**
     * 获取min到max之前的随机数
     * @param min 最小数
     * @param max 最大数,注意这里的max是不包含的，因为是下标所有要少1. the bound (exclusive) of each random value
     * @return 随机数
     */
    public static int getRandomBetween(int min,int max){
        Random random = new Random();
        return random.ints(min, max).findFirst().getAsInt();
    }

    /**
     * 数组交换位置
     * @param arr 数组
     * @param inx 交换为iny的索引
     * @param iny 交换为inx的索引
     */
    public static void swap(int[] arr,int inx,int iny){
        int temp = arr[inx];
        arr[inx] = arr[iny];
        arr[iny] = temp;
    }


}
