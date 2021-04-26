package model.myObjects;

import model.characters.MyCharacter;
import model.characters.Hero;

public class Uranium extends MyObject{
	public Uranium() {
            super("Uranium", -13, "view/img/uranium.png", 1, 4);
	}
	
	@Override
	public String descriptif() {
            return this.toString();
	}

	@Override
	public void use(MyCharacter c) {
            ((Hero)c).attack(((Hero)c).enemyInRoom(), this);
	}
}
