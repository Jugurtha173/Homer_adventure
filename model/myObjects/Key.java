package model.myObjects;

import java.util.List;
import model.GameModel;
import model.environement.AutoLockDoor;
import model.characters.MyCharacter;
import model.environement.Door;

public class Key extends MyObject {

	public Key() {
            super("Key", 0, "view/img/key.png", 2, 3);
	}
	
	@Override
	public String descriptif() {
            return this.toString()+" use it to unlock doors!";
	}

	@Override
	public void use(MyCharacter c) {
            GameModel.show(this.descriptif());	
            List <Door> doors= c.getCurrentRoom().getDoors();
            for(Door door : doors) {
                if(door instanceof AutoLockDoor) {
                        ((AutoLockDoor) door).unLock();
                }	
            }
	}

}
