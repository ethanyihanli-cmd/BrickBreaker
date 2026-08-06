package com.macondo.brickbreaker.view;

import com.macondo.brickbreaker.model.GameModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameView {
    private Canvas canvas;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(GameModel model) {
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.rgb(20,20,50));
        g.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        g.setFill(Color.WHITE);
        g.fillRect(
                model.getPaddleX(),
                model.getPaddleY(),
                model.getPaddleWidth(),
                model.getPaddleHeight()
        );

        g.setFill(Color.RED);
        g.fillOval(
                model.getBallX() - model.getBallRadius(),
                model.getBallY() - model.getBallRadius(),
                model.getBallRadius() * 2,
                model.getBallRadius() * 2
        );

        g.setFill(Color.WHITE);
        g.fillText("Score: " + model.getScore(), 20, 30);
        g.fillText("Lives: " + model.getLives(), 20, 55);

        g.setFill(Color.rgb(150, 150, 150));
        g.fillText("Move: Mouse or arrow keys", canvas.getWidth() - 200, 30);
    }

    public double getCanvasWidth() {
        return canvas.getWidth();
    }
    public double getCanvasHeight() {
        return canvas.getHeight();
    }
}
