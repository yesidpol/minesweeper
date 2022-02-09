package mines;

import java.util.function.Consumer;

public class Board {
	private int width;
	private int height;
	
	private Square[][] squares;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		initializeSquareStatus();
	}
	
	private void initializeSquareStatus() {
		squares = new Square[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				squares[x][y] = new Square(0, SquareStatus.ACTIVE);
	}
	
	public void changeSquareStatus(BoardPoint point, boolean mark) {
		Square square = squares[point.getX()][point.getY()];
		
		if(mark) markSquare(square);

		if(!mark && square.getSquareStatus() == SquareStatus.ACTIVE) {
			if(square.getNumber() == 0) {
				changeZeroSquare(point);
			}
			square.setSquareStatus(SquareStatus.PLAYED);
		}
	}
	
	private void markSquare(Square square) {
		if(square.getSquareStatus() == SquareStatus.ACTIVE) {
			square.setSquareStatus(SquareStatus.MARKED);
		} else if(square.getSquareStatus() == SquareStatus.MARKED) {
			square.setSquareStatus(SquareStatus.ACTIVE);
		}
	}
	
	public void changeZeroSquare(BoardPoint point) {	
		Square centralSquare = squares[point.getX()][point.getY()]; 

		if(centralSquare.getSquareStatus() == SquareStatus.ACTIVE) {
			centralSquare.setSquareStatus(SquareStatus.PLAYED);
			
			if(centralSquare.getNumber() == 0) {
				iterateAdjacentSquares(point, a -> { changeZeroSquare(a); });
			}
		}
	}
	
	public void addMine(BoardPoint point) {
		int x = point.getX();
		int y = point.getY();
		
		squares[x][y].setNumber(-1);
		
		addOneToAdjacentSquare(x, y);
	}
	
	private void addOneToAdjacentSquare(int x, int y) {
		iterateAdjacentSquares(new BoardPoint(x,y), (a) -> { squares[a.getX()][a.getY()].addOneToNumber(); });
	}
	
	private void iterateAdjacentSquares(BoardPoint centralPoint, Consumer<BoardPoint> c1) {
		final int x = centralPoint.getX();
		final int y = centralPoint.getY();
		BoardPoint adjacentSquarePoint;
		
		int x0, y0;
		
		x0 = x - 1; y0 = y - 1;
		
		adjacentSquarePoint = new BoardPoint(x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
		
		adjacentSquarePoint = new BoardPoint(++x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
		
		adjacentSquarePoint = new BoardPoint(++x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
		
		
		x0 = x - 1; y0 = y;
		adjacentSquarePoint = new BoardPoint(x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
		
		x0+=2;
		adjacentSquarePoint = new BoardPoint(x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);

		
		x0 = x - 1; y0 = y + 1;
		
		adjacentSquarePoint = new BoardPoint(x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
		
		adjacentSquarePoint = new BoardPoint(++x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
		
		adjacentSquarePoint = new BoardPoint(++x0, y0);
		if(isValidSquare(adjacentSquarePoint)) c1.accept(adjacentSquarePoint);
	}
	
	private boolean isValidSquare(BoardPoint point) {
		return point.getX() >= 0 && point.getX() < width && point.getY() >= 0 && point.getY() < height;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("\t");
		
		for(int i = 0; i < width; i++) {
			str.append(i);
			str.append("\t");	
		}
		str.append("\n\n");
		
		for(int y = 0; y < height; y++) {
			str.append(y);
			str.append(":\t");
			for(int x = 0; x < width; x++) {
				String data;
				
				if(squares[x][y].getSquareStatus() == SquareStatus.PLAYED) {
					if(squares[x][y].getNumber() == Square.MINE) data = "X";
					else if (squares[x][y].getNumber() == 0) data = " ";
					else data = Integer.toString(squares[x][y].getNumber());
					str.append(data);
				}
				else if(squares[x][y].getSquareStatus() == SquareStatus.MARKED)
					str.append("*");
				else
					str.append("_");
				
				
				str.append("\t" );
			}
			str.append("\n");
		}
		str.append("\n");
		return str.toString();
	}
}
