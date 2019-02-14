import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Arrays;

public class ControlPanel extends JTextField {

    private int width, height;
    private ArrayList<Line> lines;
    private ArrayList<Circle> circles;
    private ControlPanel.OnShapeAddedListener onShapeAddedListener;

    public ControlPanel(int width, int height, ArrayList<Line> lines, ArrayList<Circle> circles, ControlPanel.OnShapeAddedListener onShapeAddedListener) {
        this.width = width;
        this.height = height;
        this.lines = lines;
        this.circles = circles;
        this.onShapeAddedListener = onShapeAddedListener;
        addKeyListener(new KeyboardHandler());
        setMaximumSize(new Dimension(this.width, this.height));
    }

    private class KeyboardHandler implements KeyListener {
        public void keyTyped(KeyEvent keyEvent) {
        }
        public void keyPressed(KeyEvent keyEvent) {
        }
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                ArrayList<String> strings = new ArrayList(Arrays.asList(getText().trim().split(" ")));
                switch (strings.remove(0)) {
                    case "help":
                        JOptionPane.showMessageDialog(null, "Contact administrator!");
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    case "clear":
                        lines.clear();
                        circles.clear();
                        break;
                    case "line": {
                        int[] arr = new int[4];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = Integer.valueOf(strings.remove(0));
                        }
                        lines.add(new Line(arr[0], arr[1], arr[2], arr[3]));
                        break;
                    }
                    case "circle": {
                        int[] arr = new int[3];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = Integer.valueOf(strings.remove(0));
                        }
                        circles.add(new Circle(arr[0], arr[1], arr[2]));
                        break;
                    }
                }
                onShapeAddedListener.shapeAdded();
                setText("");
            }
        }
    }

    public interface OnShapeAddedListener {
        void shapeAdded();
    }

}