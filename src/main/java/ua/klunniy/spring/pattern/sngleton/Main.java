package ua.klunniy.spring.pattern.sngleton;

/**
 * Синглтон - это шаблон (паттерн) проектирования, который делает две вещи:
 * 1) Дает гарантию, что у класса будет всегда один экземпляр класса.
 * 2) Предоставляет глобальную точку доступа к экземпляру данного класса.
 */
public class Main {
    public static void main(String[] args) {
        MyConnection myConnection = MyConnection.getInstance();
        System.out.println(myConnection);

        MyConnection myConnection2 = MyConnection.getInstance();
        System.out.println(myConnection2);
    }
}
