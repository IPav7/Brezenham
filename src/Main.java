import javax.swing.*;

public class Main {
    public static void main(String...args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window window = new Window();
                window.setVisible(true);
            }
        });
    }
}