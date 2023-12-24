package org.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        CountDownLatch allStart = new CountDownLatch(3);
        CountDownLatch startRace = new CountDownLatch(1);
        Thread runnerTread1 = new Thread(new Runner(allStart, startRace));
        Thread runnerTread2 = new Thread(new Runner(allStart, startRace));
        Thread runnerTread3 = new Thread(new Runner(allStart, startRace));
        runnerTread1.start();
        runnerTread2.start();
        runnerTread3.start();

        System.out.println("Ждем бегунов на старте...");
        allStart.await();
        System.out.println("Все пришли...");
        System.out.println("На старт! Внимание! Марш!");
        startRace.countDown();
        runnerTread1.join();
        runnerTread2.join();
        runnerTread3.join();
        System.out.println("Гонка завершена!");
    }
    static class Runner implements Runnable{
        CountDownLatch readyToStart;
        CountDownLatch raceStart;

        public Runner(CountDownLatch readyToStart, CountDownLatch raceStart) {
            this.readyToStart = readyToStart;
            this.raceStart = raceStart;
        }

        @Override
        public void run() {
            try {
                System.out.println("Иду на старт..." + Thread.currentThread().getName());
                Thread.sleep(new Random().nextInt(500, 2000));
                readyToStart.countDown();
                raceStart.await();
                System.out.println("Бегу..." + Thread.currentThread().getName());
                Thread.sleep(new Random().nextInt(3000, 5000));
                System.out.println("Прибежал!" + Thread.currentThread().getName());
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}