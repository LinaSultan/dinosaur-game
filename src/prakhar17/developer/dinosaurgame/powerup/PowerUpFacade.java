package prakhar17.developer.dinosaurgame.powerup;

import prakhar17.developer.dinosaurgame.utils.GameConstants;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Facade pattern - the Facade (same idea as ShapeMaker in the lecture).
 * One simple class for the whole food / score / power system.
 * The Board only talks to this class. It never uses Food, FoodSpawner,
 * ScoreManager, PowerManager or Hud directly.
 */
public class PowerUpFacade implements GameConstants {

    private static final int NORMAL_JUMP_FORCE = 250;   // same force the dinosaur always used
    private static final int HIGH_JUMP_FORCE = 325;     // higher jump (strawberry)

    private ScoreManager scoreManager;
    private FoodSpawner foodSpawner;
    private PowerManager powerManager;
    private Hud hud;

    // The Facade creates the subsystem classes (like ShapeMaker in the lecture)
    public PowerUpFacade() {
        scoreManager = new ScoreManager();
        foodSpawner = new FoodSpawner();
        powerManager = new PowerManager();
        hud = new Hud();
    }

    // Every frame: create and move the food
    public void update(int speed) {
        foodSpawner.update(speed);
    }

    // Every frame: if the player touched a food, collect it
    public void collect(Rectangle playerBounds) {
        Food food = foodSpawner.takeIfTouching(playerBounds);
        if (food != null) {
            collectFood(food);
        }
    }

    // The player collected a food:
    // 1) know the food type  2) apply its effect  3) update the score  4) start the power
    public void collectFood(Food food) {
        if (food.getType() == FoodType.APPLE) {
            addPoints(ScoreManager.APPLE_POINTS);        // +20 score
        }
        // apple: shows its line for 2 seconds / banana, strawberry: power for 10 seconds
        powerManager.activate(food.getType());
    }

    // The player passed an obstacle: +10 points
    public void onObstaclePassed() {
        addPoints(ScoreManager.OBSTACLE_POINTS);
    }

    // The force of the next jump (higher while the strawberry power is active)
    public int getJumpForce() {
        if (powerManager.isHigherJumpActive()) {
            return HIGH_JUMP_FORCE;
        }
        return NORMAL_JUMP_FORCE;
    }

    // true each time the score reaches 100, 200, 300 ...
    public boolean shouldGrantShield() {
        return scoreManager.shieldEarned();
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public void draw(Graphics pen) {
        foodSpawner.draw(pen);
        hud.draw(pen, scoreManager.getScore(), powerManager);
    }

    public void reset() {
        scoreManager.reset();
        foodSpawner.reset();
        powerManager.reset();
    }

    // The banana's "double score" is applied here, for all points
    private void addPoints(int points) {
        scoreManager.add(points * powerManager.getScoreMultiplier());
    }
}
