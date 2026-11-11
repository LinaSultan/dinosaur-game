package prakhar17.developer.dinosaurgame.powerup;

import prakhar17.developer.dinosaurgame.utils.GameConstants;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/** Facade subsystem: creates food from time to time and moves it across the screen. */
public class FoodSpawner implements GameConstants {

    private static final int FOOD_Y = GROUND + 60;   // the food is on the ground: the dinosaur touches it while running

    private final Random random = new Random();
    private Food food;                                // only one food on the screen at a time
    private int framesToWait = 15 + random.nextInt(25);

    // Called every frame
    public void update(int speed) {
        if (food == null) {
            framesToWait--;
            if (framesToWait <= 0) {
                createFood();
            }
        } else {
            food.move(speed);
            if (food.isOffScreen()) {
                removeFood();
            }
        }
    }

    // If the player touches the food: remove it and return it. Otherwise return null.
    public Food takeIfTouching(Rectangle playerBounds) {
        if (food != null && food.getBounds().intersects(playerBounds)) {
            Food taken = food;
            removeFood();
            return taken;
        }
        return null;
    }

    public void draw(Graphics pen) {
        if (food != null) {
            food.draw(pen);
        }
    }

    public void reset() {
        removeFood();
    }

    private void createFood() {
        FoodType type = FoodType.values()[random.nextInt(3)];
        food = new Food(type, GW, FOOD_Y);
    }

    private void removeFood() {
        food = null;
        framesToWait = 15 + random.nextInt(25);
    }
}
