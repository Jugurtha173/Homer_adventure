/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import projet.SwitchStage;

/**
 *
 * @author JUGURTHA
 */
public class homeController implements Initializable {
    
    @FXML
    Button startBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         startBtn.setOnAction( event -> {
            //SwitchStage.switchTo("game", startBtn);
            context();
        });
    }
    
    public void context(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Homer's Adventure");
        alert.setHeaderText("Welcome to Homer's Adventure");
        alert.setContentText(
                "A game created by:\n"
                + "Hylia BOUDAHBA\n"
                + "Ramus ASSOGBA\n"
                + "Jugurtha ASMA\n"
                + "April 2021 version\n\n\n"
                + "Homer is in his office ... 'Working' \n"
                + "Suddenly ... he's hungry!\n"
                + "But Mr Burns confiscated his DONUT\n"
                + "DOH !!!\n"
                + "Homer is really hungry he will have to collect his donut in the boss's office\n"
        );
        
        ButtonType buttonTypeGo = new ButtonType("LET'S GO", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeGo);
        Optional<ButtonType> result = alert.showAndWait();
        
        try{
            if (result.get() == buttonTypeGo)
                SwitchStage.switchTo("game", startBtn);
        } catch(NoSuchElementException e){}
            
        
    }
    
}
