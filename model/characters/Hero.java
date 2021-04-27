package model.characters;

import java.util.List;
import java.util.Scanner;
import javafx.scene.image.ImageView;
import model.GameModel;
import model.myObjects.Barrel;
import model.myObjects.Donuts;
import model.environement.Door;
import model.myObjects.MyObject;
import model.environement.Room;

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

    @Override
    public int getX(){
        return this.posX;
    }


    @Override
    public int getY(){
        return this.posY;
    }


    @Override
    public void setX(int x){
        this.posX += x;
    }


    @Override
    public void setY(int y){
        this.posY += y;
    }

    public void moveX(int x){
        this.posX = x;
    }      
	
    public void moveY(int y){
        this.posY = y;
    }
	
    //aller dans une salle
    public boolean go(String room) {
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
                    return false;
                }
                // on ouvre sa porte
                door.open();
                // on entre dans la Room si elle est ouverte
                if(door.isState()) {
                    this.changeRoom(r);
                    door.close();
                    return true;
                }
                // on regarde ou on a att�rit
                //this.look();
                // c'est bon on sort de la methode
                return false;		
            }
        }
        // ici on a donc pas trouv� la Room
        GameModel.show("!!! Room not found !!!");
        return false;
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
                GameModel.show(object + " taken");
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
        GameModel.win();
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
        return ""
            +"talk:\n"
            + "Click on a persons to talk with him (Not enemies)\n\n"
            +"use object:\n"
            + "Click on object in the inventory to use it )\n\n"
            +"take object:\n"
            + "Click on object in the room to take it\n\n"
            +"look:\n"
            + "Click on the eye button to see everything around you in the room\n\n"
            +"look object:\n"
            + "Hover on object to know more about it\n\n"
            +"attack:\n"
            + "Click on an enemy to attack him \n\n"
            +"go room:\n"
            + "Click on a door to go in other room (if you can)\n\n"
            +"quit:\n"
            + "Click on the bottom right button to exit the game\n\n"
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
        if(target == null){
            GameModel.show("No enemy to attack");
            return;
        }
        if (object == null) {
            target.beAttacked(-1);
        } else {
            target.beAttacked(object.getHealthEffect());
            //this.drop(object.toString());
        }
        GameModel.showMessage(((MyCharacter)target).getName() + " HP : " + ((MyCharacter)target).getHP(), "#E9967A" );
    }

    //parler avec un personnage
    public void talk() {
        MyCharacter target = this.getCurrentRoom().getCharacters().get(0);
        if(target != null && target instanceof Other) {
            this.talkTo((Talkable)target);
        } else
            GameModel.show("There's no one to talk with here");
    }

    @Override
    public void talkTo(Talkable t) {
        t.talkTo((Talkable)this);
    }	
}
