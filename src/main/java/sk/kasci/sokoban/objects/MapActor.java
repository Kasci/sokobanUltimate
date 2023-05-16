package sk.kasci.sokoban.objects;

public class MapActor {
    private int x, y;
    private int DX, DY;
    public MapActor(int x, int y) {
        this.x = x;
        this.y = y;
        this.DX = 0;
        this.DY = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        DX = dx*64;
        DY = dy*64;
    }

    public int getDX() {
        return DX;
    }

    public void setDX(int DX) {
        this.DX = DX;
    }

    public int decDX() {
        int r = this.DX;
        this.DX += Integer.compare(0, this.DX);
        return r;
    }

    public int getDY() {
        return DY;
    }

    public void setDY(int DY) {
        this.DY = DY;
    }

    public int decDY() {
        int r = this.DY;
        this.DY += Integer.compare(0, this.DY);
        return r;
    }
}
