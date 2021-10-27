module com.example.multiplayer_snake {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens com.example.multiplayer_snake to javafx.fxml;

	exports com.example.multiplayer_snake;
}