package prakhar17.developer.dinosaurgame.components;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Decorator pattern - Decorator (same idea as ShapeDecorator in the lecture).
 * It implements Player and keeps a reference to a Player (decoratedPlayer).
 * Every method just calls the same method on decoratedPlayer.
 * The concrete decorators change only what they want to add.
 */
public abstract class PlayerDecorator implements Player {

    protected Player decoratedPlayer;

    public PlayerDecorator(Player decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override
    public void printRun(Graphics pen) {
        decoratedPlayer.printRun(pen);
    }

    @Override
    public void printDead(Graphics pen) {
        decoratedPlayer.printDead(pen);
    }

    @Override
    public void jump(int jumpForce) {
        decoratedPlayer.jump(jumpForce);
    }

    @Override
    public void fall() {
        decoratedPlayer.fall();
    }

    @Override
    public boolean isJumping() {
        return decoratedPlayer.isJumping();
    }

    @Override
    public Rectangle getBounds() {
        return decoratedPlayer.getBounds();
    }

    @Override
    public void reset() {
        decoratedPlayer.reset();
    }

    @Override
    public boolean isInvincible() {
        return decoratedPlayer.isInvincible();
    }
}
