module sk.ukf.moorhuhn {
    requires javafx.controls;
    requires javafx.fxml;


    opens sk.ukf.moorhuhn to javafx.fxml;
    exports sk.ukf.moorhuhn;
}