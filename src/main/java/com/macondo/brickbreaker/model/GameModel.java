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

    public void setPaddleX(double x) { paddleX = x; }
    public void setBallX(double x) { ballX = x; }
    public void setBallY(double y) { ballY = y; }
    public void setBallSpeedX(double x) { ballSpeedX = x; }
    public void setBallSpeedY(double y) { ballSpeedY = y; }
    public void addScore(int points) { score += points; }
    public void loseLife() { lives--; }
}
