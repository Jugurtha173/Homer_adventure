package model.myObjects;

import model.GameModel;
import model.characters.MyCharacter;


public class Duff extends MyObject{
	
	private boolean full = true;
	
	public Duff() {
            super("Duff", 2, "view/img/duff.png", 0, 0 );
	}
        
	public Duff(int x, int y) {
            super("Duff", 2, "view/img/duff.png" ,  x,  y);
	}
	
	public boolean isEmpty() {
		return !(this.full);
	}

	public void fill() {
		this.full = true;
		GameModel.show("Duff filled");
	}
	
	@Override
	public String descriptif() {
		
		return "Duff" + (this.full ? " [Full]" : " [Empty]") ;
	}

	
	public Duff getNextFullDuff(MyCharacter c) {
		for(MyObject obj : c.inventory)
			if(obj instanceof Duff && !((Duff) obj).isEmpty())
				return (Duff)obj;
			
		GameModel.show("You have no full duff in your inventory");
		return null;
		
	}
	
	public Duff getNextEmptyDuff(MyCharacter c) {
		for(MyObject obj : c.inventory)
			if(obj instanceof Duff && ((Duff) obj).isEmpty())
				return (Duff)obj;
			
		GameModel.show("You have no Empty duff in your inventory");
		return null;
		
	}
	
	@Override
	public void use(MyCharacter c) {
		Duff duff;
		if(this.full) 
			duff = this;
		else 
			duff = getNextFullDuff(c);

		if(duff == null) {	
			return;
		} else {
			GameModel.show("Drinkink duff ... YES !");
			c.editHP(duff.getHealthEffect());
			duff.full = false;
		}
	}
}
