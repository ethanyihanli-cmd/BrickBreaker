package com.macondo.brickbreaker.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InputHandler{
    private boolean leftPressed;
    private boolean rightPressed;
    private double mouseX;

    public void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            leftPressed = true;
        }
        if (code == KeyCode.RIGHT || code == KeyCode.D) {
            rightPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            leftPressed = false;
        }
        if (code == KeyCode.RIGHT || code == KeyCode.D) {
            rightPressed = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public double getMouseX() {
        return mouseX;
    }



}
