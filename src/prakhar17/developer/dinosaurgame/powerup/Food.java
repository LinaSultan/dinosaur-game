package prakhar17.developer.dinosaurgame.powerup;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/** Facade subsystem: one piece of food moving on the screen. */
public class Food {

    public static final int SIZE = 32;

    private final FoodType type;
    private int x;
    private final int y;

    public Food(FoodType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public FoodType getType() {
        return type;
    }

    public void move(int speed) {
        x -= speed;
    }

    public boolean isOffScreen() {
        return x + SIZE < 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public void draw(Graphics pen) {
        if (type == FoodType.APPLE) {
            pen.setColor(new Color(214, 48, 49));
            pen.fillOval(x, y, SIZE, SIZE);
            pen.setColor(new Color(46, 160, 67));
            pen.fillOval(x + 16, y - 4, 12, 8);          // leaf
        } else if (type == FoodType.BANANA) {
            pen.setColor(new Color(250, 204, 21));
            pen.fillRoundRect(x, y + 8, SIZE, 16, 16, 16);
            pen.setColor(new Color(120, 85, 20));
            pen.fillRect(x + SIZE - 4, y + 12, 4, 8);    // tip
        } else {
            pen.setColor(new Color(232, 52, 74));
            pen.fillPolygon(new int[]{x, x + SIZE, x + SIZE / 2},
                            new int[]{y, y, y + SIZE}, 3);
            pen.setColor(new Color(46, 160, 67));
            pen.fillRect(x + 8, y - 4, 16, 6);           // leaves
        }
    }
}
