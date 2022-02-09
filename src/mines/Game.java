package mines;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Game {
	private int width = 7;
	private int height = 7;
	private int mines = 10;
	
	private Board board;
	
	private Set<BoardPoint> minesCoord;
	
	public Game(int width, int height, int mines) {
		this.width = width;
		this.height = height;
		this.mines = mines;
		
		minesCoord = new HashSet<>();

		board = new Board(width, height);
	}
	
	public void initialize() {
		putMines();
	}
	
	private void putMines() {
		Random rnd = new Random();
		
		int k = 0;
		
		do { 
			BoardPoint point = new BoardPoint(rnd.nextInt(width), rnd.nextInt(height));
			
			if(minesCoord.add(point)) {
				board.addMine(point);
				k++;
			}
		} while(k < mines);
	}
	
	public String toString() {
		return board.toString();
	}
	
	public void changeSquareStatus(BoardPoint point, boolean mark) {
		board.changeSquareStatus(point, mark);
	}
}
