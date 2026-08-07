package com.macondo.brickbreaker.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public class GameModel {
    //white small paddle to hit ball
    private double paddleX;
    private double paddleY;
    private double paddleWidth;
    private double paddleHeight;

    //ball to hit on
    private double ballX;
    private double ballY;
    private double ballRadius;
    private double ballSpeedX;
    private double ballSpeedY;

    private List<Brick> bricks;

    private List<PowerUp> powerUps;

    private int score;
    private int lives;
    private boolean gameOver;
    private boolean won;

    private final double CANVAS_WIDTH = 800;
    private final double CANVAS_HEIGHT = 600;
    private java.util.Random random;

    public GameModel() {
        paddleWidth = 100;
        paddleHeight = 15;
        paddleX = 400 - paddleWidth / 2;
        paddleY = 560;

        ballRadius = 10;
        ballX = 400;
        ballY = 540 - ballRadius;
        ballSpeedX = 200;
        ballSpeedY = -250;

        score = 0;
        lives = 3;
        gameOver = false;
        won = false;

        random = new java.util.Random();
        bricks = new ArrayList<>();
        powerUps = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        bricks.clear();
        int rows = 8;
        int cols = 10;
        double brickWidth = 60;
        double brickHeight = 20;
        double spacing = 5;
        double offsetX = 80;
        double offsetY = 50;

        Color[] colors = {
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                Color.BLUE, Color.INDIGO, Color.VIOLET, Color.PINK
        };

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = offsetX + col * (brickWidth + spacing);
                double y = offsetY + row * (brickHeight + spacing);
                int hp = (row < 2) ? 2 : 1;
                Color color = colors[row % colors.length];
                bricks.add(new Brick(x, y, brickWidth, brickHeight, hp, color));
            }
        }
    }

    public void update(double deltaTime) {
        if (gameOver || won) return;

        ballX += ballSpeedX * deltaTime;
        ballY += ballSpeedY * deltaTime;

        if (ballX - ballRadius < 0) {
            ballX = ballRadius;
            ballSpeedX = -ballSpeedX;
        }
        if (ballX + ballRadius > CANVAS_WIDTH) {
            ballX = CANVAS_WIDTH - ballRadius;
            ballSpeedX = -ballSpeedX;
        }

        if (ballY - ballRadius < 0) {
            ballY = ballRadius;
            ballSpeedY = -ballSpeedY;
        }
        if (ballY + ballRadius > CANVAS_HEIGHT) {
            loseLife();
            resetBall();
            return;
        }

        if (ballSpeedY > 0) {
            if (ballY + ballRadius >= paddleY &&
                    ballY + ballRadius <= paddleY + paddleHeight + 10) {

                if (ballX >= paddleX && ballX <= paddleX + paddleWidth) {
                    double hitPos = (ballX - paddleX) / paddleWidth;
                    double angle = (hitPos - 0.5) * Math.PI * 0.7;
                    double speed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
                    ballSpeedX = speed * Math.sin(angle);
                    ballSpeedY = -speed * Math.cos(angle);
                    ballY = paddleY - ballRadius;
                }
            }
        }


        for (Brick brick : bricks) {
            if (brick.isDestroyed()) continue;

            if (ballIntersectsBrick(brick)) {
                brick.hit();
                if (brick.isDestroyed()) {
                    score += 10;

                    if (random.nextDouble() < 0.15) {
                        powerUps.add(new PowerUp(
                                brick.getX() + brick.getWidth() / 2 - 10,
                                brick.getY() + brick.getHeight() / 2 - 10,
                                getRandomPowerUpType()
                        ));
                    }
                }
                ballSpeedY = -ballSpeedY;
                break;
            }
        }

        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }
        if (allDestroyed) {
            won = true;
        }

        for (PowerUp p : powerUps) {
            p.update(deltaTime);
        }

        powerUps.removeIf(p -> p.getY() > CANVAS_HEIGHT);

        for (PowerUp p : powerUps) {
            if (p.isActive() && !p.isCollected()) {
                if (p.getX() + p.getWidth() > paddleX &&
                        p.getX() < paddleX + paddleWidth &&
                        p.getY() + p.getHeight() > paddleY &&
                        p.getY() < paddleY + paddleHeight) {
                    applyPowerUp(p);
                    p.setCollected(true);
                }
            }
        }
        powerUps.removeIf(p -> p.isCollected());
    }


    private boolean ballIntersectsBrick(Brick brick) {
        double bx = brick.getX();
        double by = brick.getY();
        double bw = brick.getWidth();
        double bh = brick.getHeight();

        double closestX = Math.max(bx, Math.min(ballX, bx + bw));
        double closestY = Math.max(by, Math.min(ballY, by + bh));
        double dx = ballX - closestX;
        double dy = ballY - closestY;

        return (dx * dx + dy * dy) < (ballRadius * ballRadius);
    }

    private PowerUp.PowerUpType getRandomPowerUpType() {
        PowerUp.PowerUpType[] types = PowerUp.PowerUpType.values();
        return types[random.nextInt(types.length)];
    }

    private void applyPowerUp(PowerUp p) {
        switch (p.getType()) {
            case WIDER_PADDLE:
                paddleWidth = Math.min(200, paddleWidth + 30);
                break;
            case EXTRA_LIFE:
                lives++;
                break;
            case SLOW_BALL:
                double speed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
                double newSpeed = Math.max(100, speed * 0.7);
                double angle = Math.atan2(ballSpeedY, ballSpeedX);
                ballSpeedX = newSpeed * Math.cos(angle);
                ballSpeedY = newSpeed * Math.sin(angle);
                break;
            case MULTI_BALL:
                break;
        }
    }

    public void movePaddleLeft(double deltaTime) {
        double speed = 400;
        paddleX -= speed * deltaTime;
        if (paddleX < 0) paddleX = 0;
    }

    public void movePaddleRight(double deltaTime) {
        double speed = 400;
        paddleX += speed *deltaTime;
        if (paddleX + paddleWidth > CANVAS_WIDTH) {
            paddleX = CANVAS_WIDTH - paddleWidth;
        }
    }

    public void setPaddleX(double x) {
        paddleX = x;
        if (paddleX < 0) paddleX = 0;
        if (paddleX + paddleWidth > CANVAS_WIDTH) {
            paddleX = CANVAS_WIDTH - paddleWidth;
        }
    }

    private void resetBall() {
        ballX = 400;
        ballY = 540 - ballRadius;
        ballSpeedX = 200;
        ballSpeedY = -250;
    }

    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
        }
    }

    public void resetGame() {
        paddleWidth = 100;
        paddleX = 400 - paddleWidth / 2;
        paddleY = 560;
        ballX = 400;
        ballY = 540 - ballRadius;
        ballSpeedX = 200;
        ballSpeedY = -250;
        score = 0;
        lives = 3;
        gameOver = false;
        won = false;
        powerUps.clear();
        createBricks();
    }

    public double getPaddleX() { return paddleX; }
    public double getPaddleY() { return paddleY; }
    public double getPaddleWidth() { return paddleWidth; }
    public double getPaddleHeight() { return paddleHeight; }


    public double getBallX() { return ballX; }
    public double getBallY() { return ballY; }
    public double getBallRadius() { return ballRadius; }
    public double getBallSpeedX() { return ballSpeedX; }
    public double getBallSpeedY() { return ballSpeedY; }


    public int getScore() { return score; }
    public int getLives() { return lives; }
    public boolean isGameOver() { return gameOver; }
    public boolean isWon() { return won; }
    public List<Brick> getBricks() { return bricks; }
    public List<PowerUp> getPowerUps() { return powerUps; }


    public void setGameOver(boolean over) { gameOver = over; }
    public void setWon(boolean won) { this.won = won; }
}
