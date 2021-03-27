package model.myObjects;

import model.characters.MyCharacter;

public class Lamp extends MyObject {
	
	public Lamp() {
		super("Lamp");
	}

	@Override
	public String descriptif() {
		
		return "Lamp, you can light up rooms with it";
	}

	@Override
	public void use(MyCharacter c) {
		c.getCurrentRoom().lightUp();
		
	}

}
