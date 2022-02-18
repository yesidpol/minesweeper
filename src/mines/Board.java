package mines;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Board {
	private int rowCount;
	private int colCount;
	
	private Square[][] squares;
	
	private int playedSquares;
	
	private List<SquareObserver> squareObservers;

	public Board(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;

		initializeSquareStatus();
		squareObservers = new ArrayList<>();
	}
	
	public void registerObserver(SquareObserver observer) {
		squareObservers.add(observer);
	}
	
	public void removeObserver(SquareObserver observer) {
		squareObservers.remove(observer);
	}

	private void notifyObservers(Square square, SquareStatus previousStatus, BoardPoint point) {
		squareObservers.forEach(a -> a.squareChanged(new SquareStatusChangedArgs(square, previousStatus, point)));
	}
	
	private void initializeSquareStatus() {
		playedSquares = 0; 
		squares = new Square[rowCount][colCount];
		for(int x = 0; x < rowCount; x++)
			for(int y = 0; y < colCount; y++)
				squares[x][y] = new Square(0, SquareStatus.ACTIVE);
	}
	
	public SquareStatus changeSquareStatus(BoardPoint point, boolean mark) {
		Square square = squares[point.getX()][point.getY()];
		
		if(mark) markSquare(square, point);

		if(!mark && square.getSquareStatus() == SquareStatus.ACTIVE) {
			if(square.getNumber() == 0) {
				changeZeroSquare(point);
			} else {
				changeSquareStatusAndNotify(square, point, SquareStatus.PLAYED);
				playedSquares = getPlayedSquares() + 1;
			}
		}
		return square.getSquareStatus();
	}
	
	private void markSquare(Square square, BoardPoint point) {
		if(square.getSquareStatus() == SquareStatus.ACTIVE) {
			changeSquareStatusAndNotify(square, point, SquareStatus.MARKED);
		} else if(square.getSquareStatus() == SquareStatus.MARKED) {
			changeSquareStatusAndNotify(square, point, SquareStatus.ACTIVE);
		}
	}
	
	public void changeZeroSquare(BoardPoint point) {	
		Square centralSquare = squares[point.getX()][point.getY()]; 

		if(centralSquare.getSquareStatus() == SquareStatus.ACTIVE) {
			changeSquareStatusAndNotify(centralSquare, point, SquareStatus.PLAYED);
			
			playedSquares = getPlayedSquares() + 1;
			
			if(centralSquare.getNumber() == 0) {
				iterateAdjacentSquares(point, a -> { changeZeroSquare(a); });
			}
		}
	}
	
	private void changeSquareStatusAndNotify(Square square, BoardPoint point,SquareStatus newStatus) {
		SquareStatus previous = square.getSquareStatus();
		square.setSquareStatus(newStatus);
		
		notifyObservers(square, previous, point);
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
		return point.getX() >= 0 && point.getX() < rowCount && point.getY() >= 0 && point.getY() < colCount;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append("\t");
		
		for(int i = 0; i < rowCount; i++) {
			str.append(i);
			str.append("\t");	
		}
		str.append("\n\n");
		
		for(int col = 0; col < colCount; col++) {
			str.append(col);
			str.append(":\t");
			for(int row = 0; row < rowCount; row++) {
				String data;
				
				if(squares[row][col].getSquareStatus() == SquareStatus.PLAYED) {
					if(squares[row][col].getNumber() == Square.MINE) data = "X";
					else if (squares[row][col].getNumber() == 0) data = " ";
					else data = Integer.toString(squares[row][col].getNumber());
					str.append(data);
				}
				else if(squares[row][col].getSquareStatus() == SquareStatus.MARKED)
					str.append("*");
				else
					str.append("_");
				
				
				str.append("\t" );
			}
			str.append(":");
			str.append(col);

			str.append("\n");
		}
		str.append("\n");
		return str.toString();
	}
	
	// getters and setters
	public int getPlayedSquares() {
		return playedSquares;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColCount() {
		return colCount;
	}
}
