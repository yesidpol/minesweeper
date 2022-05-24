package mines;

public class BoardPoint {
    private int x;
    private int y;
    
    public BoardPoint(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
    
    @Override
    public boolean equals(Object value) {
        if(!(value instanceof BoardPoint)) return false;
        
        BoardPoint point = (BoardPoint) value;
        
        return x == point.x && y == point.y;
    }
    
    @Override
    public int hashCode() {
        return x + y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
