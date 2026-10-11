
package prakhar17.developer.dinosaurgame.components;

import prakhar17.developer.dinosaurgame.utils.GameConstants;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bird implements Obstacle, GameConstants {

    private int x;
    private final int y;
    private final int width = 45;
    private final int height = 30;

    private final BufferedImage birdUp;
    private final BufferedImage birdDown;
    private int frame = 0;

    public Bird() throws IOException {
        x = GW;
        y = GROUND - 130;

        birdUp = ImageIO.read(Objects.requireNonNull(
            getClass().getResource("/resource/bird-up.png")
        ));

        birdDown = ImageIO.read(Objects.requireNonNull(
            getClass().getResource("/resource/bird-down.png")
        ));
    }

    @Override
    public void draw(Graphics pen) {
        BufferedImage currentImage =
            (frame / 6) % 2 == 0 ? birdUp : birdDown;

        pen.drawImage(
            currentImage, x, y, width, height, null
        );

        frame++;
    }

    @Override
    public void move(int speed) {
        x -= speed;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
