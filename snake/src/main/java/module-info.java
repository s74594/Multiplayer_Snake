module com.example.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires network;
    requires java.sql;


    opens com.example.snake to javafx.fxml;
    exports com.example.snake;
}