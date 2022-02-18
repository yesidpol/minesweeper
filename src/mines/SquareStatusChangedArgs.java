package mines;

public final class SquareStatusChangedArgs {
	final private Square square;
	final private SquareStatus previous;
	final private BoardPoint point;
	
	public SquareStatusChangedArgs(Square square, SquareStatus previous, BoardPoint point) {
		this.square = new Square(square.getNumber(), square.getSquareStatus());
		this.previous = previous;
		this.point = new BoardPoint(point.getX(), point.getY());
	}

	public Square getSquare() {
		return square;
	}

	public SquareStatus getPrevious() {
		return previous;
	}

	public BoardPoint getPoint() {
		return point;
	}
	
	
}
