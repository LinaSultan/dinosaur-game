
package prakhar17.developer.dinosaurgame.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import prakhar17.developer.dinosaurgame.utils.GameConstants;

public class Dinosaur implements GameConstants, Prototype {

    private final BufferedImage spriteImage;
    private final BufferedImage[] dinosaurRunImages;
    private final BufferedImage dinosaurDeadImage;

    private int index;
    private int y;
    private boolean isJumping;
    private boolean girl;

    public boolean isJumping() {
        return isJumping;
    }

    public Dinosaur() throws IOException {
        spriteImage = ImageIO.read(
                Objects.requireNonNull(
                        getClass().getResource("/resource/sprite-dino.png")
                )
        );

        dinosaurRunImages = new BufferedImage[4];
        dinosaurDeadImage = spriteImage.getSubimage(1690, 1, 89, 96);

        loadRunImages();

        index = 0;
        y = GROUND;
        isJumping = false;
        girl = false;
    }

    private void loadRunImages() {
        dinosaurRunImages[0] = spriteImage.getSubimage(1338, 1, 89, 96);
        dinosaurRunImages[1] = spriteImage.getSubimage(1425, 1, 89, 96);
        dinosaurRunImages[2] = spriteImage.getSubimage(1513, 1, 89, 96);
        dinosaurRunImages[3] = spriteImage.getSubimage(1601, 1, 89, 96);
    }

    @Override
    public Dinosaur clone() {
        try {
            Dinosaur copy = new Dinosaur();
            copy.girl = this.girl;
            copy.index = 0;
            copy.y = GROUND;
            copy.isJumping = false;
            return copy;
        } catch (IOException e) {
            throw new IllegalStateException("Could not clone Dinosaur", e);
        }
    }

    public Dinosaur asGirl() {
        Dinosaur copy = clone();
        copy.girl = true;
        return copy;
    }

    public void printRun(Graphics pen) {
        if (index > 3) {
            index = 0;
        }

        BufferedImage currentRunImage = dinosaurRunImages[index];
        int x = GW - 1100;

        pen.drawImage(
                currentRunImage, x, y,
                currentRunImage.getWidth(),
                currentRunImage.getHeight(), null
        );

        drawBow(pen, x, y);
        index++;
    }

    public void printDead(Graphics pen) {
        int x = GW - 1100;

        pen.drawImage(
                dinosaurDeadImage, x, y,
                dinosaurDeadImage.getWidth(),
                dinosaurDeadImage.getHeight(), null
        );

        drawBow(pen, x, y);
    }

    private void drawBow(Graphics pen, int x, int y) {
        if (!girl) {
            return;
        }

        Graphics2D g = (Graphics2D) pen.create();

        g.setColor(Color.RED);

        // Bow at the upper back edge of the head
        g.fillOval(x + 38, y - 2, 8, 6);
        g.fillOval(x + 46, y - 2, 8, 6);
        g.fillOval(x + 43, y + 1, 5, 5);

        g.dispose();
    }

    public void jump() {
        if (isJumping) {
            return;
        }

        int jumpForce = 250;
        y = y - jumpForce;
        isJumping = true;
    }

    public void fall() {
        if (y < GROUND) {
            y = y - GRAVITY;
        } else if (y == GROUND) {
            isJumping = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(GW - 1100, y, 89, 96);
    }
}
