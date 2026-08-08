# Brick Breaker

A simple brick breaker game. Move the paddle, bounce the ball, break all the bricks, and try not to lose all your lives. The game also has power-ups, extra balls, and stronger bricks to make it more fun.

## Why I made it

I wanted to make a game that connects to my childhood. I used to take my grandma's phone and play games on an old Nokia. One of the games I remember was a simple brick breaker game where you control a paddle and bounce a ball up and down.

I wanted to recreate that feeling, but make it a little better by adding upgrades and other things that make the game more fun. It is still simple, but now it has power-ups, multiple balls, lives, score, and a restart system.

## Tools

Main version: Java 21 + JavaFX. Uses Maven.

There is also an HTML version in one file with plain HTML, CSS, and JavaScript. It does not need Java or Maven.

## Run the Java version

Make sure Java 21 is installed. Then in the project folder:

- Mac/Linux: `./mvnw javafx:run`
- Windows: `mvnw.cmd javafx:run`

You can also run `Launcher.main()` in IntelliJ.

## Run the HTML version

Just open `index.html` in any browser.

## Controls

- Move with the mouse, arrow keys, or A/D.
- Press `R` to restart after winning or losing.
- You can also click after winning or losing to restart.

## Power-ups

- Wider paddle: makes the paddle bigger.
- Multi ball: adds more balls.
- Slow ball: slows the ball down.
- Extra life: gives another life.

## How to rebuild it yourself

1. Make a canvas or game window.
2. Draw a paddle near the bottom.
3. Draw a ball that moves every frame.
4. Make the ball bounce when it hits walls.
5. Make the ball bounce when it hits the paddle.
6. Create rows of bricks.
7. Check when the ball hits a brick.
8. Remove or damage the brick after a hit.
9. Add score when bricks break.
10. Take away a life when the ball falls below the paddle.
11. Add win and game over screens.
12. Add power-ups that fall from broken bricks.
13. Let the player restart the game.

In the Java version I split the code into model, view, controller, input, and utility classes. The HTML version keeps the same idea, but everything is inside one file so it is easier to open and test.
