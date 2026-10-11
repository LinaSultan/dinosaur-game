package prakhar17.developer.dinosaurgame.components;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Obstacle {
     void draw(Graphics pen);
    void move(int speed);
    int getX();
    Rectangle getBounds();
}
