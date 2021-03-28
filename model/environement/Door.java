package model.environement;

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
    	this(room1, room2);
        this.x = x;
        this.y = y;
    }
    
     public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
        return "porte " + this.getClass().getSimpleName() + "[etat = " + this.isState() + "]\n";
    }


    public String getOtherRoom(Hero homer) {
        Room r = this.room[0] != homer.getCurrentRoom() ? this.room[0] : this.room[1] ;
        return r.getName();
    }

    
    
}
