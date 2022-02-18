package gui.controller;

import gui.view.Dialogs;
import gui.view.MainView;
import mines.Board;
import mines.BoardPoint;
import mines.Game;
import mines.GameStatus;

public class MainController implements MineController{
	private MainView view;
	private Game game;
	
	public MainController() {
		Board board = new Board(10, 10);

		
		game = new Game(board, 20);

		view = new MainView(this, 10, 10);
		board.registerObserver(view);
	}
	
	public void initialize() {
		view.initialize();
	}
	
	public Object getView() {
		return view.getPane();
	}

	@Override
	public void mineButtonClicked(BoardPoint point, boolean mark) {
		game.changeSquareStatus(point, mark);
		
		if(game.getStatus() == GameStatus.WIN) {
		    Dialogs.showWinDialog();
		    view.disableButtons();
		} else if(game.getStatus() == GameStatus.LOST) {
			Dialogs.showLostDialog();
			view.disableButtons();
		}
	}
}
