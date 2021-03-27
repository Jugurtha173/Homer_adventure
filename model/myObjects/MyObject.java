package model.myObjects;

import javafx.scene.image.ImageView;
import model.characters.MyCharacter;

    public abstract class MyObject {

    protected ImageView img;

    private String name;
    private int healthEffect = 0;
    int x, y;
    String url;
    
    public MyObject(String name, String url, int x, int y) {
        this.img = new ImageView(url);
        this.img.setFitHeight(50);
        this.img.setFitWidth(50);
        this.name = name;
        this.x = x;
        this.y = y;
        this.url = url;
    }
    public MyObject(String name) {
        this.name = name;
    }

    public MyObject(String name, int healthEffect) {
            this(name);

    }
    public MyObject(String name, int healthEffect, String url, int x, int y) {
            this(name, url,  x,  y);
            this.healthEffect = healthEffect;
    }

    public int getHealthEffect() {
            return healthEffect;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public String getUrl() {
        return url;
    }

	@Override
    public String toString() {
    	return this.name;
    }

    public abstract String descriptif();
	    
    public abstract void use(MyCharacter c);
    
    public ImageView getImg(){
        return this.img;
    }

}
