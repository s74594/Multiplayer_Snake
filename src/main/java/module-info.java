module com.example.multiplayer_snake {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;

	opens com.example.multiplayer_snake to javafx.fxml;

	exports com.example.multiplayer_snake;
	exports com.example.multiplayer_snake.model;
	
	exports controller;
	
	opens controller to javafx.fxml;
}