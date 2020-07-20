package ru.basejava.webapp;

public class MainDeadlock {

    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();

        Thread thread1 = new Thread(() -> createRun(obj1, obj2));
        thread1.start();

        new Thread(() -> createRun(obj2, obj1)).start();
    }

    public static void createRun(Object obj1, Object obj2) {
        synchronized (obj1) {
            System.out.println(obj1 + " заблокирован потоком " + Thread.currentThread().getName());
            System.out.println("Пробуем перехватить " + obj2);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj2) {
                System.out.println("Удалось перехватить " + obj2);
            }
        }
    }
}
