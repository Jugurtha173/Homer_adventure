package model.environement;

import java.util.List;
import model.characters.Enemy;
import model.characters.Hero;

public class Door {
    
    
    public Room[] room = new Room[2];
    public Enemy guard = null;
    
    private boolean state;
    private int x;
    private int y;
    
    public Door(){
        this.state = false;
    }
    
    public Door(boolean state){
        this.state = state;
    }
    
    public Door(Room room1, Room room2) {
    	this();
    	this.room[0] = room1;
    	this.room[1] = room2;
    	
    	this.room[0].addDoor(this);
    	this.room[1].addDoor(this);
    }
    
    public Door(Room room1, Room room2, int x, int y) {
    	this();
    	this.room[0] = room1;
    	this.room[1] = room2;
    	
    	this.room[0].addDoor(this);
    	this.room[1].addDoor(this);
        
        this.x = x;
        this.y = y;
    }
    
     public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void open(){
        this.state = true;
    }
    
    public void close(){
        this.state = false;
    }

    public boolean isState() {
        return state;
    }
 
    @Override
    public String toString() {
        return "porte " + this.getClass().getSimpleName() + "[etat = " + this.isState() + "]\n"
                + "room 0 :" + room[0].getName() + "\n"
                + "room 1 :" + room[1].getName() + "\n\n"
                ;
    }


    public String getOtherRoom(Hero homer) {
        //List<Door> doors = homer.getCurrentRoom().getDoors();
        //int index = doors.indexOf(this);
        //System.out.println("doors.get(index) ----------->\n" + (doors.get(index)).toString());
        //Room r = doors.get(index).equals(this.room[0]) ? this.room[0] : this.room[1];
        Room r = this.room[0] != homer.getCurrentRoom() ? this.room[0] : this.room[1] ;
        //System.out.println("other room : --------------" + r.getName());
        return r.getName();
    }

    
    
}
