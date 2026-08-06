package com.macondo.brickbreaker.controller;

import com.macondo.brickbreaker.model.GameModel;
import com.macondo.brickbreaker.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

public class GameController {
    private GameModel model;
    private GameView view;
    private AnimationTimer timer;

    public GameController(GameModel model, GameView view, Scene scene) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                update(deltaTime);
                view.render(model);
                lastUpdate = now;
            }
        };
        timer.start();
    }

    private void update(double deltaTime) {
        double newX = model.getBallX() + model.getBallSpeedX() * deltaTime;
        double newY = model.getBallY() + model.getBallSpeedY() * deltaTime;

        model.setBallX(newX);
        model.setBallY(newY);
    }
}
