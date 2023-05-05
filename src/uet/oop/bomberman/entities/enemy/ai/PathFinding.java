package uet.oop.bomberman.entities.enemy.ai;

public class PathFinding {
    double G; // khoảng cách từ điểm đầu đến hiện tại
    double H; // khoảng cách từ ô hiện tại đến điểm đích
    double F; // giá G + h
    int direction;

    public PathFinding(double g, double h, double f, int direction) {
        G = g;
        H = h;
        F = f;
        this.direction = direction;
    }

    public double getG() {
        return G;
    }

    public double getF() {
        return F;
    }

    public double getH() {
        return H;
    }

    public int getDirection() {
        return direction;
    }

    public void setG(double G) {
        this.G = G;
    }

    public void setF(double f) {
        F = f;
    }

    public void setH(double h) {
        H = h;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
