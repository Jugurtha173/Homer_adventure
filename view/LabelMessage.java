/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.control.Label;
/**
 *
 * @author JUGURTHA
 */
public class LabelMessage extends Label {
    
    public LabelMessage(String text, String color){
        super(text);
        this.setStyle("-fx-background-color: "+ color + ";-fx-padding: 7");
        this.setWrapText(true);
    }
    
    
}
