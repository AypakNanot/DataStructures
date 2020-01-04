package com.aypak.sparsearray;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.fill;

/**
 * 稀疏数组
 * 稀疏数组是为了解决在多维数组中大量的值为一样(值==0或者任意)的情况，节约存储空间。
 * <pre>
 *   	   0	1	2	3	4	5	6	7	8	9
 *    0	   0	0	0	0	0	0	0	0	0	0
 *    1	   0	0	1	0	0	0	0	0	0	0
 *    2	   0	0	0	0	0	0	0	3	0	0
 *    3	   0	0	0	2	0	0	0	0	0	0
 *    4	   0	0	0	0	8	0	0	0	0	0
 *    5	   0	0	0	0	0	0	0	0	6	0
 *    6	   0	0	0	0	0	7	0	0	0	0
 *    7	   0	0	0	0	0	0	0	0	0	0
 *    8	   0	0	0	2	0	0	0	0	0	0
 *    9	   0	0	0	0	0	0	0	0	0	0
 *
 *
 *     Index	Row	  column  Value
 *       0	     2	    1	    1
 *       1	     7	    2	    3
 *       2	     3	    3	    2
 *       3	     4	    4	    8
 *       4	     8	    5	    6
 *       5	     5	    6	    7
 *       6	     3	    8	    2
 * </pre>
 * 稀疏数组有3列
 * <ul>
 * <li>第一列：对应二维数组的row号</li>
 * <li>第二列：对应二维数组的column</li>
 * <li>第三列：对应row，column的值</li>
 * </ul>
 */
public class SparseArray {

    /**
     * 当前稀疏数组中可添加的数量
     */
    private AtomicInteger count = new AtomicInteger(0);
    /**
     * 稀疏数组的行数
     */
    private final int row;
    /**
     * 稀疏数组
     */
    private final int[][] sparseArray;

    /**
     * 数组默认值
     */
    private final int defaultValue;

    /**
     * 初始化一个稀疏数组
     *
     * @param row    二维数组的总行数
     * @param column 二维数组的总列数
     * @param sum    二维数组的有效值数
     */
    public SparseArray(final int row, final int column, final int sum) {
        this(row, column, sum, 0);
    }

    /**
     * 初始化一个稀疏数组
     *
     * @param row          二维数组的总行数
     * @param column       二维数组的总列数
     * @param sum          二维数组的有效值数
     * @param defaultValue 默认值
     */
    public SparseArray(final int row, final int column, final int sum, final int defaultValue) {
        this.row = row;
        this.defaultValue = defaultValue;
        //初始化稀疏數組
        sparseArray = new int[sum + 1][3];
        sparseArray[0][0] = row;
        sparseArray[0][1] = column;
        sparseArray[0][2] = sum;
    }

    /**
     * 添加数据到稀疏数组
     *
     * @param r row
     * @param c column
     * @param v value
     */
    public void add(int r, int c, int v) {
        int index = count.incrementAndGet();
        if (index > row) {
            throw new IndexOutOfBoundsException("index > row");
        }
        sparseArray[index][0] = r;
        sparseArray[index][1] = c;
        sparseArray[index][2] = v;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] item : sparseArray) {
            sb.append(String.format("%d\t%d\t%d\t\n", item[0], item[1], item[2]));
        }
        return sb.toString();
    }

    /**
     * 转换为标准的二维数组
     * 1、先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
     * 2、在读取稀疏数组后几行的数据，并赋给 原始的二维数组
     *
     * @return 二维数组
     */
    public int[][] transform() {
        // 在读取稀疏数组后几行的数据(从第二行开始)，并赋给 原始的二维数组 即可
        int[][] arr = new int[sparseArray[0][0]][sparseArray[0][1]];
        //用默认值填充数组
        if (defaultValue != 0) {
            fill(arr, defaultValue);
        }
        //把有效值放入数组中
        for (int i = 1; i < sparseArray.length; i++) {
            arr[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        return arr;
    }

    /**
     * 将常规数组转换为稀疏数组
     *
     * @param array array
     * @return SparseArray
     */
    public static SparseArray transformToSparseArray(int[][] array) {
        return transformToSparseArray(array, 0);
    }

    /**
     * 将常规数组转换为稀疏数组
     *
     * @param array        array
     * @param defaultValue 默认填充值
     * @return SparseArray
     */
    public static SparseArray transformToSparseArray(int[][] array, int defaultValue) {
        int row = array.length;
        int column = array[0].length;

        //有效数据数量
        int sum = 0;
        // 先遍历二维数组 得到有效数据个数
        for (int[] ints : array) {
            for (int j = 0; j < column; j++) {
                if (ints[j] != defaultValue) {
                    sum++;
                }
            }
        }

        SparseArray sparseArr = new SparseArray(row, column, sum, defaultValue);

        // 遍历二维数组，将非默认值的值存放到 SparseArray中
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int v = array[i][j];
                if (v != defaultValue) {
                    sparseArr.add(i, j, v);
                }
            }
        }
        return sparseArr;
    }

}