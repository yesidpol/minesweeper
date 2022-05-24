package gui.controller;

import command.Command;
import enums.Actions;
import enums.DifficultyLevel;
import enums.GameStatus;
import factory.GameFactory;
import gui.view.BoardView;
import gui.view.Dialogs;
import gui.view.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mines.BoardPoint;
import mines.Game;

public class MainController implements MineController {
    private Stage primaryStage;
    private BoardView view;
    private MainView mainView;
    private Game game;
    public static final DifficultyLevel defaultLevel = DifficultyLevel.BEGINNER;
    
    private DifficultyLevel selectedLevel;
    
    public MainController(Stage primaryStage) {
        mainView = new MainView();
        selectedLevel = defaultLevel;
        this.primaryStage = primaryStage;
    }
    
    public void initialize() {
        initializeActions();
        initializeGame(MainController.defaultLevel);
        initializeScene();
    }
    
    public Object getView() {
        return mainView.getPane();
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
    
    private void initializeActions() {
        Command newGame = () -> initializeGame(selectedLevel);
        Command beginner = () -> initializeGame(DifficultyLevel.BEGINNER);
        Command intermediate = () -> initializeGame(DifficultyLevel.INTERMEDIATE);
        Command expert = () -> initializeGame(DifficultyLevel.EXPERT);
        
        mainView.initialize();
        mainView.setAction(Actions.NEW_GAME, newGame);
        mainView.setAction(Actions.BEGINNER, beginner);
        mainView.setAction(Actions.INTERMEDIATE, intermediate);
        mainView.setAction(Actions.EXPERT, expert);
        
        mainView.getPane().setOnKeyPressed(
            event -> {
                switch(event.getCode()) {
                case F2:
                    newGame.execute();
                    break;
                default:
                    break;
                }
            });
    }
    
    private void initializeScene() {
        Scene scene = new Scene((Parent)this.getView());
        scene.getStylesheets().add("/resources/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
    
    private void initializeGame(DifficultyLevel level) {
        selectedLevel = level;
        
        game = GameFactory.createBoard(level);

        view = new BoardView(this, game.getRowCount(), game.getColCount());
        game.getBoard().registerObserver(view);
        
        view.initialize();
        mainView.setContent(view.getPane());
        
        primaryStage.sizeToScene();
    }
}
