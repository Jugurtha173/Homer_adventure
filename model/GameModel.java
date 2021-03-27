/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import model.characters.Hero;
import model.myObjects.MyObject;
import model.environement.NuclearCentral;

/**
 *
 * @author JUGURTHA
 */
public class GameModel {
    
    NuclearCentral nc = new NuclearCentral();
    Hero homer = new Hero("Homer");
    
    public GameModel(){
        nc.init(homer);     
    }
    
    public NuclearCentral getNuclearCentral(){
        return this.nc;
    }
    
    public Hero getHomer(){
        return this.homer;
    }
    
    public void moveHomer(int x, int y){
        if ( getX() + x < 0 || getX() + x > 4 || getY() + y < 0 || getY() + y > 4)
            return;
        
        this.homer.setX(x);
        this.homer.setY(y);
    }
    
    public int getX(){
            return this.homer.getX();
        }
        
	
    public int getY(){
        return this.homer.getY();
    }
    
    public String help(){
        return this.homer.help();
    }
    
    public String look(){
        return this.homer.look();
    }
    
    public SimpleDoubleProperty hpProperty(){
        return (SimpleDoubleProperty) this.homer.hpProperty;
    }
    
    public List<MyObject> getInventory(){
        return this.homer.inventory;
    }
    
}
