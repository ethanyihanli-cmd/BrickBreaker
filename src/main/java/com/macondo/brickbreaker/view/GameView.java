package com.macondo.brickbreaker.view;

import com.macondo.brickbreaker.model.Ball;
import com.macondo.brickbreaker.model.Brick;
import com.macondo.brickbreaker.model.GameModel;
import com.macondo.brickbreaker.model.PowerUp;
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

        for (int i = 0; i < canvas.getHeight(); i++) {
            double progress = (double) i / canvas.getHeight();
            int r = (int) (20 + progress * 30);
            int gr = (int) (20 + progress * 20);
            int b = (int) (50 + progress* 60);
            g.setFill(Color.rgb(r, gr, b));
            g.fillRect(0, i, canvas.getWidth(), 1);
        }

        g.setFill(Color.rgb(20, 20, 50));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Brick brick : model.getBricks()) {
            if (brick.isDestroyed()) continue;

            double x = brick.getX();
            double y = brick.getY();
            double w = brick.getWidth();
            double h = brick.getHeight();

            g.setFill(brick.getColor());
            g.fillRect(x, y, w, h);

            g.setFill(Color.rgb(255, 255, 255, 0.2));
            g.fillRect(x + 2, y + 2, w - 4, h / 2 - 2);

            g.setStroke(Color.rgb(255, 255, 255, 0.3));
            g.setLineWidth(1);
            g.strokeRect(x, y, w, h);

            if (brick.getMaxHitPoints() > 1) {
                g.setFill(Color.WHITE);
                g.setFont(Font.font("Arial", 10));
                g.fillText(String.valueOf(brick.getHitPoints()), x + w / 2 - 4, y + h / 2 + 4);
            }
        }

        for (PowerUp p : model.getPowerUps()) {
            if (!p.isActive()) continue;

            double x = p.getX();
            double y = p.getY();
            double w = p.getWidth();
            double h = p.getHeight();

            g.setFill(p.getColor().deriveColor(1, 1, 1, 0.3));
            g.fillOval(x - 6, y - 6, w + 12, h + 12);

            g.setFill(p.getColor());
            g.fillRoundRect(x, y, w, h, 5, 5);

            g.setStroke(Color.rgb(255, 255, 255, 0.3));
            g.setLineWidth(1);
            g.strokeRoundRect(x, y, w, h, 5, 5);

            g.setFill(Color.WHITE);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            g.fillText(p.getSymbol(), x + w / 2 - 6, y + h / 2 + 6);

        }

        for (Ball ball : model.getBalls()) {
            double bx = ball.getX();
            double by = ball.getY();
            double r = ball.getRadius();

            g.setFill(Color.rgb(255, 100, 100,0.15));
            g.fillOval(bx - r - 8, by - r - 8, r * 2 + 16, r * 2 + 16);
            g.setFill(Color.rgb(255,80,80));
            g.fillOval(bx - r, by - r, r * 2, r * 2);
            g.setFill(Color.rgb(255, 200, 200));
            g.fillOval(bx - r * 0.4, by - r / 2, r * 0.8, r * 0.6);
        }

        double px = model.getPaddleX();
        double py = model.getPaddleY();
        double pw = model.getPaddleWidth();
        double ph = model.getPaddleHeight();


        g.setFill(Color.rgb(80, 80, 200, 0.15));
        g.fillRect(px - 6, py - 6, pw + 12, ph + 12);
        g.setFill(Color.rgb(120, 120, 255));
        g.setFill(Color.rgb(60, 60, 180));
        g.fillRoundRect(px, py, pw, ph, 5, 5);
        g.setFill(Color.rgb(120, 120, 255));
        g.fillRect(px + 6, py + 3, pw - 12, ph - 6);
        g.setStroke(Color.rgb(100, 100, 220));
        g.setLineWidth(1);
        g.strokeRoundRect(10, 10, 160, 100, 10, 10);
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        g.fillText("Score: " + model.getScore(), 25, 38);
        g.setFill(Color.RED);
        g.setFont(Font.font("Arial", 18));
        g.fillText("Lives: " + getHearts(model.getLives()), 25, 68);
        g.setFill(Color.CYAN);
        g.setFont(Font.font("Arial", 14));
        g.fillText("Balls: " + model.getBalls().size(), 25, 95);
        g.setFill(Color.rgb(150, 150, 150));
        g.setFont(Font.font("Arial", 14));
        g.fillText("Move: mouse or arrow keys", canvas.getWidth() - 200, 30);

        if (model.isGameOver()) {
            g.setFill(Color.rgb(0, 0, 0, 0.7));
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.setFill(Color.RED);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            g.fillText("GAME OVER", canvas.getWidth() / 2 - 150, canvas.getHeight() / 2 - 20);

            g.setFill(Color.WHITE);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            g.fillText("Score: " + model.getScore(), canvas.getWidth() / 2 - 60, canvas.getHeight() / 2 + 40);

            g.setFill(Color.LIGHTGRAY);
            g.setFont(Font.font("Arial", 18));
            g.fillText("Press 'R' or click anywhere to restart", canvas.getWidth() / 2 - 120, canvas.getHeight() / 2 + 90);
        }

        if (model.isWon()) {
            g.setFill(Color.rgb(0, 0, 0, 0.7));
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.setFill(Color.GOLD);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            g.fillText("YOU WINNNN！！！", canvas.getWidth() / 2 - 130, canvas.getHeight() / 2 - 20);

            g.setFill(Color.WHITE);
            g.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            g.fillText("Score: " + model.getScore(), canvas.getWidth() / 2 - 60, canvas.getHeight() / 2 + 40);

            g.setFill(Color.LIGHTGRAY);
            g.setFont(Font.font("Arial", 18));
            g.fillText("Press 'R' or click to play again", canvas.getWidth() / 2 - 130, canvas.getHeight() / 2 + 90);
        }
    }

    private String getHearts(int lives) {
        StringBuilder hearts = new StringBuilder();
        for (int i = 0; i < lives; i++) {
            hearts.append("❤");
        }
        return hearts.toString();
    }

    public double getCanvasWidth() {
        return canvas.getWidth();
    }

    public double getCanvasHeight() {
        return canvas.getHeight();
    }
}
