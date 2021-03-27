package model.myObjects;

import model.characters.MyCharacter;
import model.characters.Hero;


public class Knife extends MyObject{

	public Knife() {
		super("Knife", -5);
		
	}
	
	@Override
	public String descriptif() {
		
		return this.toString();
	}

	@Override
	public void use(MyCharacter c) {
		((Hero)c).attack(((Hero)c).enemyInRoom());
	}
    
}
