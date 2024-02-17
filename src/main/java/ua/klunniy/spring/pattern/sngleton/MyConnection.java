package ua.klunniy.spring.pattern.sngleton;

/**
 * @author Serhii Klunniy
 */
public class MyConnection {

    private MyConnection() {}

    private static MyConnection instence;

    public static MyConnection getInstance() {
        if (instence == null) {
            instence = new MyConnection();
        }
        return instence;
    }
}
