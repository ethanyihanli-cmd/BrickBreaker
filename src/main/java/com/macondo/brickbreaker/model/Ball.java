package com.macondo.brickbreaker.model;

public class Ball {
    private double x;
    private double y;
    private double radius;
    private double vx;
    private double vy;
    private boolean active;

    public Ball(double x, double y, double radius, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.active = true;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    public void reflectX() { vx = -vx; }
    public void reflectY() { vy = -vy; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadius() { return radius; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public boolean isActive() { return active; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
    public void setActive(boolean active) { this.active = active; }
}
