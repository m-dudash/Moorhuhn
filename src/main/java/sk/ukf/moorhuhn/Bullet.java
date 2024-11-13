package sk.ukf.moorhuhn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Bullet extends ImageView {

    BulletHolder hb;
   ImageView iv;

    public Bullet(BulletHolder hb){
        this.hb = hb;
        Image img = new Image("bullet.png", true);
        iv = new ImageView(img);
        iv.setFitWidth(80);
        iv.setFitHeight(200);
        hb.getChildren().add(iv);
    }

}
