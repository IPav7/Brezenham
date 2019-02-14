public class Line {

    public int x_0;
    public int y_0;
    public int x_1;
    public int y_1;

    public Line() {
        this.x_0 = 0;
        this.x_1 = 0;
        this.y_0 = 0;
        this.y_1 = 0;
    }

    public Line(int x_0, int y_0, int x_1, int y_1) {
        this.x_0 = x_0;
        this.y_0 = y_0;
        this.x_1 = x_1;
        this.y_1 = y_1;
    }

    public String toString() {
        return x_0 + " " + y_0 + " " + x_1 + " " + y_1;
    }

}