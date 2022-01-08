package com.example.multiplayer_snake.model;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import java.util.LinkedList;

public class Player {

	@SuppressWarnings("exports")
	public Text points;
	@SuppressWarnings("exports")
	public Text name;
	public double snakeX;
	public double snakeY;
	public double snakeXP2; // Player Two
	public double snakeYP2; // Player Two
	public double fruitX;
	public double fruitY;
	@SuppressWarnings("exports")
	public Bounds snakeBounds;
	@SuppressWarnings("exports")
	public Bounds snakeBoundsPlayerTwo; // Player Two
	@SuppressWarnings("exports")
	public Bounds fruitBounds;
	public boolean eatFruit = false;
	public boolean gameOver = false;
	final double snakeSpeed = 1.5; // speed adjust
	public int snakeBodySize = 1;
	public int snakeBodySizePlayerTwo = 1;
	public LinkedList<Double> snakeBodyLocationsX = new LinkedList<>();
	public LinkedList<Double> snakeBodyLocationsY = new LinkedList<>();
	public LinkedList<Double> snakeBodyLocationsXP2 = new LinkedList<>(); // Player Two
	public LinkedList<Double> snakeBodyLocationsYP2 = new LinkedList<>(); // Player Two

	@SuppressWarnings("exports")
	public void movePlayer(double x, double y, KeyCode direction) {
		this.snakeX = x;
		this.snakeY = y;
		eatFruit = false;

//		System.out.println("Key: " + direction + " SnakeX: " + snakeX + " SnakeY: " + snakeY); // Debug

		if (direction != null) {
			switch (direction) {
			/**
			 * Keycontrolls: Arrow Keys: UP, DOWN, LEFT, RIGHT
			 */
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
			default -> System.out.println("Key '" + direction + "' is not defined!");
			}
		}
		snakeBodyLocations();
	}

	/** Player Two **/
	@SuppressWarnings("exports")
	public void movePlayerTwo(double xp2, double yp2, KeyCode direction) {
		this.snakeXP2 = xp2;
		this.snakeYP2 = yp2;
		eatFruit = false;

//		System.out.println("Key: " + direction + " SnakeX: " + snakeX + " SnakeY: " + snakeY); // Debug

		if (direction != null) {
			switch (direction) {
			/**
			 * Keycontrolls: Arrow Keys: W, A, S, D
			 */
			case W -> {
				// Detect food collision
				checkCollision();
				snakeYP2 = (snakeYP2 - snakeSpeed);
				break;
			}
			case S -> {
				// Detect food collision
				checkCollision();
				snakeYP2 = (snakeYP2 + snakeSpeed);
				break;
			}
			case A -> {
				// Detect food collision
				checkCollision();
				snakeXP2 = (snakeXP2 - snakeSpeed);
				break;
			}
			case D -> {
				// Detect food collision
				checkCollision();
				snakeXP2 = (snakeXP2 + snakeSpeed);
				break;
			}
			default -> System.out.println("Key '" + direction + "' is not defined!");
			}
		}
		snakeBodyLocationsPlayerTwo();
	}

	private void snakeBodyLocations() {
		snakeBodyLocationsX.addFirst(snakeX);
		snakeBodyLocationsY.addFirst(snakeY);

		if (snakeBodyLocationsX.size() > 98) {
			snakeBodyLocationsX.removeLast();
		}

		if (snakeBodyLocationsY.size() > 98) {
			snakeBodyLocationsY.removeLast();
		}
	}

	/** Player Two **/
	private void snakeBodyLocationsPlayerTwo() {
		snakeBodyLocationsXP2.addFirst(snakeXP2);
		snakeBodyLocationsYP2.addFirst(snakeYP2);

		if (snakeBodyLocationsXP2.size() > 98) {
			snakeBodyLocationsXP2.removeLast();
		}

		if (snakeBodyLocationsYP2.size() > 98) {
			snakeBodyLocationsYP2.removeLast();
		}
	}

	public void checkCollision() {
		
		// Head touches any fruit
		if (fruitBounds.intersects(snakeBounds)) {
			eatFruit = true;

			if (snakeBodySize < 100) {
				System.out.println("Player One: SnakeSize(" + snakeBodySize + ")");
				// Wachstum der Schlange
				snakeBodySize += 3;
			}
		} else if (fruitBounds.intersects(snakeBoundsPlayerTwo)) {
			eatFruit = true;

			if (snakeBodySizePlayerTwo < 100) {
				System.out.println("Player Two: SnakeSize(" + snakeBodySizePlayerTwo + ")");
				// Wachstum der Schlange
				snakeBodySizePlayerTwo += 3;
			}
		}

		// Head touches any border
		// Kollisionserkennung
		// 1 = Temporär ausgeschaltet
		// 0 = Reaktiviert
		int collisionDetectionOff = 0; // Debug

		if (collisionDetectionOff == 0) {
			// Collistion detection for player one
			if (snakeX <= 15) {
				System.out.println("Left border touched: Game Over!"); // Collision with left border
				gameOver();
			} else if (snakeX >= 1520) {
				System.out.println("Right border touched: Game Over!"); // Collision with right border
				gameOver();
			} else if (snakeY <= 40) {
				System.out.println("Top border touched: Game Over!"); // Collision with top border
				gameOver();
			} else if (snakeY > 770) {
				System.out.println("Bottom border touched: Game Over!"); // Collision with bottom border
				gameOver();
			}
		} else if (collisionDetectionOff == 1){
			return;
		}
	}

	@FXML
	private void gameOver() {
		gameOver = true;
	}

	/* Method to generate food at a random position(x,y) */
	public void generateFood() {

		/* place food within defined borders */
		int minWidth = 200;
		int maxWidth = 1500;
		int minHeight = 25;
		int maxHeight = 786;

		/* generates a random value */
		int posX = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
		int posY = (int) (Math.random() * (maxHeight - minHeight) + minHeight);

		/* set fruit */
		fruitX = posX;
		fruitY = posY;

//		System.out.println("Fruit location(x): " + fruitX + " Fruit location(y): " + fruitY); // Debug
	}
}