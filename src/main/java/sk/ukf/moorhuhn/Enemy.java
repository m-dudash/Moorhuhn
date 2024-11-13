package sk.ukf.moorhuhn;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

public class Enemy extends ImageView {
    Random rnd = new Random();
    int d;

    List lst;
    Group grp;
    int speed;
    int sizeE;
    int hitScore;
    public Enemy(Group grp, List lst){
        super(new Image("duck.png", true));

        this.d = rnd.nextInt(2); //flight direction:  0 - left  1 - right

        //staging
        if(d == 0) this.setX(-120);
        else this.setX(grp.getScene().getWidth() +120);
        this.setY(rnd.nextDouble(100, grp.getScene().getHeight() - 50));
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
            this.d = 3; //direction 3 == falling down
            System.out.println("HIT");
            ScoreBoard.up(hitScore);
            ShootScore sh = new ShootScore(this);  //creating a score over a shot enemy
        }
    }


    public void del(List<Enemy> enemies) {
        grp.getChildren().remove(this);  //deleting from the scene
        enemies.remove(this); // deleting from the list
    }



    public void move(){
        if(d == 0){ //LEFT direction
            this.setX(this.getX() - speed);
            if(this.getX() < -700){
                this.setX(grp.getScene().getWidth());
            }

        }
        else if(d == 1){ //RIGHT direction
            this.setX(this.getX()+speed);
            this.setScaleX(-1);
            if(this.getX() > grp.getScene().getWidth()){
                this.setX(-700);
            }

        }
        else if(d == 3){  //Falling down
            this.setY(this.getY()+20);
        }

    }


}
