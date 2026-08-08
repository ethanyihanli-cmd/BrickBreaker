package com.macondo.brickbreaker.controller;

import com.macondo.brickbreaker.input.InputHandler;
import com.macondo.brickbreaker.model.GameModel;
import com.macondo.brickbreaker.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

public class GameController {
    private GameModel model;
    private GameView view;
    private InputHandler input;
    private AnimationTimer timer;
    private boolean usingMouse = true;

    public GameController(GameModel model, GameView view, Scene scene) {
        this.model = model;
        this.view = view;
        this.input = new InputHandler();
        attachInputHandlers(scene);
    }

    private void attachInputHandlers(Scene scene) {
        scene.setOnKeyPressed(e -> {
            input.keyPressed(e);
            usingMouse = false;
        });

        scene.setOnKeyReleased(e -> {
            input.keyReleased(e);
        });

        scene.setOnMouseMoved(e -> {
            input.mouseMoved(e);
            usingMouse = true;
        });

        scene.setOnKeyPressed( e -> {
            input.keyPressed(e);
            usingMouse = false;
            if (e.getCode().toString().equals("R") && (model.isGameOver() || model.isWon())) {
                model.resetGame();
            }
        });

        scene.setOnMouseClicked(e -> {
            if (model.isGameOver() || model.isWon()) {
                model.resetGame();
            }
        });
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
       if (model.isGameOver() || model.isWon()) {
           return;
       }

       if (usingMouse) {
           double mouseX = input.getMouseX();
           double newX = mouseX - model.getPaddleWidth() / 2;
           model.setPaddleX(newX);
       } else {
           if (input.isLeftPressed()) {
               model.movePaddleLeft(deltaTime);
           }
           if (input.isRightPressed()) {
               model.movePaddleRight(deltaTime);
           }
       }

       model.update(deltaTime);
    }
}
