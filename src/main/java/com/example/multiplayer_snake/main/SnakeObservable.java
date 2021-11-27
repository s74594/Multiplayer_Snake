package com.example.multiplayer_snake.main;

public interface SnakeObservable {
    void registerObserver(SnakeObserver snakeObserver);

    void deleteObserver(SnakeObserver snakeObserver);

    void notifyObserver();
}
