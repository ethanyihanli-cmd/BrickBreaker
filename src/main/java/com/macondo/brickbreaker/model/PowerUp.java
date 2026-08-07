package com.macondo.brickbreaker.model;

import javafx.scene.paint.Color;

public class PowerUp {
    public enum PowerUpType {
        WIDER_PADDLE,
        MULTI_BALL,
        SLOW_BALL,
        EXTRA_LIFE
    }

    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private PowerUpType type;
    private boolean active;
    private boolean collected;

    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;
        this.speed = 120;
        this.type = type;
        this.active = true;
        this.collected = false;
    }

    public void update(double deltaTime) {
        if (active) {
            y += speed * deltaTime;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public PowerUpType getType() { return type; }
    public boolean isActive() { return active; }
    public boolean isCollected() { return collected; }

    public void setCollected(boolean collected) { this.collected = collected; }
    public void setActive(boolean active) { this.active = active; }

    public Color getColor() {
        switch (type) {
            case WIDER_PADDLE: return Color.CYAN;
            case MULTI_BALL: return Color.GREEN;
            case SLOW_BALL: return Color.BLUE;
            case EXTRA_LIFE: return Color.RED;
            default: return Color.WHITE;
        }
    }

    public String getSymbol() {
        switch (type) {
            case WIDER_PADDLE: return "⬛";
            case MULTI_BALL: return "●";
            case SLOW_BALL: return "◉";
            case EXTRA_LIFE: return "❤";
            default: return "?";
        }
    }
}
