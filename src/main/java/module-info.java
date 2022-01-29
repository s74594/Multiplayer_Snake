module com.example.multiplayer_snake {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.sql;
	requires javafx.graphics;
	requires org.json;
	requires java.logging;
//	requires gson;
	requires com.google.gson;

	opens com.example.multiplayer_snake to javafx.fxml;
	opens controller to javafx.fxml;

	exports com.example.multiplayer_snake;
	exports com.example.multiplayer_snake.model;
	exports controller;
}