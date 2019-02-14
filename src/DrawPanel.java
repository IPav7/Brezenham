import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class DrawPanel extends JComponent implements ControlPanel.OnShapeAddedListener {

    private int width, height;
    public ArrayList<Line> lines;
    public ArrayList<Circle> circles;

    public DrawPanel(int width, int height) {
        this.width = width ;
        this.height = height;
        setPreferredSize(new Dimension(this.width, this.height));
        setSize(this.width, this.height);
        lines = new ArrayList<Line>();
        circles = new ArrayList<Circle>();
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        drawBackground(g);
        draw(g);
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    private void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        for (Line l: lines) {
            g.drawLine(l.x_0, l.y_0, l.x_1, l.y_1);
        }
        for (Circle circle: circles)
            drawCircle(circle.xCenter, circle.yCenter, circle.radius, g);
    }

    private void drawCircle(int center_x, int center_y, int radius, Graphics g){
        int x = 0, y = radius, sigma = 0, delta = 2 - 2 * radius;
        while (y >= 0){
            //считаем для одной четверти, и симметрично заполняем остальные
            g.drawLine(center_x + x, center_y - y, center_x + x, center_y - y);     // 1 четверть
            g.drawLine(center_x - x, center_y - y, center_x - x, center_y - y);     // 2 четверть
            g.drawLine(center_x - x, center_y + y, center_x - x, center_y + y);     // 3 четверть
            g.drawLine(center_x + x, center_y + y, center_x + x, center_y + y);     // 4 четверть
            sigma = 2 * (delta + y) - 1;
            if (delta < 0 && sigma <= 0) {          //перемещение по горизонтали
                x++;
                delta += x + 1;
            } else if (delta > 0 && sigma > 0) {    //перемещение по вертикали
                y--;
                delta -= y + 1;
            } else {                                //перемещение по диагонали
                x++;
                delta += x - y;
                y--;
            }
        }
    }

    @Override
    public void shapeAdded() {
        repaint();
    }

}