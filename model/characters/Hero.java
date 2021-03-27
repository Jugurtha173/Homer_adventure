package model.characters;

import java.util.List;
import java.util.Scanner;
import javafx.beans.property.IntegerProperty;
import javafx.scene.image.ImageView;
import model.GameModel;
import model.characters.Attackable;
import model.myObjects.Barrel;
import model.characters.MyCharacter;
import model.myObjects.Donuts;
import model.environement.Door;
import model.environement.Door;
import model.characters.Enemy;
import model.environement.NuclearCentral;
import model.environement.NuclearCentral;
import model.myObjects.MyObject;
import model.characters.Other;
import model.environement.Room;
import model.environement.Room;
import model.characters.Talkable;

/**
 *
 * @author JUGURTHA
 */
public class Hero extends MyCharacter implements Attackable, Talkable{
    
    int posX = 4;
    int posY = 4;
    

    Scanner action = new Scanner(System.in);
    Scanner choice = new Scanner(System.in);
    private boolean win = false;
    private boolean quit = false;
    private boolean cheat = false;
	
	public Hero(String name) {
		super(name, "view/img/homer.gif");
	}
	
        public int getX(){
            return this.posX;
        }
        
	
        public int getY(){
            return this.posY;
        }
        
	
        public void setX(int x){
            this.posX += x;
        }
        
	
        public void setY(int y){
            this.posY += y;
        }
        
        public ImageView getImg(){
            return this.img;
        }
        
        public void setImg(ImageView img){
            this.img = img;
        }
        
	public void play() {
		while (!win && !quit && this.isAlive()) {
			System.out.print("$>");
			String line = action.nextLine();
			String[] argv = line.split(" ");
			this.evalAction(argv);			
		}	
	}

	public void evalAction(String[] argv) {
		switch (argv[0].toLowerCase()) {
		case "go": {
			try {		
				this.go(argv[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				GameModel.show("Where do want to go ?");
			}
			break;
		}
		case "help": {
			this.help();
			break;
		}
		case "look": {
			try {		
				this.look(argv[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				this.look();
			}
			
			break;		
		}
		case "take": {
			this.take(argv[1]);
			break;
		}
		case "drop":{
			try {
				this.drop(argv[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				GameModel.show("You can drop an Object in your inventory");
			}
			break;
		}
		case "quit": {
			this.quit();
			break;
		}
		case "use": {
			try {
				use(argv[1], argv[2]);
			} catch (IndexOutOfBoundsException e2) {
				try {
					this.use(argv[1]);
				} catch (ArrayIndexOutOfBoundsException e1) {
					GameModel.show("Use what ?");
				}
			}
			break;
		}
		case "talk": {
			this.talk();
			break;
		}
		case "inventory": {
			this.showInventory();
			break;
		}
		case "attack": {
			// si il n'y a pas d'ennemie ou que l'ennemie est mort
			Enemy target = this.enemyInRoom();
			if(target != null && !(target.isAlive())) {
				GameModel.show("No ennemy alive here ! you can go ");
				break;
			}
				
			// sinon, on l'attaque avec l'objet en argument
			try {
				MyObject obj= this.findObjectInventory(argv[1]);
			    this.attack((Attackable) target, obj);
			    
			} catch (ArrayIndexOutOfBoundsException e) {
				
				this.attack((Attackable) target);
			}
			break;
		}
		case "cheat": {
			this.switchCheat();
			break;
		}
		
		default:
			GameModel.show("!!! Action incorrect !!!");
		}
	}
	
	//aller dans une salle
	public void go(String room) {
		// la liste des portes de la Room actuelle
		List<Door> ld = this.getCurrentRoom().getDoors();
		// on parcours les portes
		for(Door door : ld) {
			// pour chaque porte on recupere la Room d'a cote
			Room r = door.room[0] != this.getCurrentRoom() ? door.room[0] : door.room[1] ;
			// si c'est bien la Room qu'on veut
			if(r.getName().equalsIgnoreCase(room)) {
				// on verifie si il y a un ennemie
				if(door.guard != null && door.guard.isAlive()) {
					door.guard.attack((Attackable)this);
					return;
				}
				// on ouvre sa porte
				door.open();
				// on entre dans la Room si elle est ouverte
				if(door.isState()) {
					this.changeRoom(r);
					door.close();
				}
				// on regarde ou on a att�rit
				//this.look();
				// c'est bon on sort de la methode
				return;		
			}
			
		}
		// ici on a donc pas trouv� la Room
		GameModel.show("!!! Room not found !!!");
		
	}
	
	//changer de salle
	public void changeRoom(Room room) {
		this.getCurrentRoom().removeCharacter(this);
		this.setCurrentRoom(room);
		room.addCharacter(this);
	}
	
	//prendre un objet
	public void take(String object) {
		if(this.isCurrentLight()) {
			MyObject obj = findObject(object);
			if(obj != null) {
				this.inventory.add(obj);
				this.getCurrentRoom().getObjects().remove(obj);
				GameModel.show(object.toString() + " taken");
			}
			if(obj instanceof Donuts) {
				this.win = true;
				this.win();
			}
		}
	}
	
	//deposer un objet
	public void drop(String object) {
		MyObject obj = findObjectInventory(object);
		if(obj != null) {
			this.inventory.remove(obj);
			this.getCurrentRoom().addObject(obj);
			GameModel.show(object + " dropped");
		}
		
	}
	
	
	//gagner la partie
	public void win(){
		GameModel.show("YOUHOUUUUUUU! MY DONUTS \n\n\n\nGAME FINISH; YOU WON!!");
	}
	
	//test si une salle est eclairee ou non
	public boolean isCurrentLight() {
			return this.getCurrentRoom().isLigth;
		
	}
	
	//regarder ou on se trouve
	public String look() {
		if(this.getCurrentRoom().isLigth)
			return this.getCurrentRoom().descriptif();
                else return "Hmmmm !!! Can't see anything, this room is not enlightened";
		
	}
	
	//regarder un objet
	public void look(String object) {
		if(this.isCurrentLight()) {
			MyObject obj = findObject(object);
			if(obj != null) 
                            GameModel.show(obj.descriptif());
			else
                            GameModel.show("!!! There's no " + object + " here !!!");	
		}
	}
	
	//utiliser un objet
	public void use(String object) {
		MyObject obj = findObjectInventory(object);
		if(obj != null) {
			obj.use(this);
		}
	}
	
	//utiliser un objet avec un autre
	public void use(String obj1, String obj2) {
		MyObject object1 = findObjectInventory(obj1);
		if(object1 == null) {
			System.err.println("You have no "+ object1 +" in your inventory");
			return;
		}
		
		MyObject object2 = findObjectInventory(obj2);
		if(object2 == null) {
			System.err.println("You have no "+ object2 +" in your inventory");
			return;
		}
		
		((Barrel) object1).use(this, object2);
		
	}
	
	//quitter la partie
	public void quit() {
		GameModel.show("Really ? I'm always hungry, you want to RAGE QUIT ? ( enter q to quit)");
		if(this.action.nextLine().equalsIgnoreCase("q")) {
			this.quit  = true;
			GameModel.show("Okay! See you ");
			return;
		}
		GameModel.show("Thank you ! We go back");
	}
	 	
	//trouver une objet dans la salle
	public MyObject findObject(String object) {
		// la liste d'objets de la Room actuelle
		List<MyObject> lo = this.getCurrentRoom().getObjects();
		for(MyObject obj : lo) {
			if(obj.toString().equalsIgnoreCase(object)) {
				return obj;
			}
		}
		GameModel.show("!!! No " + object + " found here !!!");
		return null;
	}
	
	//retourne un objet dans l'inventaire 
	public MyObject findObjectInventory(String object) {
		// la liste d'objets de la Room actuelle
		List<MyObject> lo = this.inventory;
		for(MyObject obj : lo) {
			if(obj.toString().equalsIgnoreCase(object)) {
				return obj;
			}
		}
		GameModel.show("!!! No " + object + " in your inventory !!!");
		return null;
	}
	
	//voir l'inventaire
	public void showInventory() {
		if(this.inventory.size() < 0) {
			GameModel.showMessage("!!! Inventory is empty !!!");
		} else {
			for(MyObject obj : this.inventory ) {
				GameModel.showMessage(obj.toString());
			}
		}
	}
	
	//retourne l'ennemie qui est dans la salle
	public Enemy enemyInRoom() {
		List<MyCharacter> chars = this.getCurrentRoom().getCharacters();
		for(MyCharacter c : chars) {
			if(c instanceof Enemy) {
				return (Enemy) c;
			}	
		}
		return null;
	}
	
	// commande help  affiche la liste de commandes disponibles
	public String help() {
		return "MENU \n"
                        +"inventory: 	         Show the inventory\n"
                        +"talk:                    Talk with other persons in the room(Not enemies)\n"
                        +"use: 		         Use an object in your inventory (you'll choose which one)\n"
                        +"use object:	         Use the object (if it contains in inventory)\n"
                        +"use object1 object2:	 Use the object with the other one (ex. 'use barrel duff' to refill duff)\n"
                        +"take object:   	         Take an object\n"
                        +"drop object:   	         Drop an object from your inventory to the room\n"
                        +"look:     	         See everything around you in the room\n"
                        +"look object:  	         To know more about the object\n"
                        +"attack:        	         Attack the enemy in the room (if you inventory is empty, you'll punch him)\n"
                        +"attack object:        	 Attack the enemy in the room with the object (if you have not the object, you'll punch him)\n"
                        +"go room:  	         Go in other room (if you can)\n"
                        +"quit:    	         Exit the game\n"
                ;

	}
	
	
	
	@Override
	public void beAttacked(int damage) {
		this.editHP(damage);	
		//this.showHP();
		// on verifie le hero est toujours vivant
		this.die();
	}

	@Override
	public void attack(Attackable target) {
		// l'ennemie attack la cible avec son invetaire si ce dernier n'est pas vide
		//if(this.inventory.size() != 0) {
			GameModel.show("You can USE an object in your Inventory to attack");
			//this.showInventory();
			
			//String s = choice.next();
			//MyObject obj = findObjectInventory(s);
			
			//this.attack(target, obj);
		
		//} else {
			
			this.attack(target, null);
		//}
		
		// Cheat code
		if(!this.cheat) {
			target.attack(this);
		}
	}
	
	//mode triche
	public void switchCheat() {
		if(!this.cheat) GameModel.show("!!! NOW when you attack an enemy, he will not attack back !!!");
		else GameModel.show("!!! NOW when you attack an enemy, he will attack back !!!");
		
		this.cheat = !this.cheat;
	}

	@Override
	public void attack(Attackable target, MyObject object) {
		if (object == null) {
			GameModel.showMessage("let's punch him");
			target.beAttacked(-1);
		} else {
			target.beAttacked(object.getHealthEffect());
			//this.drop(object);
		}
                GameModel.showMessage(((MyCharacter)target).getName() + " HP : " + ((MyCharacter)target).getHP() );
		
	}
	
	//parler avec un personnage
	public void talk() {
            MyCharacter target = this.getCurrentRoom().getCharacters().get(0);
            if(target != null && target instanceof Other) {
                    this.talkTo((Talkable)target);
            }
            else
                    GameModel.show("There's no one to talk with here");
	}

	@Override
	public void talkTo(Talkable t) {
		t.talkTo((Talkable)this);
		
	}

	
}
