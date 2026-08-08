package com.macondo.brickbreaker.util;

public class SoundManager {
    private static SoundManager instance;

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playHit() {
    }

    public void playBrick() {
    }

    public void playLose() {
    }

    public void playPowerup() {
    }

    public void playWin() {
    }

    public void GameOver() {
    }
}
