package model.environement;

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
        	GameModel.show("The door is locked! Use key to unlock.");
        	
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
