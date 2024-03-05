package ua.klunniy.spring.multithreading.first;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello world test - 1");
        MyClass myThread = new MyClass();
        myThread.start();

        MyClass myThread2 = new MyClass();
        myThread2.start();

        Thread thread = new Thread(new MyClass2());
        thread.start();

        System.out.println("Hello world test - 2");
    }
}
