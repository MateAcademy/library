package ua.klunniy.spring.multithreading.first;

public class MyClass2 implements Runnable{
    @Override
    public void run() {
        System.out.println("run");
    }
}
