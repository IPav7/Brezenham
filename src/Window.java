import javax.swing.*;
import java.awt.*;

class Window extends JFrame {

    private static final int WIDTH = 600, HEIGHT = 600;

    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

        DrawPanel drawPanel = new DrawPanel(600, 585);
        ControlPanel controlPanel = new ControlPanel(600, 15, drawPanel.lines, drawPanel.circles, drawPanel);
        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        pack();

    }

}