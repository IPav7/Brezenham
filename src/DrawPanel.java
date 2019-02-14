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
            drawBresenhamLine(l.x_0, l.y_0, l.x_1, l.y_1, g);
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

    // Этот код "рисует" все 9 видов отрезков. Наклонные (из начала в конец и из конца в начало каждый), вертикальный и горизонтальный - тоже из начала в конец и из конца в начало, и точку.
    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    public void drawBresenhamLine (int xstart, int ystart, int xend, int yend, Graphics g)
    /**
     * xstart, ystart - начало;
     * xend, yend - конец;
     * "g.drawLine (x, y, x, y);" используем в качестве "setPixel (x, y);"
     * Можно писать что-нибудь вроде g.fillRect (x, y, 1, 1);
     */
    {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
        /*
         * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
         * справа налево по иксу, то incx будет равен -1.
         * Это будет использоваться в цикле постороения.
         */
        incy = sign(dy);
        /*
         * Аналогично. Если рисуем отрезок снизу вверх -
         * это будет отрицательный сдвиг для y (иначе - положительный).
         */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy)
        //определяем наклон отрезка:
        {
            /*
             * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
             * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
             * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
             * по y сдвиг такой отсутствует.
             */
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;
        }
        else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el/2;
        g.drawLine (x, y, x, y);//ставим первую точку
        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

        for (int t = 0; t < el; t++)//идём по всем точкам, начиная со второй и до последней
        {
            err -= es;
            if (err < 0)
            {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }
            else
            {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            g.drawLine (x, y, x, y);
        }
    }

    @Override
    public void shapeAdded() {
        repaint();
    }

}