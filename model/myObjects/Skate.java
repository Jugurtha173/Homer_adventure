package model.myObjects;

import model.characters.Hero;
import model.characters.MyCharacter;


public class Skate extends MyObject{
    public Skate() {
    	super("Skate", -3, "view/img/skate.png", 1, 2);
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
            ((Hero)c).attack(((Hero)c).enemyInRoom(), this);	
    }
}
