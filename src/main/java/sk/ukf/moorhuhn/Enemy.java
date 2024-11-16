package sk.ukf.moorhuhn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Enemy extends ImageView {
    Random rnd = new Random();
    int d;
    int dy;
    String kind;
    int frame;

    List lst;
    Group grp;
    int speed;
    int sizeE;
    int hitScore;
    MediaView mv;
    boolean death;
    public Enemy(Group grp, List lst, String k, MediaView mv){
        super(new Image("file:src/main/resources/sprites/"+k+"Ghost0.png"));
        this.frame = 0;
        this.death = false;
        this.mv = mv;

        this.d = rnd.nextInt(2); //flight direction:  0 - left  1 - right
        this.kind = k;

        //staging
        if(d == 0) {
            this.setX(-120);
            setScaleX(-1);
        }
        else {
            this.setX(grp.getScene().getWidth() +120);

        }
        this.setY(rnd.nextDouble(100, grp.getScene().getHeight() - 50));

        if(getY() > grp.getScene().getHeight()/2) this.dy = -1;
        else this.dy = 1;

        grp.getChildren().add(this);


        int[] sizes = {50,80,100,120};
        int[] hitScors = {12,10,8,5};

        //Defining enemy size and hit points
        int n = rnd.nextInt(4);
        this.hitScore = hitScors[n];
        this.sizeE = sizes[n];
        this.setFitHeight(sizes[n]);
        this.setFitWidth(sizes[n]);


        this.grp = grp;
        this.lst = lst;
        lst.add(this);


        //Defining enemy speed
        this.speed = rnd.nextInt(7,12);

    }


    public void hit() {
        if (d != 3 && BulletHolder.n > 0) {
            int s = rnd.nextInt(3);
            pl("ghost_death_"+s);
            this.d = 3;
            System.out.println("HIT - sound s = "+s);
            ScoreBoard.up(hitScore);
            ShootScore sh = new ShootScore(this);  //creating a score over a shot enemy
        }
    }

    private void pl(String f){
        String path = "src/main/resources/sounds/"+f+".mp3";
        Media shootSound = new Media(new File(path).toURI().toString());
        MediaPlayer mp = new MediaPlayer(shootSound);
        mv.setMediaPlayer(mp);
        mp.play();
    }


    public void del(List<Enemy> enemies) {
        grp.getChildren().remove(this);  //deleting from the scene
        enemies.remove(this); // deleting from the list
    }



    public void move(){
        if(this.d != 3){
            frame = (frame+1)%4;
            super.setImage(new Image("file:src/main/resources/sprites/"+kind+"Ghost"+frame+".png"));
        }
        else{
            frame = (frame + 1) % 20;
            super.setImage(new Image("file:src/main/resources/sprites/"+kind+"Die/tile"+frame+".png"));
            if(frame == 19) this.death = true;
        }



        if(d == 0){ //LEFT direction
            this.setX(this.getX() - speed);
            if(this.getX() < -700){
                this.setX(grp.getScene().getWidth());
            }

        }
        else if(d == 1){ //RIGHT direction
            this.setX(this.getX()+speed);
            if(this.getX() > grp.getScene().getWidth()){
                this.setX(-700);
            }

        }



        int p = rnd.nextInt(5);
        if(p == 3) {
            this.setY((getY()) + dy * 8);
        }

        if(this.getY() > grp.getScene().getHeight()-80 || this.getY() < 60){
            dy = -dy;
        }


    }


}
