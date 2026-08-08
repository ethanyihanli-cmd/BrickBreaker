package com.macondo.brickbreaker.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import com.macondo.brickbreaker.util.SoundManager;

public class GameModel {
    //white small paddle to hit ball
    private double paddleX;
    private double paddleY;
    private double paddleWidth;
    private double paddleHeight;

    private List<Ball> balls;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;

    private int score;
    private int lives;
    private boolean gameOver;
    private boolean won;

    private final double CANVAS_WIDTH = 800;
    private final double CANVAS_HEIGHT = 600;
    private java.util.Random random;



    //ball to hit on
    private double ballX;
    private double ballY;
    private double ballRadius;
    private double ballSpeedX;
    private double ballSpeedY;


    public GameModel() {
        paddleWidth = 100;
        paddleHeight = 15;
        paddleX = 400 - paddleWidth / 2;
        paddleY = 560;

        balls = new ArrayList<>();
        balls.add(new Ball(400, 540, 10, 200, -250));

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

        for (Ball ball : balls) {
            ball.update(deltaTime);
        }

        for (Ball ball : balls) {
            if (ball.getX() - ball.getRadius() < 0) {
                ball.setX(ball.getRadius());
                ball.reflectX();
            }

            if (ball.getX() + ball.getRadius() > CANVAS_WIDTH) {
                ball.setX(CANVAS_WIDTH - ball.getRadius());
                ball.reflectX();
            }

            if (ball.getY() - ball.getRadius() < 0) {
                ball.setY(ball.getRadius());
                ball.reflectY();
            }

            if (ball.getY() + ball.getRadius() > CANVAS_HEIGHT) {
                SoundManager.getInstance().playLose();
                ball.setActive(false);
            }
        }

        balls.removeIf(b -> !b.isActive());

        if (balls.isEmpty()) {
            loseLife();
            if (lives > 0) {
                Ball newBall = new Ball(400, 540, 10, 200, -250);
                balls.add(newBall);
            }
            return;
        }

        for (Ball ball : balls) {
            if (ball.getVy() > 0) {
                if (ball.getY() + ball.getRadius() >= paddleY &&
                    ball.getY() + ball.getRadius() <= paddleY + paddleHeight + 10) {

                    if(ball.getX() >= paddleX && ball.getX() <= paddleX + paddleWidth) {
                        SoundManager.getInstance().playHit();
                        double hitPos = (ball.getX() - paddleX) / paddleWidth;
                        double angle = (hitPos - 0.5) * Math.PI * 0.7;
                        double speed = Math.sqrt(ball.getVx() * ball.getVx() + ball.getVy() * ball.getVy());
                        ball.setVx(speed * Math.sin(angle));
                        ball.setVy(-speed * Math.cos(angle));
                        ball.setY(paddleY - ball.getRadius());
                    }
                }
            }
        }

    for (Ball ball : balls) {
        for (Brick brick : bricks) {
            if (brick.isDestroyed()) continue;

            if (ballIntersectsBrick(ball, brick)) {
                SoundManager.getInstance().playBrick();
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
                ball.reflectY();
                ballSpeedY = -ballSpeedY;
                break;
            }
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
            SoundManager.getInstance().playWin();
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
                    SoundManager.getInstance().playPowerup();
                    applyPowerUp(p);
                    p.setCollected(true);
                }
            }
        }
        powerUps.removeIf(p -> p.isCollected());
    }


    private boolean ballIntersectsBrick(Ball ball, Brick brick) {
        double bx = brick.getX();
        double by = brick.getY();
        double bw = brick.getWidth();
        double bh = brick.getHeight();
        double ballX = ball.getX();
        double ballY = ball.getY();
        double r = ball.getRadius();

        double closestX = Math.max(bx, Math.min(ballX, bx + bw));
        double closestY = Math.max(by, Math.min(ballY, by + bh));
        double dx = ballX - closestX;
        double dy = ballY - closestY;

        return (dx * dx + dy * dy) < (r * r);
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
            case SLOW_BALL: {
                for (Ball ball : balls) {
                    double speed = Math.sqrt(ball.getVx() * ball.getVx() + ball.getVy() * ball.getVy());
                    double newSpeed = Math.max(100, speed * 0.7);
                    double angle = Math.atan2(ball.getVy(), ball.getVx());
                    ball.setVx(newSpeed * Math.cos(angle));
                    ball.setVy(newSpeed * Math.sin(angle));
                }
                double speed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
                double newSpeed = Math.max(100, speed * 0.7);
                double angle = Math.atan2(ballSpeedY, ballSpeedX);
                ballSpeedX = newSpeed * Math.cos(angle);
                ballSpeedY = newSpeed * Math.sin(angle);
                break;
            }
            case MULTI_BALL: {
                for (int i = 0; i < 2; i++) {
                    double angle = Math.PI / 4 + i * Math.PI / 2;
                    double speed = 200;
                    Ball b = new Ball(
                            400 + (i * 30 -15),
                            540,
                            10,
                            speed * Math.cos(angle),
                            speed * Math.sin(angle)
                    );
                    balls.add(b);
                }
                break;
            }
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
        balls.clear();
        balls.add(new Ball(400, 540, 10, 200, -250));
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
    public List<Ball> getBalls() { return balls; }
    public List<Brick> getBricks() { return bricks; }
    public List<PowerUp> getPowerUps() { return powerUps; }


    public void setGameOver(boolean over) { gameOver = over; }
    public void setWon(boolean won) { this.won = won; }
}
