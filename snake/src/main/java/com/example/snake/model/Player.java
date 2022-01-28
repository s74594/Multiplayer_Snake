package com.example.snake.model;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.LinkedList;

/**
 * holds player information and controls them
 */
public class Player {

	public Text points;
	public Text name;
	public double snakeX;
	public double snakeY;
	public double snakeXP2;
	public double snakeYP2;
	public double fruitX;
	public double fruitY;
	public Bounds snakeBounds;
	public Bounds snakeBoundsPlayerTwo;
	public Bounds fruitBounds;
	public boolean eatFruit = false;
	public boolean gameOver = false;
	public boolean gameOverPlayerTwo = false;
	final double snakeSpeed = 1.5; // speed adjust
	public int snakeBodySize = 1;
	public int snakeBodySizePlayerTwo = 1;
	public LinkedList<Double> snakeBodyLocationsX = new LinkedList<>();
	public LinkedList<Double> snakeBodyLocationsY = new LinkedList<>();
	public LinkedList<Double> snakeBodyLocationsXP2 = new LinkedList<>();
	public LinkedList<Double> snakeBodyLocationsYP2 = new LinkedList<>();

	/**
	 * player1 snake moving
	 *
	 * @param x coordinate of snake
	 * @param y coordinate of snake
	 * @param direction player moving direction
	 */
	public void movePlayer(double x, double y, KeyCode direction) {
		this.snakeX = x;
		this.snakeY = y;
		eatFruit = false;

		// System.out.println("Key: " + direction + " SnakeX: " + snakeX + " SnakeY: " + snakeY); // Debug

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

	/**
	 * player2 snake moving
	 *
	 * @param xp2 coordinate of snake
	 * @param yp2 coordinate of snake
	 * @param direction player moving direction
	 */
	public void movePlayerTwo(double xp2, double yp2, KeyCode direction) {
		this.snakeXP2 = xp2;
		this.snakeYP2 = yp2;
		eatFruit = false;

		// System.out.println("Key: " + direction + " SnakeX: " + snakeXP2 + " SnakeY: " + snakeYP2); // Debug

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

	/**
	 * snake body coordinates player1
	 */
	private void snakeBodyLocations() {
		snakeBodyLocationsX.addFirst(snakeX);
		snakeBodyLocationsY.addFirst(snakeY);

		if (snakeBodyLocationsX.size() > 998) {
			snakeBodyLocationsX.removeLast();
		}

		if (snakeBodyLocationsY.size() > 998) {
			snakeBodyLocationsY.removeLast();
		}
	}

	/**
	 * snake body coordinates player2
	 */
	private void snakeBodyLocationsPlayerTwo() {
		snakeBodyLocationsXP2.addFirst(snakeXP2);
		snakeBodyLocationsYP2.addFirst(snakeYP2);

		if (snakeBodyLocationsXP2.size() > 998) {
			snakeBodyLocationsXP2.removeLast();
		}

		if (snakeBodyLocationsYP2.size() > 998) {
			snakeBodyLocationsYP2.removeLast();
		}
	}

	public void checkCollision() {
		// Head touches a fruit
		if (fruitBounds.intersects(snakeBounds)) {
			eatFruit = true;

			if (snakeBodySize < 1000) {
				// Wachstum der Snake Player One
				snakeBodySize += 5;
			}
		} else if (fruitBounds.intersects(snakeBoundsPlayerTwo)) {
			eatFruit = true;

			if (snakeBodySizePlayerTwo < 1000) {
				// Wachstum der Snake Player Two
				snakeBodySizePlayerTwo += 5;
			}
		}
		// Head touches a border
		// Kollisionserkennung
		// 1 = Temporär ausgeschaltet
		// 0 = Reaktiviert
		int collisionDetectionStatus = 0; // Debug

		if (collisionDetectionStatus == 0) {
			// Collision detection for player one
			if (snakeX <= 15) {
			// System.out.println("Left border touched: Game Over!"); // Collision with left border
				gameOver();
			} else if (snakeX >= 1520) {
			// System.out.println("Right border touched: Game Over!"); // Collision with right border
				gameOver();
			} else if (snakeY <= 40) {
			// System.out.println("Top border touched: Game Over!"); // Collision with top border
				gameOver();
			} else if (snakeY > 770) {
			// System.out.println("Bottom border touched: Game Over!"); // Collision with bottom border
				gameOver();
			}

			// Collistion detection for player two
			if (snakeXP2 <= 15) {
			// System.out.println("Left border touched: Game Over!"); // Collision with left border
				gameOverPlayerTwo();
			} else if (snakeXP2 >= 1520) {
			// System.out.println("Right border touched: Game Over!"); // Collision with right border
				gameOverPlayerTwo();
			} else if (snakeYP2 <= 40) {
			// System.out.println("Top border touched: Game Over!"); // Collision with top border
				gameOverPlayerTwo();
			} else if (snakeYP2 > 770) {
			// System.out.println("Bottom border touched: Game Over!"); // Collision with bottom border
				gameOverPlayerTwo();
			}
		} else if (collisionDetectionStatus == 1) {
			return;
		}
	}

	/**
	 * sets gameover player1
	 */
	@FXML
	private void gameOver() {
		gameOver = true;
	}

	/**
	 * sets gameover player2
	 */
	private void gameOverPlayerTwo() {
		gameOverPlayerTwo = true;
	}


	/**
	 *  generates food at a random positions
	 */
	public void generateFood() {
		/* place food within defined borders */
		int minWidth = 200;
		int maxWidth = 1500;
		int minHeight = 25;
		int maxHeight = 760;

		/* generates a random value */
		int posX = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
		int posY = (int) (Math.random() * (maxHeight - minHeight) + minHeight);

		/* set fruit */
		fruitX = posX;
		fruitY = posY;
		// System.out.println("Fruit location(x): " + fruitX + " Fruit location(y): " + fruitY); // Debug
	}
}