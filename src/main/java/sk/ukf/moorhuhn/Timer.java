    package sk.ukf.moorhuhn;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.scene.control.Label;
    import javafx.scene.text.Font;
    import javafx.util.Duration;

    public class Timer extends Label{
        int n;
        Label lb;
        Timeline tl;
         public Timer(){
             super("60");
             this.n = 60;
             this.setFont(new Font("Arial", 40));
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
         }
    }
