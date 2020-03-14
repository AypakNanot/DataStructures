package com.aypak.random.access;

import java.util.Arrays;

/**
 * 高效产生n以内n个不重复的数
 */
public class RandomAccessDemo {

    public static void main(String[] args) {
        int[] generate = RandomAccess.generate(100);
        System.out.println(Arrays.toString(generate));
        System.out.println(generate.length);
        //在实际项目中，我们常常会模拟数据，把一个数据分成若干份，并且所有之和等于这个数据。

        // 先把分数生成随机数，然后➗总分数，就得到每份的比例，总数据✖️比例＝每份的数据大小
        //比如：把500000分成，100份，每份不一样
        final Integer n = 100;
        final Integer zh = 500000;
        final Integer sum = sum(n);
        int[] pd = RandomAccess.generate(n);
        //如果想要数据不会丢失精度，可使用double，int可能会丢失精度
        for (int i = 0; i < pd.length; i++) {
            pd[i] = zh/sum * pd[i];
        }
        System.out.println(Arrays.toString(pd));


    }

    public static int sum(int n){
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
}
