package model.environement;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import model.GameModel;

public class ClosedDoor extends Door{
    
    
    public ClosedDoor(){
        super(false);
    }
    
    public ClosedDoor(Room room1, Room room2){
    	super(room1, room2);
    }
    
    public ClosedDoor(Room room1, Room room2, int x, int y){
    	super(room1, room2, x, y);
    }
    
    
    
    @Override
    public void open(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("NOPE !");
        alert.setHeaderText("This door is closed");
        alert.setContentText("You can't open it !");
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOk);
        alert.showAndWait();
    }
    
}
