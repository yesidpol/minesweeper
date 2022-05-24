package mines;

public class Square {
    public final static int MINE = -1; 
    private int number;
    private SquareStatus squareStatus;
    
    public Square(int number, SquareStatus squareStatus) {
        this.number  = number;
        this.squareStatus = squareStatus;
    }
    
    public Square() {
        this.number = 0;
        this.squareStatus = SquareStatus.ACTIVE;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        // TODO [0, 8] validation
        this.number = number;
    }

    public SquareStatus getSquareStatus() {
        return squareStatus;
    }

    public void setSquareStatus(SquareStatus squareStatus) {
        this.squareStatus = squareStatus;
    }
    
    public void addOneToNumber() {
        if(number != MINE) {
            number++;
        }
    }

}
