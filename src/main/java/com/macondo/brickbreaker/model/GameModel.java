package com.macondo.brickbreaker.model;

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

    private int score;
    private int lives;
    private boolean gameOver;

    private final double CANVAS_WIDTH = 800;
    private final double CANVAS_HEIGHT = 600;

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
    }

    public void update(double deltaTime) {
        if (gameOver) return;

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
                    double speed = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY *ballSpeedY);
                    ballSpeedX = speed * Math.sin(angle);
                    ballSpeedY = -speed * Math.cos(angle);
                    ballY = paddleY - ballRadius;
                }
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

    public void addScore(int points) { score += points; }
    public void setGameOver(boolean over) { gameOver = over; }
}
