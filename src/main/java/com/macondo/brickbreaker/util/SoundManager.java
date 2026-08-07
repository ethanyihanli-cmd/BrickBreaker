package com.macondo.brickbreaker.util;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class SoundManager {
    private static SoundManager instance;
    private AudioClip hitSound;
    private AudioClip brickSound;
    private AudioCLip loseSound;
    private AudioClip powerupSound;
    private AudioClip winSound;
    private AudioClip gameOverSound;

    private SoundManager() {
        loadSounds();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadSounds() {
        try {
            URL hitUrl = getClass().getResource("/sounds/hit.wav");
            URL brickUrl = getClass().getResource("/sounds/brick.wav");
            URL loseUrl = getClass().getResource("/sounds/lose.wav");
            URL powerupUrl = getClass().getResource("/sounds/powerup.wav");
            URL winUrl = getClass().getResource("/sounds.win.wav");
            URL gameOverUrl = getClass().getResource("/sounds/gameover.wav");


            if (hitUrl != null) hitSound = new AudioCLip(hitUrl.toString());
            if (brickUrl != null) brickSound = new AudioClip(brickUrl.toString());
            if (loseUrl != null) loseSound = new AudioClip(loseUrl.toString());
            if (powerupUrl != null) powerupSound = new AudioClip(powerupUrl.toString());
            if (winUrl != null) winSound = new AudioClip(WinUrl.toString());
            if (gameOverUrl != null) gameOverSound = new AudioClip(gameOverUrl.toString());
        } catch (Exception e) {
            System.out.println("Sound files error, running without it");
        }
    }

    public void playHit() {
        if (hitSound != null) {
            hitSound.stop();
            hitSound.play(0.3);
        }
    }

    public void playBrick() {
        if (brickSound != null) {
            brickSound.stop();
            brickSound.play(0.4);
        }
    }

    public void playLose() {
        if (loseSound != null) {
            loseSound.stop();
            loseSound.play(0.5);
        }
    }

    public void playPowerup() {
        if (powerupSound != null) {
            powerupSound.stop();
            powerupSound.play(0.6);
        }
    }

    public void playWin() {
        if (winSound != null) {
            winSound.stop();
            winSound.play(0.7);
        }
    }

    public void GameOver() {
        if (gameOverSound != null) {
            gameOverSound.stop();
            gameOverSound.play(0.7);
        }
    }
}
