package com.macondo.brickbreaker.model;

import javafx.scene.paint.Color;

public class Brick {
    private double x;
    private double y;
    private double width;
    private double height;
    private int hitPoints;
    private int maxHitPoints;
    private Color color;
    private boolean destroyed;

    public Brick(double x, double y, double width, double height, int hitPoints, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.color = color;
        this.destroyed = false;
    }

    public void hit() {
        hitPoints--;
        if (hitPoints <= 0) {
            destroyed = true;
        }
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public int getHitPoints() { return hitPoints; }
    public int getMaxHitPoints() { return maxHitPoints; }
    public Color getColor() { return color; }
    public boolean isDestroyed() { return destroyed; }
}
