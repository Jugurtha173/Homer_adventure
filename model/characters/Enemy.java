package model.characters;

import model.GameModel;
import model.environement.Door;
import model.myObjects.MyObject;

public class Enemy extends MyCharacter implements Attackable{
    
    private Door myDoor;

    public Enemy(String name) {
            super(name);
    }

    public Enemy(String name, String url) {
            super(name, url);
    }


    @Override
    public void beAttacked(int damage) {
            if (damage > 0 ) {
                    damage = -damage;			
            }
            this.editHP(damage);
            this.showHP();
            // on verifie le hero est toujours vivant
            this.die();
    }

    @Override
    public void attack(Attackable target) {
            String detail = "DOH ! you've been attacked by "+ this.getName()+ ", with a ";
            // l'ennemie attack la cible avec le premier objet de son invetaire si ce dernier n'est pas vide
            if(this.inventory.size() != 0) {
                    detail += inventory.get(0).toString() + ", " + ((this.inventory.get(0).getHealthEffect() * 100) / MyCharacter.MAX_HP) ;
                    target.beAttacked(this.inventory.get(0).getHealthEffect());		
                    // on perds le premier objet de l'inventaire
                    this.getCurrentRoom().addObject(this.inventory.get(0));
                    System.out.println("----------- ici ");
                    GameModel.controller.syncRoom();
                    System.out.println("normalement room Sync");
                    this.inventory.remove(this.inventory.get(0));
            } else {
                    // sinon on frappe la cible avec un coup-de-poing ( un coup-de-poing implique -5% de points de vie)
                    detail += " punch -5";
                    target.beAttacked(-1);
            }


            detail += "% HP, you must kill him before !";
            GameModel.show(detail);
    }

    @Override
    public void attack(Attackable target, MyObject object) {
            target.beAttacked(object.getHealthEffect());	

    }

	
    
}
