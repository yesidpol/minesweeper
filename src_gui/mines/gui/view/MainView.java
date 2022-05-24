package mines.gui.view;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mines.gui.command.Command;
import mines.gui.enums.Actions;

public class MainView {
    private VBox pane;
    private Pane content;
    
    private Map<Actions, MenuItem> menuItems;
    
    public MainView() {
    }
    
    public Pane getPane() {
        return pane;
    }

    public void initialize() {
        MenuItem newGame = new MenuItem("New game");
        RadioMenuItem levelBegginer = new RadioMenuItem("Begginer");
        RadioMenuItem levelIntermediate = new RadioMenuItem("Intermediate");
        RadioMenuItem levelExpert = new RadioMenuItem("Expert");
        ToggleGroup tgv = new ToggleGroup();
        
        levelBegginer.setToggleGroup(tgv);
        levelIntermediate.setToggleGroup(tgv);
        levelExpert.setToggleGroup(tgv);
        
        Menu config = new Menu("Config");
        
        ObservableList<MenuItem> items = config.getItems();
        
        items.add(newGame);
        items.add(levelBegginer);
        items.add(levelIntermediate);
        items.add(levelExpert);
        
        MenuBar menuBar = new MenuBar(config);
        
        pane = new VBox();
        
        pane.getChildren().add(menuBar);
        
        menuItems = new HashMap<Actions, MenuItem>();
        
        menuItems.put(Actions.NEW_GAME, newGame);
        menuItems.put(Actions.BEGINNER, levelBegginer);
        menuItems.put(Actions.INTERMEDIATE, levelIntermediate);
        menuItems.put(Actions.EXPERT, levelExpert);
    }
    
    public void setAction(Actions idAction, Command command) {
        MenuItem item = menuItems.get(idAction);
        item.setOnAction(e -> {
            command.execute();
        });
    }

    public void setContent(Pane value) {    
        if(this.content != null) {
            pane.getChildren().remove(content);
        }
        this.content = value;
        pane.getChildren().add(value);
    }
}