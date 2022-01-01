package com.example.multiplayer_snake.model;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class Player {

    @SuppressWarnings("exports")
	public Text points;
    @SuppressWarnings("exports")
	public Text name;
    public double snakeX;
    public double snakeY;
    public double fruitX;
    public double fruitY;
    @SuppressWarnings("exports")
    public Bounds snakeBounds;
    @SuppressWarnings("exports")
    public Bounds fruitBounds;
    public boolean eatFruit = false;
    public boolean gameOver = false;
    final double snakeSpeed = 3; // speed adjust

    @SuppressWarnings("exports")
    public void movePlayer(double x, double y, KeyCode direction) {
        this.snakeX = x;
        this.snakeY = y;

        // System.out.println("Key: " + direction + "  SnakeX: " + snakeX + "  SnakeY: " + snakeY); // Debug
        
        switch (direction) {
            case UP -> {
                // Detect food collision
                checkCollision();
                snakeY = (snakeY - snakeSpeed);
                break;
            }
            case DOWN -> {
                // Detect food collision
                checkCollision();
                snakeY = (snakeY + snakeSpeed);
                break;
            }
            case LEFT -> {
                // Detect food collision
                checkCollision();
                snakeX = (snakeX - snakeSpeed);
                break;
            }
            case RIGHT -> {
                // Detect food collision
                checkCollision();
                snakeX = (snakeX + snakeSpeed);
                break;
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        }
    }

    public void checkCollision() {
        // Head touches a fruit
        if (fruitBounds.intersects(snakeBounds)) {
            System.out.println("Food collision detected"); // Debug
            eatFruit = true;
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
        gameOver = true;
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

        System.out.println("Fruit location(x): " + fruitX + " Fruit location(y): " + fruitY); // Debug
    }
}