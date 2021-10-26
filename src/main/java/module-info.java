module com.example.multiplayer_snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.multiplayer_snake to javafx.fxml;
    exports com.example.multiplayer_snake;
}