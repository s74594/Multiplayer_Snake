package com.example.multiplayer_snake.model;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class Player {

    Text points;
    Text name;
    public double snakeX;
    public double snakeY;
    public double fruitX;
    public double fruitY;
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

    /* Method to generate food at a random position(x,y) */
    public void generateFood() {

        /* place food within defined borders */
        int minWidth = 0;
        int maxWidth = 580;
        int minHeight = 25;
        int maxHeight = 380;

        /* generates a random value */
        int posX = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
        int posY = (int) (Math.random() * (maxHeight - minHeight) + minHeight);

        /* set fruit */
        fruitX = posX;
        fruitY = posY;
        System.out.println("X: " + posX + " Y: " + posY); // Debug
    }

}