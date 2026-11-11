
package prakhar17.developer.dinosaurgame;

import prakhar17.developer.dinosaurgame.components.*;
import prakhar17.developer.dinosaurgame.powerup.PowerUpFacade;
import prakhar17.developer.dinosaurgame.utils.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Board extends JPanel implements GameConstants {

    private Timer timer;
    private final Ground ground;
    private final Top top;
    private final Dinosaur dino;
    private Player player;                  // the dinosaur, possibly wrapped by a decorator
    private final PowerUpFacade powerUps;   // Facade: food, score and power-ups
    private final Cloud cloud;
    private final GameOver gameOver;
    private final int speed;
    private Obstacle obstacle;
    private ObstacleCreator obstacleCreator;
    private boolean nextIsBird = true;
private boolean isGameOver = false;
private JButton restartButton;

    protected Board(Dinosaur dino) throws IOException {
        speed = 50;

        top = new Top();
        this.dino = dino;
        this.player = dino;
        powerUps = new PowerUpFacade();
        ground = new Ground();
        cloud = new Cloud();
        gameOver = new GameOver();
        obstacleCreator = new CactusCreator();
        obstacle = obstacleCreator.createObstacle();

        setFocusable(true);
        bindEvents();
        gameLoop();
        createRestartButton();
    }

    @Override
protected void paintComponent(Graphics pen) {
    super.paintComponent(pen);

    printBackground(pen);
    cloud.printCloud(pen);
    ground.moveGroundBySpeed(speed);

    obstacle.draw(pen);
    obstacle.move(speed);

    if (obstacle.getBounds().x + obstacle.getBounds().width < 0) {
        powerUps.onObstaclePassed();
        try {
            createNextObstacle();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    try {
        top.printTop(pen);
        ground.printGround(pen);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

    if (!player.isInvincible()) {
        player = dino;                              // shield time is over: back to the plain dinosaur
    }
    powerUps.update(speed);
    powerUps.collect(player.getBounds());

    if (!player.isInvincible() && powerUps.shouldGrantShield()) {
        player = new ShieldDecorator(player);       // Decorator: wrap the dinosaur with a shield
    }

    powerUps.draw(pen);
    player.fall();

    if (player.getBounds().intersects(obstacle.getBounds()) && !player.isInvincible()) {
        gameOver.printGameOver(pen);
        player.printDead(pen);
        isGameOver = true;
        restartButton.setVisible(true);
        timer.stop();
    } else {
        player.printRun(pen);
    }
}

    private void printBackground(Graphics pen) {
        pen.setColor(new Color(GB_RED, GB_GREEN, GB_BLUE));
        pen.fillRect(0, 0, GW, GH);
    }

    private void gameLoop() {
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

   private void bindEvents() {
    addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE && !isGameOver) {
                player.jump(powerUps.getJumpForce());
            }
            if (e.getKeyCode() == KeyEvent.VK_R && isGameOver) {
                restartGame();
            }
        }
    });
}

    private void createNextObstacle() throws IOException {
        if (nextIsBird) {
            obstacleCreator = new BirdCreator();
        } else {
            obstacleCreator = new CactusCreator();
        }

        obstacle = obstacleCreator.createObstacle();
        nextIsBird = !nextIsBird;
    }

    private void restartGame() {
    try {
        obstacleCreator = new CactusCreator();
        obstacle = obstacleCreator.createObstacle();
        nextIsBird = true;
        player = dino;          // drop any shield
        dino.reset();
        powerUps.reset();
        ground.reset();
        isGameOver = false;
        restartButton.setVisible(false);
        requestFocusInWindow();
        timer.start();
        repaint();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

private void createRestartButton() {
    setLayout(null);

    Color normal = new Color(83, 83, 83);
    Color hover = new Color(110, 110, 110);

    restartButton = new JButton("RESTART");
    restartButton.setBounds((GW - 160) / 2, 350, 160, 45);
    restartButton.setFont(new Font("Arial", Font.BOLD, 18));
    restartButton.setForeground(Color.WHITE);
    restartButton.setBackground(normal);
    restartButton.setOpaque(true);
    restartButton.setBorderPainted(false);
    restartButton.setFocusPainted(false);
    restartButton.setFocusable(false);
    restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    restartButton.setVisible(false);

    restartButton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            restartButton.setBackground(hover);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            restartButton.setBackground(normal);
        }
    });

    restartButton.addActionListener(e -> restartGame());
    add(restartButton);
}
}
