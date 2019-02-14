public class Circle {

    public int xCenter;
    public int yCenter;
    public int radius;

    public Circle() {
        this.xCenter = 0;
        this.radius = 0;
        this.yCenter = 0;
    }

    public Circle(int xCenter, int yCenter, int radius) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
    }

    public String toString() {
        return xCenter + " " + yCenter + " " + radius;
    }

}
