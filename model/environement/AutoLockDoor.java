package model.environement;

public class AutoLockDoor extends Door {
    
    private boolean lock = true;
    
    public AutoLockDoor(){
       super(false);
    }
    
    public AutoLockDoor(Room room1, Room room2) {
    	super(room1, room2);
    }
    
    @Override
    public void open(){
        if( !(this.lock) ) {
        	super.open();
        } else { 
        	System.out.println("The door is locked! Use key to unlock.");
        	
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
            System.out.println("The door is now unlock! You can go.");
        }
        	
    }

    
}
