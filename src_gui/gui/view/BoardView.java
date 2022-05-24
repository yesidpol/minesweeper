package gui.view;

import java.util.function.BiConsumer;

import gui.controller.MineController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mines.BoardPoint;
import mines.Square;
import mines.SquareObserver;
import mines.SquareStatusChangedArgs;

public class BoardView  implements SquareObserver{

    private GridPane pane;
    private Button[][] buttons;
    
    final private int rowCount;
    final private int colCount;
    
    private MineController controller;
    
    private static final int SQUARE_SIZE = 26;
    
    public BoardView(MineController controller, int rows, int cols) {
        this.controller = controller;
        this.rowCount = rows;
        this.colCount = cols;
    }
    
    public void initialize() {
        buttons = new Button[rowCount][colCount];
        initButtons();
        initGrid();
    }
    
    private void initGrid() {
        pane = new GridPane();
        iterateButtons(new BiConsumer<Integer, Integer>() {    
            @Override
            public void accept(Integer row, Integer col) {
                pane.add(buttons[row][col], row, col);
            }
        });
    }
    
    private void initButtons() {
        iterateButtons(new BiConsumer<Integer, Integer>() {    
            @Override
            public void accept(Integer row, Integer col) {
                Button btn = new Button();
                
                btn.setMinWidth(SQUARE_SIZE);
                btn.setMinHeight(SQUARE_SIZE);
                
                btn.setUserData(new BoardPoint(row,col));
                
                btn.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton() == MouseButton.SECONDARY) {

                            controller.mineButtonClicked((BoardPoint)btn.getUserData(), true);
                        } else if (event.getButton() == MouseButton.PRIMARY) {                            
                            controller.mineButtonClicked((BoardPoint)btn.getUserData(), false);
                        }
                    }
                });
                
                buttons[row][col] = btn;
            }
        });
    }
    
    public void disableButtons() {
        iterateButtons(new BiConsumer<Integer, Integer>() {    
            @Override
            public void accept(Integer row, Integer col) {
                buttons[row][col].setDisable(true);
            }
        });
    }
    
    public Pane getPane() {
        return pane;
    }

    @Override
    public void squareChanged(SquareStatusChangedArgs args) {
        Square square = args.getSquare();
        BoardPoint point = args.getPoint();
        
        Button b = buttons[point.getX()][point.getY()];
        
        switch(square.getSquareStatus()) {
        case PLAYED:
            if(square.getNumber() == Square.MINE) {
                b.setText("💀");
            }
            else if (square.getNumber() == 0) {
                b.setVisible(false);
            }
            else {
                String numberStr = Integer.toString(square.getNumber());
                b.getStyleClass().add("n" + numberStr);
                b.setText(numberStr);
            }
        break;
        case MARKED:
            b.setText("🚩");
        break;
        case ACTIVE:
            b.setText("");
        break;
        }
    }

    private void iterateButtons(BiConsumer<Integer, Integer> consumer) {
        for(int row = 0; row < buttons.length; row++) {
            for(int col=0; col < buttons[0].length; col++) {
                consumer.accept(row, col);
            }
        }
    }
}
