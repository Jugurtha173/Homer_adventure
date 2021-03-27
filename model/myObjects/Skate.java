package model.myObjects;

import model.GameModel;
import model.characters.MyCharacter;


public class Skate extends MyObject{
    public Skate() {
    	super("Skate", -3, "view/img/skate.png", 0, 0);
    }
    
    public Skate(int x, int y) {
    	super("Skate", -3, "view/img/skate.png",  x,  y);
    }
    
    @Override
    public String descriptif() {

            return this.toString() +":  Damage: " + this.getHealthEffect();
    }

    @Override
    public void use(MyCharacter c) {
            GameModel.show(this.descriptif());	
    }
}
