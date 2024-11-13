package sk.ukf.moorhuhn;

import javafx.scene.layout.HBox;

public class BulletHolder extends HBox {
    static int n;

    public BulletHolder(int gap){
        super(gap);
        addBullets();
    }

    public void addBullets(){
        this.getChildren().clear();
        n = 0;
        for(int i = 0; i <7; i++){
            Bullet b = new Bullet(this);
            n++;
        }

    }

    public void shoot(){
        if(n > 0){
            this.getChildren().removeLast();
            n--;
        }

    }
}
