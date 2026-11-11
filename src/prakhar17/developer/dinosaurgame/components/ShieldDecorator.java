package prakhar17.developer.dinosaurgame.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Decorator pattern - Concrete Decorator (same idea as RedShapeDecorator in the lecture).
 * It draws the dinosaur as usual, then adds a shield on top of it.
 * While the shield is active the player can hit obstacles without dying.
 */
public class ShieldDecorator extends PlayerDecorator {

    private static final long SHIELD_TIME = 10000;   // 10 seconds

    private final long endTime;                      // added state

    public ShieldDecorator(Player decoratedPlayer) {
        super(decoratedPlayer);
        endTime = System.currentTimeMillis() + SHIELD_TIME;
    }

    @Override
    public void printRun(Graphics pen) {
        decoratedPlayer.printRun(pen);   // the normal behaviour
        drawShield(pen);                 // the added behaviour
    }

    @Override
    public boolean isInvincible() {
        return System.currentTimeMillis() < endTime;
    }

    private void drawShield(Graphics pen) {
        Rectangle r = decoratedPlayer.getBounds();

        pen.setColor(new Color(80, 170, 255, 80));
        pen.fillOval(r.x - 14, r.y - 14, r.width + 28, r.height + 28);

        pen.setColor(new Color(30, 120, 220));
        pen.drawOval(r.x - 14, r.y - 14, r.width + 28, r.height + 28);
    }
}
