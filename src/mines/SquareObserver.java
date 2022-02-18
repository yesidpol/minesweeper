package mines;

@FunctionalInterface
public interface SquareObserver {
	void squareChanged(SquareStatusChangedArgs args);
}
