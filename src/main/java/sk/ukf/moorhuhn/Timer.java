    package sk.ukf.moorhuhn;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.scene.control.Label;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Font;
    import javafx.util.Duration;

    public class Timer extends Label{
        int n;
        Label lb;
        Timeline tl;
        Game gm;
         public Timer(Game gm){
             super("60");
             this.gm = gm;
             this.n = 60;
             this.setFont(Font.font("Pixelify Sans", 40));
             this.setTextFill(Color.WHITE);
             startTimer();
         }

         private void startTimer(){
             tl = new Timeline(new KeyFrame(Duration.seconds(1), evt -> t()));
             tl.setCycleCount(60);
             tl.play();
         }

         private void t(){
             n--;
             this.setText(""+n);
             if(n <= 0){
                 gm.gameOver(); //end of the game
             }
         }
    }
