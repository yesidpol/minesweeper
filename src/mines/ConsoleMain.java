package mines;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.GameStatus;

public class ConsoleMain {
	private int rowCount = 8;
	private int colCount = 8;
	private int mines = 10;
	
	private String[][] squareStrings;
	private Game game;
	
	public ConsoleMain() {
		squareStrings = new String[rowCount][colCount];
		
		for(int x = 0; x < rowCount; x++)
			for(int y = 0; y < colCount; y++)
				squareStrings[x][y] = "_";
		
		Board board = new Board(rowCount, colCount);
		
		board.registerObserver(new SquareObserver() {
			@Override
			public void squareChanged(SquareStatusChangedArgs args) {
				Square square = args.getSquare();
				
				String data = "";
				
				if(square.getSquareStatus() == SquareStatus.PLAYED) {
					if(square.getNumber() == Square.MINE) data = "X";
					else if (square.getNumber() == 0) data = " ";
					else data = Integer.toString(square.getNumber());
				}
				else if(square.getSquareStatus() == SquareStatus.MARKED)
					data = "?";
				
				squareStrings[args.getPoint().getX()][args.getPoint().getY()] = data;
						
			}
		});
		
		game = new Game(board, mines);
	}
	
	public static void printBoard(String[][] squareStrings) {
		StringBuilder str = new StringBuilder();
		
		str.append("\t");
		
		for(int i = 0; i < squareStrings.length; i++) {
			str.append(i);
			str.append("\t");	
		}
		str.append("\n\n");
		
		for(int col = 0; col < squareStrings[0].length; col++) {
			str.append(col);
			str.append(":\t");
			for(int row = 0; row < squareStrings.length; row++) {
				str.append(squareStrings[row][col]);
				str.append("\t" );
			}
			str.append(":");
			str.append(col);

			str.append("\n");
		}
		str.append("\n");
		
		System.out.println(str.toString());
	}
	
	public void play() {
		int x, y;
		
		Scanner in = new Scanner(System.in);
		
		printBoard(this.squareStrings);

		do {
			System.out.print("Enter x.y.[+]: ");
			
			String input = in.nextLine();

			if(input.equals("exit")) {
				break;
			}
			Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(.?)");
			Matcher matcher = pattern.matcher(input);

			if(!matcher.find()) {
				System.out.println("Input wrong");
				continue;
			}
			x = Integer.parseInt(matcher.group(1));
			y = Integer.parseInt(matcher.group(2));
			
			boolean mark = input.length() > 3 && !matcher.group(3).isEmpty();

			if(x >= 0 && y >= 0)
				game.changeSquareStatus(new BoardPoint(x,y), mark);
			
			System.out.print("\033[H\033[2J");  
			System.out.flush();
			
			printBoard(this.squareStrings);
			
			if(game.getStatus() == GameStatus.LOST) {
				System.out.println("YOU LOST :'(");
				break;
			} else if(game.getStatus() == GameStatus.WIN) {	
				System.out.println("YOU WON!!!");
				break;
			}

		} while(true);
		
		in.close();		
	}
	
	public static void main(String[] args) {
		ConsoleMain b = new ConsoleMain();
		
		b.play();
	}

}
