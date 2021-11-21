package com.example.multiplayer_snake.model;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Player {

    Text points;
    Text name;
    public double snakeX;
    public double snakeY;
    public double fruitX;
    public double fruitY;
    public Bounds snakeBounds;
    public Bounds fruitBounds;
    public int eatFruit = 0;
    public int gameOver = 0;
    final double snakeSpeed = 3; // speed adjust


    public void movePlayer(double x, double y, KeyCode k) {
        this.snakeX = x;
        this.snakeY = y;

        System.out.println("Key: " + k + "  SnakeX: " + snakeX + "  SnakeY: " + snakeY); // Debug
        switch (k) {
            case UP -> {
                // Detect food collision
                checkCollision();
                snakeY = (snakeY - snakeSpeed);
            }
            case DOWN -> {
                // Detect food collision
                checkCollision();
                snakeY = (snakeY + snakeSpeed);
            }
            case LEFT -> {
                // Detect food collision
                checkCollision();
                snakeX = (snakeX - snakeSpeed);
            }
            case RIGHT -> {
                // Detect food collision
                checkCollision();
                snakeX = (snakeX + snakeSpeed);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + k);
        }
    }

    public void checkCollision() {
        // Head touches a fruit
        if (fruitBounds.intersects(snakeBounds)) {
            System.out.println("Food collision detected"); // Debug
            eatFruit = 1;
        }

        // Head touches any border
        if (snakeX <= 9) {
            System.out.println("Left border touched: Game Over!"); // Collision with left border
            gameOver();
        } else if (snakeX >= 590) {
            System.out.println("Right border touched: Game Over!"); // Collision with right border
            gameOver();
        } else if (snakeY <= 35) {
            System.out.println("Top border touched: Game Over!"); // Collision with top border
            gameOver();
        } else if (snakeY > 395) {
            System.out.println("Bottom border touched: Game Over!");// Collision with bottom border
            gameOver();
        }
    }

    @FXML
    private void gameOver() {
        gameOver = 1;
        System.out.println("Gameover");
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