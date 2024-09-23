package com.chenmeng.project.demo;

/**
 * 两个线程轮流打印 1~100
 *
 * @author chenmeng
 */

public class AlternatePrinting {

    private int currentNumber = 1;
    private final Object lock = new Object();

    public static void main(String[] args) {
        AlternatePrinting ap = new AlternatePrinting();

        // 创建奇数打印线程
        Thread oddPrinter = new Thread(() -> {
            ap.printNumber(true);
        });
        oddPrinter.start();

        // 创建偶数打印线程
        Thread evenPrinter = new Thread(() -> {
            ap.printNumber(false);
        });
        evenPrinter.start();
    }

    /**
     * 打印从 1~100 的数字，根据 flag 的值决定打印奇数还是偶数。
     * 使用同步锁来确保奇数和偶数交替打印，避免线程安全问题。
     * 当线程应该等待时，它将释放锁，并在获得锁后继续执行。
     *
     * @param flag 代表是否为奇数 -- true-奇数, false-偶数
     */
    private void printNumber(boolean flag) {
        while (currentNumber <= 100) {
            synchronized (lock) {

                // 当当前数字不符合线程的打印条件时，线程等待，释放锁。
                while ((flag && currentNumber % 2 == 0) || (!flag && currentNumber % 2 == 1)) {
                    try {
                        // 如果当前线程不应该打印,等待
                        lock.wait();
                    } catch (InterruptedException e) {
                        // 将中断异常转换为运行时异常，并抛出。
                        throw new RuntimeException(e);
                    }
                }

                // 当前数字在范围内且符合线程的打印条件时，进行打印并递增当前数字。
                if (currentNumber <= 100) {
                    System.out.println("Thread " + (flag ? "Odd " : "Even") + ": " + currentNumber);
                    currentNumber++;
                    // 通知所有等待的线程，可能有线程因为当前数字不符合条件而等待。
                    lock.notifyAll();
                }
            }
        }
    }
}