package model.myObjects;

import java.util.List;
import model.environement.AutoLockDoor;
import model.characters.MyCharacter;
import model.environement.Door;

public class Key extends MyObject {

	public Key() {
		super("Key");
	}
	
	@Override
	public String descriptif() {
		
		return this.toString()+" use it to unlock doors!";
	}

	@Override
	public void use(MyCharacter c) {
		System.out.println(this.descriptif());	
		List <Door> doors= c.getCurrentRoom().getDoors();
		for(Door door : doors) {
			if(door instanceof AutoLockDoor) {
				((AutoLockDoor) door).unLock();
			}
			
		}
	}

}
