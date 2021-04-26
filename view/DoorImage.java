/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import model.characters.Hero;
import model.environement.Door;

/**
 *
 * @author JUGURTHA
 */
public class DoorImage extends ImageView {
    private final Door door;
    private final Hero myHero;
    
   public DoorImage(Door door, Hero myHero){
        super("view/img/door.png");
        this.door = door;
        this.myHero = myHero;

        this.setFitHeight(100);
        this.setFitWidth(60);
        this.setCursor(Cursor.CLOSED_HAND);
        Tooltip tooltip = new Tooltip("GO to " + door.getOtherRoom(myHero));
        Tooltip.install(this, tooltip); 
   }

    public Door getDoor() {
        return door;
    }

    public Hero getMyHero() {
        return myHero;
    }
    
}
