/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.DoorImage;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import model.GameModel;
import javafx.scene.layout.GridPane;
import model.characters.Attackable;
import model.characters.Enemy;
import model.characters.Hero;
import model.characters.MyCharacter;
import model.characters.Other;
import model.environement.Door;
import model.myObjects.MyObject;
import view.GameView;
/**
 *
 * @author JUGURTHA
 */
public class GameController implements Initializable {
    
    public GameModel model;
    GameView view;
    Hero myHero;
    final int gridSize = 4;
    
    public GameController(GameView view){
        this.model = new GameModel(this);
        this.view = view;
        this.view.setController(this);
        this.myHero = model.getHomer();
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.view.getRoot().addEventHandler(KeyEvent.KEY_PRESSED, (event) -> moveHomer(event));
        syncRoom();   
    }
    
    @FXML
    public void moveHomer(KeyEvent e ) {
        this.view.getGridPane().getChildren().remove(this.view.getHomer());

        if ( null != e.getCode())
            switch (e.getCode()) {
                case Z: model.moveHomer(0, -1); break;
                case S: model.moveHomer(0, +1); break;
                case Q: model.moveHomer(-1, 0); break;
                case D: model.moveHomer(+1, 0); break;
                default: break;
            }
        
        this.view.getGridPane().add(this.view.getHomer(), model.getX(), model.getY());
        for (Node node : this.view.getGridPane().getChildren()) {
            if(GridPane.getRowIndex(node) == myHero.getY()
                    && GridPane.getColumnIndex(node) == myHero.getX()
                    && node instanceof DoorImage) {
                this.go((DoorImage)node);
                break;
            }
        }
    }
    
    @FXML
    public void help(){
        this.view.getTabPane().getSelectionModel().select(this.view.getMessageTab());
        this.showMessage(this.model.help(), "F4D03F ");
    }
    
    @FXML
    public void look(){
        this.view.getTabPane().getSelectionModel().select(this.view.getMessageTab());
        this.showMessage(this.model.look(), "#AEB6BF");
    }
   
    public void addInventory(ImageView img){
        this.view.getFlowInventory().getChildren().add(img);
    }
    
    public void syncRoom(){
        
        ObservableList<Node> children = this.view.getGridPane().getChildren();
        
        children.clear();
        syncDoors();
        
        if(!(myHero.getCurrentRoom().isLigth)){
            this.view.setBackGround("view/img/darkRoom.png");
        } else {
            this.view.setBackGround(myHero.getCurrentRoom().getBgUrl());
            syncObjects();
            syncCharacters();     
        }
        this.view.getGridPane().add(this.view.getHomer(), model.getX(), model.getY());
        // centrer chaque node du gridPane
        for (Node node : children)
            GridPane.setHalignment(node, HPos.CENTER);
    }
    
    public void syncObjects(){
        List<MyObject> objects = myHero.getCurrentRoom().getObjects();
        for (MyObject object : objects) {
            ImageView img = object.getImg();
            Tooltip tooltip = new Tooltip();
            Tooltip.install(img, tooltip);
            
            img.setOnMouseEntered(e -> {
                img.scaleXProperty().setValue(1.7);
                img.scaleYProperty().setValue(1.7);
                tooltip.setText(object.descriptif());
                
            });
            
            img.setOnMouseExited(e -> {
                img.scaleXProperty().setValue(1);
                img.scaleYProperty().setValue(1);
            });
            
            img.setOnMouseClicked(e -> {
                // mettre l'objet dans l'inventaire
                this.myHero.take(object.toString());
                this.view.getGridPane().getChildren().remove(img);
                this.addInventory(img);
                
                // utiliser l'objet dans l'inventaire
                img.setOnMouseClicked(event -> {
                    myHero.use(object.toString());
                    this.syncRoom();
                });
            });
            
            img.setCursor(Cursor.HAND);
            this.view.getGridPane().add(img, object.getX(), object.getY());
        }
    }
    
    public void syncCharacters(){
        List<MyCharacter> characters = myHero.getCurrentRoom().getCharacters();
        
        for (MyCharacter ch : characters) {
            if(!(ch instanceof Hero)){
                ImageView img = ch.getImg();
                img.setCursor(Cursor.HAND);
                if(ch instanceof Enemy){
                    img.setOnMouseClicked(e ->{
                        if(ch.isAlive())
                            myHero.attack((Attackable) ch);
                        else
                            show("The dead body of " + ch.getName());
                    });
                } else {
                    img.setOnMouseClicked(e ->{
                        myHero.talk();
                    });
                }
                this.view.getGridPane().add(img, ch.getX(), ch.getY());
            }
        }
    }
    
    public void syncDoors(){
        List<Door> doors = myHero.getCurrentRoom().getDoors();
        
        for (Door door : doors) {
            DoorImage img = new DoorImage(door, myHero);
            
            if(door.room[1].equals(myHero.getCurrentRoom()))
                this.view.getGridPane().add(img, gridSize - door.getX(), gridSize - door.getY());
            else
                this.view.getGridPane().add(img, door.getX(), door.getY());
              
        }
    }
    
    public void show(String text){
        this.view.show(text);
    }
    
    public void showMessage(String text, String color){
        
        this.view.showMessage(text, color);
    }
    
    public void showMessage(String text){
        this.showMessage(text, "#5DADE2"); // default color
    }
    
    public void talk(Other other){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(other.getSpeechs().get(0));

        ButtonType buttonTypeYes = new ButtonType(other.getCondition());
        ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeBye = new ButtonType("Bye", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
            other.dropAllInventory();
            alert.getButtonTypes().removeAll(buttonTypeYes, buttonTypeNo);
            alert.getButtonTypes().setAll(buttonTypeBye);
            alert.setContentText(other.getSpeechs().get(1));
            alert.showAndWait();
        } else {
            alert.getButtonTypes().removeAll(buttonTypeYes, buttonTypeNo);
            alert.getButtonTypes().setAll(buttonTypeBye);
            alert.setContentText(other.getSpeechs().get(2));
            alert.showAndWait();
        }
        this.syncRoom();
    }
    
    public SimpleDoubleProperty getHpProperty(){
        return model.getHpProperty();
    }

    // changer de salle avec transition
    private void go(DoorImage doorImage) {
                
        if(myHero.go(""+doorImage.getDoor().getOtherRoom(myHero))){
            int xBefore = myHero.getX();
            int yBefore = myHero.getY();
            int xAfter = (gridSize - myHero.getX());
            int yAfter = (gridSize - myHero.getY());
            
            myHero.moveX(xAfter);
            myHero.moveY(yAfter);
            
            this.view.goTransition(xBefore, yBefore, xAfter, yAfter);
            this.show(""+ myHero.getCurrentRoom());
        }
    }

}