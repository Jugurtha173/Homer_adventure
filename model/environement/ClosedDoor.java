package model.environement;

public class ClosedDoor extends Door{
    
    
    public ClosedDoor(){
        super(false);
    }
    
    public ClosedDoor(Room room1, Room room2){
    	super(room1, room2);
    }
    
    
    
    @Override
    public void open(){
        System.err.println("This door is closed, you can't open it !");
    }
    
}
