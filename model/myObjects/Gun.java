package model.myObjects;

import model.characters.MyCharacter;
import model.characters.Hero;

public class Gun extends MyObject{
	public Gun() {
		super("Gun", -10, "view/img/gun.png", 4, 1);
		
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
