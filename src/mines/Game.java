package mines;

import java.util.Random;

public class Game {
	private int width = 7;
	private int height = 7;
	
	private int mines = 10;
	
	private int[][] board;

	public Game(int width, int height, int mines) {
		this.width = width;
		this.height = height;
		this.mines = mines;
		// check width*height > mines
		board = new int[width][height];
	}
	
	public void generate() {
		putMines();
		fillSquares();
	}
	
	private void putMines() {
		Random rnd = new Random();
		
		int k = 0;
		
		do { 
			int x = rnd.nextInt(width);
			int y = rnd.nextInt(height);
			
			if (board[x][y] == 0) {
				board[x][y] = -10;
				k++;
			}
		} while(k < mines);
	}
	
	private void fillSquares() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(board[x][y] < 0) {
					addOneToAdjacentSquare(x, y);
				}
			}
		}
	}
	
	private void addOneToAdjacentSquare(int x, int y) {
		int x0, y0;
		
		x0 = x - 1; y0 = y - 1;
		
		if(isValidSquare(x0, y0)) board[x0][y0]++;
		x0++;
		if(isValidSquare(x0, y0)) board[x0][y0]++;
		x0++;
		if(isValidSquare(x0, y0)) board[x0][y0]++;
		
		x0 = x - 1; y0 = y;		
		
		if(isValidSquare(x0, y0)) board[x0][y0]++;
		x0+=2;
		if(isValidSquare(x0, y0)) board[x0][y0]++;

		x0 = x - 1; y0 = y + 1;
		
		if(isValidSquare(x0, y0)) board[x0][y0]++;
		x0++;
		if(isValidSquare(x0, y0)) board[x0][y0]++;
		x0++;
		if(isValidSquare(x0, y0)) board[x0][y0]++;
	}
	
	private boolean isValidSquare(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("\t");
		
		for(int i = 0; i < width; i++) {
			str.append(i+1);
			str.append("\t");	
		}
		str.append("\n\n");
		
		for(int y = 0; y < height; y++) {
			str.append(y + 1);
			str.append(":\t");
			for(int x = 0; x < width; x++) {
				String data;
				
				if(board[x][y] < 0) data = "x";
				else if (board[x][y] == 0) data = "_";
				else data = Integer.toString(board[x][y]);
				
				str.append(data);
				str.append("\t");
			}
			str.append("\n");
		}
		str.append("\n");
		return str.toString();
	}

	public static void main(String[] args) {	
		Game game = new Game(16, 16, 40);
		
		game.generate();
		
		System.out.println(game.toString());
	}
}
