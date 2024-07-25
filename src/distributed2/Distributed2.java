/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed2;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Ismail
 */
public class Distributed2 {

    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        ArrayList<Thread> lockerThreads = new ArrayList<Thread>();

        for (int i = 0; i <= 10; i++) {
            lockerThreads.add(new Thread(new MultiplierLock(lock, i)));
            lockerThreads.get(i).start();
        }
        Object sync = new Object();
        ArrayList<Thread> syncThreads = new ArrayList<Thread>();

        for (int i = 0; i <= 10; i++) {
            syncThreads.add(new Thread(new MultiplierSync(sync, i)));
            syncThreads.get(i).start();
        }
    }

    public static void printMultiTable(int multiplier) {
        // Print the multiplication table from 1 to 10
        for (int i = 1; i <= 10; i++) {
            System.out.print(i * multiplier + "\t");
        }
        System.out.println();
    }
}

class MultiplierSync implements Runnable {

    int multiplier;
    Object sync;

    MultiplierSync(Object sync, int multiplier) {
        this.multiplier = multiplier;
        this.sync = sync;
    }

    @Override
    public void run() {
        synchronized (sync) {
            try {
                Distributed2.printMultiTable(multiplier);

                Thread.sleep(1000);

                System.out.println("Thread " + Thread.currentThread().getName() + " is done");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class MultiplierLock implements Runnable {

    ReentrantLock lock;
    int multiplier;

    public MultiplierLock(ReentrantLock lock, int multiplier) {
        this.lock = lock;
        this.multiplier = multiplier;
    }

    @Override
    public void run() {
        lock.lock();

        try {
            Distributed2.printMultiTable(multiplier);

            Thread.sleep(1000);

            System.out.println("Thread " + Thread.currentThread().getName() + " is done");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
