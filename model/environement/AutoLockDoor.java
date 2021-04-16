package model.environement;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import model.GameModel;

public class AutoLockDoor extends Door {
    
    private boolean lock = true;
    
    public AutoLockDoor(){
       super(false);
    }
    
    public AutoLockDoor(Room room1, Room room2) {
    	super(room1, room2);
    }
    
    public AutoLockDoor(Room room1, Room room2, int x, int y) {
    	super(room1, room2, x, y);
    }
    
    @Override
    public void open(){
        if( !(this.lock) ) {
            super.open();
        } else { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NOPE !");
            alert.setHeaderText("This door is locked!");
            alert.setContentText("Use a key to unlock.");
            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOk);
            alert.showAndWait();
        	
        }
    }
    
    @Override
    public void close(){
        super.close();
        this.lock = true;
    }
    
    public void unLock(){
        if( !(super.isState())) {
            this.lock = false;
            GameModel.show("The door is now unlock! You can go.");
        }
        	
    }

    
}
