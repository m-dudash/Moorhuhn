package sk.ukf.moorhuhn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Bullet extends ImageView {

    BulletHolder hb;


    public Bullet(BulletHolder hb){
        super(new Image("bullet.png", true));
        this.hb = hb;
        this.setPreserveRatio(true);
        this.setFitWidth(60);
        hb.getChildren().add(this);
    }

}
