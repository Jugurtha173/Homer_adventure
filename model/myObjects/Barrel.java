package model.myObjects;

import model.GameModel;
import model.characters.MyCharacter;

public class Barrel extends MyObject {
		
	private int level = 5;

	public Barrel() {
		super("Barrel", 0, "view/img/barrel.png", 1, 1);
	}
	
	@Override
	public String descriptif() {	
		return this.toString() + "[level "+ this.level +"]";
	}

	@Override
	public void use(MyCharacter c) {
            if(this.level <= 0) {
                    GameModel.show("Barrel is empty, can't fill");
                    return;
		}
            // Remplire la premiere bouteille de duff vide dand l'inventaire
            Duff nextEmptyDuff = Duff.getNextEmptyDuff(c);
            if(nextEmptyDuff != null) {
                    nextEmptyDuff.fill();
                    this.level--;
                    return;
            }
            GameModel.show("You can fill "+ this.level +" duff bottles with this barrel");
	}
	
	public void use(MyCharacter c, MyObject duff) {
		if(this.level <= 0) {
			GameModel.show("Barrel is empty, can't fill");
			return;
		}
		
		if(!(duff instanceof Duff)) {
			GameModel.show("Barrel can fill only empty duff bottles");
			return;
		}		
		// Remplire la premiere bouteille de duff vide dand l'inventaire
		Duff nextEmptyDuff = Duff.getNextEmptyDuff(c);
		if(nextEmptyDuff != null) {
			nextEmptyDuff.fill();
			this.level--;			
		}

	}

}
