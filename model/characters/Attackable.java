package model.characters;

import model.myObjects.MyObject;

public interface Attackable {
	
	public void beAttacked(int damage);
	
	
	public void attack(Attackable target);

	
	public void attack(Attackable target, MyObject object);
	
	
	
}
