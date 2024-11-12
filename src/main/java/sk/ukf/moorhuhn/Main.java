package sk.ukf.moorhuhn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1500, 770);

        Image cursorImg = new Image("file:src/main/resources/cursor.png");
        ImageCursor cursor = new ImageCursor(cursorImg);
        scene.setCursor(cursor);


        stage.setTitle("Moorhuhn - by M.Dudash");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}