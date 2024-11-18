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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Group {
    // Moorhuhn by Mykhailo M. Dudash
    public List<Enemy> enemies = new ArrayList<>();
    Random rnd = new Random();
    Group enems;
    BulletHolder bullets;
    MediaView mv;
    MediaView mvb;
    Game game;
    Timeline tl;


    public Game() {
        this.game = this;

        //game layers
        Group boards = new Group();
        enems = new Group();
        getChildren().addAll(enems,boards);

        //game media viewers
        mv = new MediaView();
        mvb = new MediaView();


        //custom font
        Font font = Font.loadFont(getClass().getResourceAsStream("/pixelFont.ttf"), 16);


        backgroundMusic("ghostTheme");


        //Scoreboard
        Label board = new Label();
        board.setFont(Font.font("Pixelify Sans", 40));
        ScoreBoard scoreBoard = new ScoreBoard(board);
        board.setLayoutY(30);
        board.setLayoutX(30);



        //Timer
        Timer t = new Timer(this);
        t.setLayoutX(990);
        t.setLayoutY(30);
        boards.getChildren().addAll(board, t);



        //Bullet holder
        bullets = new BulletHolder(0, mv);
        bullets.setLayoutX(620);
        bullets.setLayoutY(490);
        boards.getChildren().add(bullets);


        //General timeline
        tl = new Timeline(new KeyFrame(Duration.millis(40), evt -> tick()));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();


    }



    //key handler
    public void kHandler(KeyEvent ky){
        KeyCode c = ky.getCode();
        if(c == KeyCode.SPACE){
            bullets.addBullets();
        }
    }


    //Timeline handler
    public void tick(){
        moveAll();
        //New enemy appears with a probability of 1/30 every tick of the timeline.
        int n = rnd.nextInt(30);
        if(n == 5 && enemies.size() < 8){
            addEnemy();
            System.out.println("ghosts count: "+enemies.size());
        }
    }


    public void addEnemy(){
        Enemy e = new Enemy(enems, enemies, defineKind(), mv);
    }


    //method for Game Over screen
    public  void gameOver(){
        saveScore(ScoreBoard.score);
        game.getChildren().clear();
        enemies.clear();
        tl.stop();

        ImageView background = new ImageView(new Image("Background.png"));
        background.setPreserveRatio(true);
        background.setFitHeight(game.getScene().getHeight());
        game.getChildren().addFirst(background);

        Label lb = new Label("Game Over");
        lb.setFont(Font.font("Pixelify Sans", 60));
        Label rs = new Label("Result: " + ScoreBoard.score);
        rs.setFont(Font.font("Pixelify Sans", 40));
        Label md = new Label("by M. M. Dudash");
        md.setFont(Font.font("Pixelify Sans", 20));
        Label bs = new Label("Best result: "+ maxResult());
        bs.setFont(Font.font("Pixelify Sans", 20));
        rs.setTextFill(Color.WHITE);
        lb.setTextFill(Color.WHITE);
        md.setTextFill(Color.WHITE);
        bs.setTextFill(Color.WHITE);
        game.getChildren().addAll(lb, rs, md, bs);

        lb.setLayoutX(400);
        lb.setLayoutY(300);

        rs.setLayoutY(400);
        rs.setLayoutX((900 - rs.prefWidth(-1)) / 2);

        bs.setLayoutY(460);
        bs.setLayoutX((930 - rs.prefWidth(-1)) / 2);

        md.setLayoutX(490);
        md.setLayoutY(540);

        //disabling mouse actions after the end of the game
        getScene().setOnMouseClicked(evt -> {});



    }


    private void saveScore(int n){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true))) {
            writer.write(String.valueOf(n));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("CHYBA "+ e);
        }
    }

    private int maxResult() {
        int max = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int score = Integer.parseInt(line);
                    if (score > max) {
                        max = score;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Сhyba v riadku" + line);
                }
            }
        } catch (IOException e) {
            System.out.println("CHYBA " + e);
        }
        return max;
    }


    //defining kind of the ghost
    public String defineKind(){
        int n = rnd.nextInt(2);
        if(n == 0) return "w";
        return "d";
    }

    private void backgroundMusic(String f){
        String path = "src/main/resources/sounds/"+f+".mp3";
        Media fon = new Media(new File(path).toURI().toString());
        MediaPlayer mpf = new MediaPlayer(fon);
        mpf.setVolume(0.6);
        mvb.setMediaPlayer(mpf);
        mpf.play();
    }


    //method for flying all enemies
    public void moveAll() {
        for (Enemy e : new ArrayList<>(enemies)) { //Copy of the list to avoid deletion errors
            e.move();
            if (e.death) {
                e.del(enemies);
            }
        }
    }


    //Method for increasing the range of the cursor
    public void handleMouseClick(MouseEvent event) {

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

}