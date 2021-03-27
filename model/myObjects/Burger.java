package model.myObjects;

import model.GameModel;
import model.characters.MyCharacter;

public class Burger extends MyObject{

	public Burger() {
		super("Burger", 3, "view/img/burger.png", 3, 2);
	}
	
	@Override
	public String descriptif() {
		
		return this.toString()+" use to gain energy!";
	}

	@Override
	public void use(MyCharacter c) {
		c.editHP(this.getHealthEffect());
		GameModel.show("Eating burger ... YES !");
		c.inventory.remove(this);
	}
    
}
