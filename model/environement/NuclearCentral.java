package model.environement;

import controller.GameController;
import model.characters.Talkable;
import model.characters.Hero;
import model.characters.Enemy;
import model.characters.Other;
import model.myObjects.Lamp;
import model.myObjects.Donuts;
import model.myObjects.MyObject;
import model.myObjects.Duff;
import model.myObjects.Uranium;
import model.myObjects.Key;
import model.myObjects.Skate;
import model.myObjects.Knife;
import model.myObjects.Gun;
import model.myObjects.Burger;
import model.myObjects.Parchment;
import model.myObjects.Barrel;
import java.util.ArrayList;
import java.util.List;
import model.GameModel;

public class NuclearCentral {
	
    private final List<Room> Rooms = new ArrayList<>();

	
    public void init(Hero homer) {
        // initialisation

        // creation des lieux
        Room homerDesk = new Room("My Desk");
        Room hall = new Room("Hall");
        Room rest = new Room("Rest Room");
        Room b24 = new Room("B24");
        Room control = new Room("Control Room");
        Room kitchen = new Room("Kitchen");
        Room storage = new Room("Storage Room", false);
        Room moes = new Room("MOE'S Bar");
        Room engine = new Room("Engine Room");
        Room auditorium = new Room("Auditorium");
        Room employee = new Room("Employee Break Room");
        Room operations = new Room("Operations Center");
        Room production = new Room("Production Room");
        Room burnsDesk = new Room("Burns Office");	
        Room safety = new Room("Safety deposit box");	

        // ajout des lieux a la NuclearCenter
        this.Rooms.add(homerDesk);
        this.Rooms.add(hall);
        this.Rooms.add(rest);
        this.Rooms.add(b24);
        this.Rooms.add(control);
        this.Rooms.add(kitchen);
        this.Rooms.add(storage);
        this.Rooms.add(moes);
        this.Rooms.add(engine);
        this.Rooms.add(auditorium);
        this.Rooms.add(employee);
        this.Rooms.add(operations);
        this.Rooms.add(production);
        this.Rooms.add(burnsDesk);
        this.Rooms.add(safety);

        // creation des portes
        Door hd_h = new Door(homerDesk, hall,0, 2 );
        Door h_r = new Door(hall, rest, 3, 0);
        Door h_b = new Door(hall, b24, 2, 0);
        Door h_c = new Door(hall, control, 1, 0);
        Door r_k = new Door(rest, kitchen, 2, 0);
        Door b_c = new Door(b24, control, 0, 2);
        Door b_k = new Door(b24, kitchen, 3, 0 );
        ClosedDoor b_s = new ClosedDoor(b24, storage, 1, 0);
        Door c_m = new Door(control, moes, 2 ,0);
        Door k_e = new Door(kitchen, engine, 3, 0);
        AutoLockDoor k_a = new AutoLockDoor(kitchen, auditorium, 2, 0);
        Door k_s = new Door(kitchen, storage, 0, 2);
        Door s_m = new Door(storage, moes, 0, 2);
        Door m_o = new Door(moes, operations, 2, 0);
        Door e_a = new Door(engine, auditorium, 0, 2);
        SecretCodeDoor a_p = new SecretCodeDoor(auditorium, production, 2210, 2, 0);
        Door a_e = new Door(auditorium, employee, 0, 2);
        Door e_o = new Door(employee, operations, 0, 2);
        Door p_b = new Door(production, burnsDesk, 0, 2);
        SecretCodeDoor b_safety = new SecretCodeDoor(burnsDesk, safety, 0, 2);

        // creation des objets
        MyObject skate = new Skate();
        MyObject hamburger = new Burger();
        MyObject key = new Key();
        MyObject duff1 = new Duff();
        MyObject duff2 = new Duff();
        MyObject duff3 = new Duff();
        MyObject barrel= new Barrel();
        MyObject lamp = new Lamp();
        MyObject parchment = new Parchment();
        MyObject donuts = new Donuts();


        // creation des personnages
        Other moe = new Other("Moe", "yes", "view/img/moe.png");
        moe.addSpeechs(Talkable.speechOfMoe1, Talkable.speechOfMoe2, Talkable.speechOfMoe3);
        moe.inventory.add(barrel);

        Other marge = new Other("Marge", "kiss", "view/img/marge.gif");
        marge.addSpeechs(Talkable.speechOfMarge1, Talkable.speechOfMarge2, Talkable.speechOfMarge3);
        marge.inventory.add(new  Duff());

        Other wiggum = new Other("Wiggum", "yes", "view/img/wiggum.png");
        wiggum.addSpeechs(Talkable.speechOfWiggum1, Talkable.speechOfWiggum2, Talkable.speechOfWiggum3);
        wiggum.inventory.add(new  Gun());
        wiggum.inventory.add(key);

        Other krusty = new Other("Krusty", "yes", "view/img/krusty.png");
        krusty.addSpeechs(Talkable.speechOfKrusty1, Talkable.speechOfKrusty2, Talkable.speechOfKrusty3);
        krusty.inventory.add(hamburger);

        Other bart = new Other("Bart", "yes", "view/img/bart.png");
        bart.addSpeechs(Talkable.speechOfBart1, Talkable.speechOfBart2, Talkable.speechOfBart3);
        bart.inventory.add(skate);

        Other lisa = new Other("Lisa", "yes", "view/img/lisa.gif");
        lisa.addSpeechs(Talkable.speechOfLisa1, Talkable.speechOfLisa2, Talkable.speechOfLisa3);

        Other burns= new Other("Mr Burns", "yes", "view/img/burns.png");
        burns.addSpeechs(Talkable.speechOfBurns1, Talkable.speechOfBurns2, Talkable.speechOfBurns3);

        // creation des ennemies
        Enemy tahiti_Bob = new Enemy("Tahiti Bob", "view/img/bob.png");
        tahiti_Bob.inventory.add(new Knife());
        tahiti_Bob.inventory.add(new Knife());
        tahiti_Bob.inventory.add(new Knife());

        Enemy kang_kodos = new Enemy("Kang & Kodos", "view/img/aliens.png");
        kang_kodos.inventory.add(new Uranium());

        Enemy nelson = new Enemy("Nelson", "view/img/nelson.png");

        Enemy smithers= new Enemy("Smithers", "view/img/smiters.png");
        smithers.inventory.add(new Gun());


        // MAJ des Rooms
        homerDesk.addObject(new Duff());

        hall.addCharacter(bart);
        hall.addObject(duff1);

        control.addCharacter(wiggum);
        control.addObject(lamp);

        b24.addEnemy(tahiti_Bob, b_k);

        rest.addCharacter(marge);

        moes.addCharacter(moe);

        storage.addObject(parchment);

        kitchen.addCharacter(krusty);

        operations.addEnemy(kang_kodos, m_o);

        employee.addEnemy(nelson, e_o);

        auditorium.addCharacter(lisa);

        engine.addObject(duff2);

        production.addEnemy(smithers, p_b);
        production.addObject(duff3);

        burnsDesk.addCharacter(burns);

        safety.addObject(donuts);


        // Homer est dans la premiere salle (son bureau)
        this.Rooms.get(0).addCharacter(homer);

        homer.beAttacked(-3);
    }
}
