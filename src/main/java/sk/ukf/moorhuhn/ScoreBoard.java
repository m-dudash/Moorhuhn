package sk.ukf.moorhuhn;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ScoreBoard {
    //Class cannot be inherited from the Label due to static implementation errors  ( I tried :( )
    public static int score;
    static Label lb;
    public ScoreBoard(Label lb){
        this.lb = lb;
        score = 0;
        lb.setText("Scores: "+ score);
        lb.setTextFill(Color.WHITE);
    }

    public static void up(int n){
        score+= n;
        lb.setText("Scores: "+score);
    }
}
