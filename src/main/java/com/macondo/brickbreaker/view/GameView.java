package com.macondo.brickbreaker.view;

import com.macondo.brickbreaker.model.Brick;
import com.macondo.brickbreaker.model.GameModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameView {
    private Canvas canvas;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(GameModel model) {
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.rgb(20,20,50));
        g.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (Brick brick : model.getBricks()){
            if (brick.isDestroyed()) continue;

            double x = brick.getX();
            double y = brick.getY();
            double w = brick.getWidth();
            double h = brick.getHeight();

            g.setFill(brick.getColor());
            g.fillRect(x, y, w, h);

            g.setStroke(Color.rgb(255,255,255,0.3));
            g.setLineWidth(1);
            g.strokeRect(x, y, w, h);

            if (brick.getMaxHitPoints() > 1) {
                g.setFill(Color.WHITE);
                g.setFont(Font.font("Arial", 10));
                g.fillText(String.valueOf(brick.getHitPoints()), x + w/2 - 4, y + h/2 + 4);
            }
        }

        g.setFill(Color.rgb(80, 80, 200));
        g.fillRoundRect(
                model.getPaddleX(),
                model.getPaddleY(),
                model.getPaddleWidth(),
                model.getPaddleHeight(),
                5, 5
        );
        g.setFill(Color.rgb(120, 120, 255));
        g.fillRect(
                model.getPaddleX() + 5,
                model.getPaddleY() + 3,
                model.getPaddleWidth() - 10,
                model.getPaddleHeight() - 6
        );

        //ball
        double bx = model.getBallX();
        double by = model.getBallY();
        double r = model.getBallRadius();

        g.setFill(Color.rgb(255, 100, 100));
        g.fillOval(bx - r, by - r, r * 2, r * 2);
        g.setFill(Color.rgb(255, 200, 200));
        g.fillOval(bx - r/2, by - r/2, r, r);

        g.setFill(Color.WHITE);
        g.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        g.fillText("Score: " + model.getScore(), 20, 35);
        g.fillText("Lifes: " + getHearts(model.getLives()), 20, 65);

        g.setFill(Color.rgb(150, 150, 150));
        g.setFont(Font.font("Arial", 14));
        g.fillText("Move: mouse or arrow keys", canvas.getWidth() - 200, 30);

        if (model.isGameOver()) {
            g.setFill(Color.rgb(0, 0, 0, 0.7));
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.setFill(Color.RED);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            g.fillText("GAME OVER", canvas.getWidth()/2 - 150, canvas.getHeight()/2 -20);

            g.setFill(Color.WHITE);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            g.fillText("Score: " + model.getScore(), canvas.getWidth()/2 - 60, canvas.getHeight()/2 + 40);

            g.setFill(Color.LIGHTGRAY);
            g.setFont(Font.font("Arial", 18));
            g. fillText("Press 'R' or click anywhere to restart", canvas.getWidth()/2 - 120, canvas.getHeight()/2 + 90);
        }

        if (model.isWon()) {
            g.setFill(Color.rgb(0, 0, 0, 0.7));
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.setFill(Color.GOLD);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            g.fillText("YOU WINNNN！！！", canvas.getWidth()/2 - 130, canvas.getHeight()/2 - 20);

            g.setFill(Color.WHITE);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            g.fillText("Score: " + model.getScore(), canvas.getWidth()/2 - 60, canvas.getHeight()/2 + 40);

            g.setFill(Color.LIGHTGRAY);
            g.setFont(Font.font("Arial", 18));
            g.fillText("Press 'R' or click to play again", canvas.getWidth()/2 - 130, canvas.getHeight()/2 + 90);

            private String getHearts(int lives) {
                return "❤".repeat(Math.max(0, lives));
            }

            public double getCanvasWidth() {
                return canvas.getWidth();
            }

            public double getCanvasHeight() {
                return canvas.getHeight();
            }




        }


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

        if (model.isGameOver()) {
            g.setFill(Color.rgb(0, 0, 0, 0.7));
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.setFill(Color.RED);
            g.setFont(javafx.scene.text.Font.font("Arial", 28));
            g.fillText("GAME OVER", canvas.getWidth() / 2 - 150, canvas.getHeight() / 2);

            g.setFill(Color.WHITE);
            g.setFont(javafx.scene.text.Font.font("Arial", 20));
            g.fillText("Click or press R to restart", canvas.getWidth() / 2 - 110, canvas.getHeight() / 2 + 50);
        }
    }

    public double getCanvasWidth() {
        return canvas.getWidth();
    }
    public double getCanvasHeight() {
        return canvas.getHeight();
    }
}
