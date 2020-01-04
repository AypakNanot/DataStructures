package com.aypak.sparsearray;

/**
 *  稀疏数组
 *  稀疏数组是为了解决在多维数组中大量的值为一样(值==0或者任意)的情况，节约存储空间。
 *  <pre>
 *    	    0	1	2	3	4	5	6	7	8	9
 *     0	0	0	0	0	0	0	0	0	0	0
 *     1	0	0	1	0	0	0	0	0	0	0
 *     2	0	0	0	0	0	0	0	3	0	0
 *     3	0	0	0	2	0	0	0	0	0	0
 *     4	0	0	0	0	8	0	0	0	0	0
 *     5	0	0	0	0	0	0	0	0	6	0
 *     6	0	0	0	0	0	7	0	0	0	0
 *     7	0	0	0	0	0	0	0	0	0	0
 *     8	0	0	0	2	0	0	0	0	0	0
 *     9	0	0	0	0	0	0	0	0	0	0
 *  </pre>
 *
 *  <pre>
 *
 *      Index	Row	  column  Value
 *        0	     2	    1	    1
 *        1	     7	    2	    3
 *        2	     3	    3	    2
 *        3	     4	    4	    8
 *        4	     8	    5	    6
 *        5	     5	    6	    7
 *        6	     3	    8	    2
 *  </pre>
 */
public class SparseArrayDemo {


	public static void main(String[] args) {

		int row = 10;
		int column = 10;
		//原数组为10*10的二维数组 int[10][10]
		//0：表示没有值，从图中可以看出，有值的个数只有 7 个。
		int[][] sourceArray = new int[row][column];
		sourceArray[2][1] = 1;
		sourceArray[7][2] = 3;
		sourceArray[3][3] = 2;
		sourceArray[4][4] = 8;
		sourceArray[8][5] = 6;
		sourceArray[5][6] = 7;
		sourceArray[3][8] = 2;

		// 输出原始的二维数组
		System.out.println("原始的二维数组：");
		for (int[] r : sourceArray) {
			for (int data : r) {
				System.out.printf("%d\t", data);
			}
			System.out.println();
		}

		SparseArray sparseArr = SparseArray.transformToSparseArray(sourceArray);

		// 输出稀疏数组的形式
		System.out.println("\n得到稀疏数组为：");
		System.out.println(sparseArr.toString());

		//将稀疏数组恢复成原始的二维数组
		int[][] chessArr2 = sparseArr.transform();
		// 输出恢复后的二维数组
		System.out.println("转换后的二维数组：");
		for (int[] r : chessArr2) {
			for (int data : r) {
				System.out.printf("%d\t", data);
			}
			System.out.println();
		}
	}



}
