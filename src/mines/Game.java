package mines;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import enums.GameStatus;


public class Game {
    private int mines;
    
    private int safeSquaresCount;
    
    private Board board;
    
    private Set<BoardPoint> minesCoord;
    private GameStatus status;
    
    public Game(Board board, int mines) {
        this.mines = mines;
        this.board = board;
        // TODO Throw exception
        this.safeSquaresCount = getRowCount() * getColCount() - mines;
        
        minesCoord = new HashSet<>();
        
        status = GameStatus.NOT_PLAYING;
    }
    
    private void start(BoardPoint initialPoint) {
        putMines(initialPoint);
        status = GameStatus.PLAYING;
    }
    
    private void putMines(BoardPoint initialPoint) {
        Random rnd = new Random();
        
        int k = 0;
        
        do { 
            BoardPoint point = new BoardPoint(rnd.nextInt(getRowCount()), rnd.nextInt(getColCount()));
            
            if(!point.equals(initialPoint) && minesCoord.add(point)) {
                board.addMine(point);
                k++;
            }
        } while(k < mines);
    }
    
    public String toString() {
        return board.toString();
    }
    
    public boolean changeSquareStatus(BoardPoint point, boolean mark) {
        boolean result = false;
        if(status == GameStatus.NOT_PLAYING && !mark) {
            start(point);
        }
        if(status == GameStatus.PLAYING) {
            SquareStatus squareStatus = board.changeSquareStatus(point, mark);
            if(!mark && squareStatus != SquareStatus.MARKED)
                changeStatus(point);
            result = true;
        }
        return result;
    }
    
    private void changeStatus(BoardPoint point) {
        if(minesCoord.contains(point)) {
            status = GameStatus.LOST;
        } else if(board.getPlayedSquares() == safeSquaresCount) {
            status = GameStatus.WIN;
        }
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getRowCount() {
        return board.getRowCount();
    }

    public int getColCount() {
        return board.getColCount();
    }

    public Board getBoard() {
        return board;
    }
}
