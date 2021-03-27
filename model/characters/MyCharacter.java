package model.characters;

import model.myObjects.MyObject;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import model.environement.Room;

public abstract class MyCharacter {
    
    protected final static int MAX_HP = 20;
    public static final String DEFAULT_NAME = "UNKNOWN";
    private Boolean alive = true;
    private final String name;
    private int HP;
    public DoubleProperty hpProperty = new SimpleDoubleProperty();
    private Room currentRoom;
    protected ImageView img;


    int x, y;

    public List<MyObject> inventory = new ArrayList<>();

    public MyCharacter() {
            this.name = DEFAULT_NAME;
    }

    public MyCharacter(String name) {
            this.name = name;
            this.HP = MAX_HP;
    }
    public MyCharacter(String name, String url) {
            this.name = name;
            this.HP = MAX_HP;
            this.img = new ImageView(url);
            this.img.setFitHeight(100);
            this.img.setFitWidth(50);
            this.x = 2;
            this.y = 2;
    }

    public int getHP() {
            return this.HP;
    }

    public void editHP(int change) {
        this.HP += change;
        if(this.HP > MAX_HP){
                this.HP = MAX_HP;
        } else if (this.HP <= 0) {
                this.alive = false;
        }
        double h = ((this.HP / 20f));
        this.hpProperty.setValue(h);     
    }

    public Boolean isAlive() {
            return this.alive;
    }

    public void showHP() {
            int level = this.getHP();
            if(level < 0) level = 0;

            String hp = this.getName() + " HP : [";
            for (int i = 0; i < MAX_HP; i++) {
                    if(i < level) {
                            hp += "#";
                    } else {
                            hp += "-";
                    }
            }
            // afficher un pourcentage
            hp += "] " + ((level * 100) / MAX_HP) + "%\n";
            System.out.println(hp);
    }

    public String getName() {
            return this.name;
    }

    public Room getCurrentRoom() {
            return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
            this.currentRoom = currentRoom;
    }

    public void dropAllInventory() {
            for(MyObject obj : this.inventory) {
                    this.getCurrentRoom().addObject(obj);
            }
            this.inventory.clear();

    }

    @Override
    public String toString() {	
            return this.name;
    }

    public void die() {
            if( this.getHP() <= 0) {
                    System.err.println(this.getName() + " is dead !");
                    this.alive = false;
                    this.dropAllInventory();
            }

    }

    public ImageView getImg(){
        return this.img;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
