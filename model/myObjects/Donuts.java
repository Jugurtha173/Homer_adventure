package model.myObjects;

import model.characters.MyCharacter;

public class Donuts extends MyObject{

	
	public Donuts() {
		super("Donuts",0);
	}
	
	@Override
	public String descriptif() {
		return this.toString();
	}

	@Override
	public void use(MyCharacter c) {
		System.out.println();
		
	}

	
	
}
