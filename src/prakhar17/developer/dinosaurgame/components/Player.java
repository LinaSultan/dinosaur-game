package prakhar17.developer.dinosaurgame.components;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Decorator pattern - Component.
 * Everything the Board needs from the character. Both the Dinosaur
 * (Concrete Component) and the decorators (like ShieldDecorator) implement it,
 * so the Board can use any of them in the same way.
 */
public interface Player {

    void printRun(Graphics pen);

    void printDead(Graphics pen);

    void jump(int jumpForce);

    void fall();

    boolean isJumping();

    Rectangle getBounds();

    void reset();

    // true while the player can hit obstacles without dying
    boolean isInvincible();
}
