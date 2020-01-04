package com.aypak.unidirectionalqueue;

import java.util.Objects;
import java.util.Scanner;

/**
 * 单向队列
 */
public class UnidirectionalQueueDemo {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("请输入需要初始化单向队列的大小：");
            String capacity = scanner.nextLine();
            UnidirectionalQueue q = new UnidirectionalQueue(Integer.valueOf(capacity));
            System.out.println("a：添加数据");
            System.out.println("s：显示数据");
            System.out.println("g：获取数据");
            System.out.println("exit:退出");
            while (true) {
                String command = scanner.nextLine();
                boolean exit =false;
                switch (command) {
                    case "a":
                        System.out.println("请输入需要添加的数据：");
                        command = scanner.nextLine();
                        System.out.println("添加数据："+command);
                        q.add(Integer.valueOf(command));
                        break;
                    case "s":
                        System.out.println(q.toString());
                        break;
                    case "g":
                        Integer v = q.get();
                        if(Objects.nonNull(v)){
                            System.out.println("获取到的数据：" + v);
                        }
                        break;
                    case "exit":
                        System.out.println("关闭");
                        exit = true;
                        break;
                    default:
                        System.out.println("无效输入");
                        break;
                }
                if(exit){
                    break;
                }
            }

        }

    }
}
