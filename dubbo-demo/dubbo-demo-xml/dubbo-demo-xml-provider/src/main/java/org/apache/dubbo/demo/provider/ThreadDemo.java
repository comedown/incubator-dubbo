package org.apache.dubbo.demo.provider;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadDemo {
    static char[] chars = new char[3 * 10];
    final static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
    final static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) {
        MyThread t1 = new MyThread(1, 'A', 10);
        MyThread t2 = new MyThread(2, 'B', 10);
        MyThread t3 = new MyThread(3, 'C', 10);
        singleExecutor.execute(t1);
        singleExecutor.execute(t2);
        singleExecutor.execute(t3);
        try {
            countDownLatch.await();
            singleExecutor.shutdown();
            while(true) {
                if(singleExecutor.isTerminated()) {
                    System.out.println(String.valueOf(chars));
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread implements Runnable {

        private int order;
        private char ch;
        private int count;

        public MyThread(final int order, final char ch, final int count) {
            this.order = order;
            this.ch = ch;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                chars[i * 3 + order - 1] = ch;
            }
            countDownLatch.countDown();
        }
    }
}
