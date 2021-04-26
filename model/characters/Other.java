package model.characters;

import model.myObjects.MyObject;
import model.myObjects.Parchment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.scene.control.TextInputDialog;
import model.GameModel;


public class Other extends MyCharacter implements Talkable{

    public final List<String> speechs = new ArrayList<>();

    public String getCondition() {
        return condition;
    }
	private String condition = "yes";
	Scanner interact = new Scanner(System.in);
	
	public Other(String name, String condition) {
		super(name);
		this.condition = condition;
		
	}
	public Other(String name, String condition, String url) {
		super(name, url);
		this.condition = condition;
		
	}
	
	public Other(String name) {
		super(name);
		
	}

	
	public void addSpeechs(String speech1, String speech2, String speech3) {
		this.speechs.add(speech1);
		this.speechs.add(speech2);
		this.speechs.add(speech3);
	}
        
        public List<String> getSpeechs() {
            return speechs;
        }


	@Override
	public void talkTo(Talkable t) {
            
            if (this.getName().equalsIgnoreCase("Lisa")) 
                    this.talkToLisa(t);
            else
            if(this.getName().equalsIgnoreCase("Mr Burns"))
                    this.talkToBurns(t);

            else {
                GameModel.talk(this);
	
            }
        }
	
	public void talkToLisa(Talkable t) {
            GameModel.showMessage(this.speechs.get(0));
		List<MyObject> inv = ((MyCharacter)t).inventory;
		for(MyObject obj : inv) {
			if(obj instanceof Parchment) {
				((Parchment)obj).decrypt();
				GameModel.showMessage(this.speechs.get(1));
				GameModel.showMessage(obj.descriptif());
				return;
			}
		}
		GameModel.showMessage(this.speechs.get(2));

	}
	
	public void talkToBurns(Talkable t) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Talk with Burns");
            dialog.setContentText(this.speechs.get(0));
            
            Optional<String> result = dialog.showAndWait();
            Float note = 0F;
            try {
                note = Float.parseFloat(result.get());  
            } catch (Exception e) {}
            
            if(note >= 17) 
                dialog.setContentText(this.speechs.get(1));
            else 
                dialog.setContentText(this.speechs.get(2));
            
            dialog.showAndWait();
		
	}
    
}
