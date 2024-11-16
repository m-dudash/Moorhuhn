package sk.ukf.moorhuhn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

public class BulletHolder extends HBox {
    static int n;
    MediaView mv;
    public BulletHolder(int gap, MediaView mv){
        super(gap);
        this.mv = mv;
        addBullets();
    }

    public void addBullets(){
        n = this.getChildren().size();
        if(n <7){
            pl("reload");

            Timeline r = new Timeline(new KeyFrame(Duration.millis(50), evt -> {
                Bullet b = new Bullet(this);
                n++;
            }));
            r.setCycleCount(7 - n);
            r.play();
        }

    }

    public void shoot(){
        if(n > 0){
            this.getChildren().removeLast();
            pl("shoot");
            n--;
        }
        else{
            pl("empty");
        }

    }

    private void pl(String f){
        String path = "src/main/resources/sounds/"+f+".mp3";
        Media shootSound = new Media(new File(path).toURI().toString());
        MediaPlayer mp = new MediaPlayer(shootSound);
        mv.setMediaPlayer(mp);
        mp.play();
    }
}
