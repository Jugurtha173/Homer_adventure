package model.environement;

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
        GameModel.show("This door is closed, you can't open it !");
    }
    
}
