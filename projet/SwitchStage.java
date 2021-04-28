package projet;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchStage extends Stage{
    
    public SwitchStage(String path) {
        super();
        
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            this.setScene(new Scene(root));
            this.setTitle("Homer's adventure");
            this.setResizable(false);
        }
       catch (IOException e){
           System.out.println("ERREUR, SwitchStage");
           e.printStackTrace();
       }
    }
    
    public static void switchTo(String path, Node target) {
        ((Stage) target.getScene().getWindow()).close();
            SwitchStage myStage = new SwitchStage("view/" + path + ".fxml");
            myStage.show();
    }
}