package sk.ukf.moorhuhn;

import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Game game = new Game();


        Scene scene = new Scene(game, 1070, 600);


        ImageView background = new ImageView(new Image("Background.png"));
        background.setPreserveRatio(true);
        background.setFitHeight(scene.getHeight());
        game.getChildren().addFirst(background);


        //Custom cursor
        Image cursorImg = new Image("file:src/main/resources/cursor.png");
        ImageCursor cursor = new ImageCursor(cursorImg);
        scene.setCursor(cursor);


        //mouse handler
        scene.setOnMouseClicked(event -> game.handleMouseClick(event));

        //key handler
        scene.setOnKeyPressed(evt -> game.kHandler(evt));

        stage.setTitle("Moorhuhn - by M.Dudash");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
