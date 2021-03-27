package model.myObjects;

import model.characters.MyCharacter;

public class Burger extends MyObject{

	public Burger() {
		super("Burger", 3);
	}
	
	@Override
	public String descriptif() {
		
		return this.toString()+" use to gain energy!";
	}

	@Override
	public void use(MyCharacter c) {
		c.editHP(this.getHealthEffect());
		System.out.println("Eating burger ... YES !");
		c.inventory.remove(this);
	}
    
}
