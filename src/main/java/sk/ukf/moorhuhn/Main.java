package sk.ukf.moorhuhn;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    // Moorhuhn by Mykhailo M. Dudash
    public List<Enemy> enemies = new ArrayList<>();
    Random rnd = new Random();
    Group root;
    BulletHolder bullets;

    @Override
    public void start(Stage stage) {
        //project layers
        root = new Group();
        Group boards = new Group();
        Group enems = new Group();
        root.getChildren().addAll(enems,boards);

        Scene scene = new Scene(root, 1500, 770);

        //Scoreboard
        Label board = new Label();
        board.setFont(new Font("Arial", 40));
        ScoreBoard scoreBoard = new ScoreBoard(board);
        board.setLayoutY(30);
        board.setLayoutX(30);


        //Timer
        Timer t = new Timer();
        t.setLayoutX(scene.getWidth() - 80);
        t.setLayoutY(30);
        boards.getChildren().addAll(board, t);


        Enemy en = new Enemy(root, enemies);
        scene.setOnMouseClicked(this::handleMouseClick);


        //Custom cursor
        Image cursorImg = new Image("file:src/main/resources/cursor.png");
        ImageCursor cursor = new ImageCursor(cursorImg);
        scene.setCursor(cursor);


        //Bullet holder
        bullets = new BulletHolder(0);
        bullets.setLayoutX(scene.getWidth() - 600);
        bullets.setLayoutY(scene.getHeight() - 200);
        boards.getChildren().add(bullets);


        //General timeline
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(40), evt -> tick()));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();



        scene.setOnKeyPressed(evt -> kHandler(evt));
        stage.setTitle("Moorhuhn - by M.Dudash");
        stage.setScene(scene);
        stage.show();
    }



    public void kHandler(KeyEvent ky){
        KeyCode c = ky.getCode();
        if(c == KeyCode.SPACE){
            bullets.addBullets();
        }
    }


    //Timeline handler
    public void tick(){
        moveAll();
        //New enemy appears with a probability of 1/40 every tick of the timeline.
        int n = rnd.nextInt(40);
        if(n == 5 && enemies.size() < 8){
            addEnemy();
            System.out.println(enemies.size());
        }
    }

    public void addEnemy(){
        Enemy e = new Enemy(root, enemies);
    }


    //a method for flying all enemies
    public void moveAll() {
        for (Enemy e : new ArrayList<>(enemies)) { //Copy of the list to avoid deletion errors
            e.move();
            if (e.getY() > root.getScene().getHeight()) {
                e.del(enemies);
            }
        }
    }


//Method for increasing the range of the cursor
    private void handleMouseClick(MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();

        for (Enemy enemy : enemies) {
            double enemyCenterX = enemy.getX() + enemy.getFitWidth() / 2;
            double enemyCenterY = enemy.getY() + enemy.getFitHeight() / 2;

            double distance = Math.sqrt(Math.pow(clickX - enemyCenterX, 2) + Math.pow(clickY - enemyCenterY, 2));

            if (distance <= 50) {
                enemy.hit();
                break;
            }
            else {
                System.out.println("MIMO");
            }
        }
        bullets.shoot();
    }



    public static void main(String[] args) {
        launch();
    }
}