package model.environement;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import model.GameModel;

public class SecretCodeDoor extends AutoLockDoor{
    
    private final int SECRET_CODE;
    
    public SecretCodeDoor(){
        super(); 
        this.SECRET_CODE = 1703;
    }
    
    public SecretCodeDoor(Room room1, Room room2, int x, int y) {
    	super(room1, room2, x, y);
    	this.SECRET_CODE = 1703;
    }
    
    public SecretCodeDoor(Room room1, Room room2, int code) {
    	super(room1, room2);
    	this.SECRET_CODE = code;
    }
    
    public SecretCodeDoor(Room room1, Room room2, int code, int x, int y) {
    	super(room1, room2, x, y);
    	this.SECRET_CODE = code;
    }
   
    @Override
    public void open() {
    	this.unLock();    	
    }
    
    @Override
    public void unLock(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Secret code door");
        dialog.setHeaderText("Tape the code !");
        dialog.setContentText("Please enter the code:");

        Optional<String> result = dialog.showAndWait();
        try {
            if (result.get().equals(""+ this.SECRET_CODE)){
                super.unLock();
                super.open();
            } else{
                GameModel.showMessage("CODE INCORRECT !!!", "RED");
            }
        } catch (Exception e) {}
        
    }
}
