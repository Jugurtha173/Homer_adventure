package model.environement;

import java.util.Scanner;
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
    
    public void unLock(){
        super.unLock();
        super.open();
    }

}
