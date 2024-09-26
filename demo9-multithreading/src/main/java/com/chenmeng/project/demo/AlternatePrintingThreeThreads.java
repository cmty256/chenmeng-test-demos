package com.chenmeng.project.demo;

/**
 * 三个线程轮流打印 1~100
 *
 * @author chenmeng
 */
public class AlternatePrintingThreeThreads {

    /**
     * 当前要打印的数字
     */
    private int currentNumber = 1;
    /**
     * 用于同步的锁对象
     */
    private final Object lock = new Object();
    /**
     * 控制哪个线程应该打印的标识 0:3n, 1:3n+1, 2:3n+2
     */
    private int turn = 0;

    public static void main(String[] args) {
        AlternatePrintingThreeThreads ap = new AlternatePrintingThreeThreads();

        // 创建并启动三个线程
        Thread t1 = new Thread(() -> ap.printNumbers(0));
        Thread t2 = new Thread(() -> ap.printNumbers(1));
        Thread t3 = new Thread(() -> ap.printNumbers(2));

        t1.start();
        t2.start();
        t3.start();

    }

    /**
     * 根据 turn 的值打印对应范围的数字
     *
     * @param offset 线程的偏移量，用于确定线程打印数字的序列。 -- 0:3n 1:3n+1 2:3n+2
     */
    private void printNumbers(int offset) {
        while (currentNumber <= 100) {
            synchronized (lock) {

                // 等待直到当前线程应该打印的条件满足
                while ((turn % 3) != offset) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        // 将中断异常转换为运行时异常，并抛出。
                        throw new RuntimeException(e);
                    }
                }

                // 检查当前数字是否仍在打印范围内，并且是当前线程应该打印的数字
                if (currentNumber <= 100 && (currentNumber - 1) % 3 == offset) {
                    System.out.println("Thread " + (offset + 1) + " printed: " + currentNumber);
                    currentNumber++;
                    // 更新打印轮次，确保三个线程按顺序进行
                    turn = (turn + 1) % 3;
                    // 唤醒所有等待的线程，因为打印条件可能已经改变
                    lock.notifyAll();
                }
            }
        }
    }
}