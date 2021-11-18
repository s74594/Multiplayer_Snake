package com.example.multiplayer_snake.model;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Player {

    Text points;
    Text name;
    public double snakeX;
    public double snakeY;
    final double snakeSpeed = 3; // speed adjust


    public void movePlayer(double x, double y, KeyCode k) {
		this.snakeX = x;
		this.snakeY = y;

        System.out.println("Key: " + k + "  SnakeX: " + snakeX + "  SnakeY: " + snakeY); // Debug
        switch (k) {
            case UP -> {
                // Detect food collision
                //checkCollision();
				snakeY = (snakeY - snakeSpeed);
                //return
            }
            case DOWN -> {
                // Detect food collision
                //checkCollision();
				snakeY = (snakeY + snakeSpeed);
                //return snake.setLayoutY(snakeY + snakeSpeed);
            }
            case LEFT -> {
                // Detect food collision
                //checkCollision();
				snakeX = (snakeX - snakeSpeed);
                //return snake.setLayoutX(snakeX - snakeSpeed);
            }
            case RIGHT -> {
                // Detect food collision
                //checkCollision();
				snakeX = (snakeX + snakeSpeed);
                //return snake.setLayoutX(snakeX + snakeSpeed);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + k);
        }
    }

}