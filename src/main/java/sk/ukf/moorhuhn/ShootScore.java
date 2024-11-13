package sk.ukf.moorhuhn;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ShootScore extends Label{
    Group grp;
    Timeline tl;
    int stav;

    public ShootScore(Enemy en){
        super("" + en.hitScore);
        this.setLayoutX(en.getX());
        this.setLayoutY(en.getY());
        this.setFont(new Font("Arial", 20));
        this.grp = en.grp;
        grp.getChildren().add(this);
        animStart();

    }
    private void animStart(){
        tl = new Timeline(new KeyFrame(Duration.millis(50), evt -> move()));
        tl.setCycleCount(20);
        tl.play();
    }

    private void move(){
        stav++;
        System.out.println("STAV: "+stav);
        this.setLayoutY(this.getLayoutY() - 5);
        if(stav == 20){
            grp.getChildren().remove(this); //label disappears after 20 ticks.
        }
    }
}