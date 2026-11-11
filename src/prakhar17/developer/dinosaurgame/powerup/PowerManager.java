package prakhar17.developer.dinosaurgame.powerup;

/** Facade subsystem: turns the temporary powers on and off (each lasts 10 seconds) and remembers the apple line. */
public class PowerManager {

    private static final long POWER_TIME = 10000;   // 10 seconds
    private static final long APPLE_LINE_TIME = 2000; // the apple line is shown for 2 seconds

    private long doubleScoreEnd = 0;
    private long higherJumpEnd = 0;
    private long appleEnd = 0;

    public void activate(FoodType type) {
        long end = System.currentTimeMillis() + POWER_TIME;
        if (type == FoodType.BANANA) {
            doubleScoreEnd = end;
        } else if (type == FoodType.STRAWBERRY) {
            higherJumpEnd = end;
        } else {
            appleEnd = System.currentTimeMillis() + APPLE_LINE_TIME;
        }
    }

    public void deactivate(FoodType type) {
        if (type == FoodType.BANANA) {
            doubleScoreEnd = 0;
        } else if (type == FoodType.STRAWBERRY) {
            higherJumpEnd = 0;
        } else {
            appleEnd = 0;
        }
    }

    public boolean isDoubleScoreActive() {
        return System.currentTimeMillis() < doubleScoreEnd;
    }

    public boolean isHigherJumpActive() {
        return System.currentTimeMillis() < higherJumpEnd;
    }

    // true for 2 seconds after the player eats an apple (to show the line)
    public boolean isAppleShown() {
        return System.currentTimeMillis() < appleEnd;
    }

    public int getScoreMultiplier() {
        if (isDoubleScoreActive()) {
            return 2;
        }
        return 1;
    }

    // Seconds left for a power (0 if it is not active)
    public int secondsLeft(FoodType type) {
        long end = (type == FoodType.BANANA) ? doubleScoreEnd : higherJumpEnd;
        long left = end - System.currentTimeMillis();
        if (left <= 0) {
            return 0;
        }
        return (int) (left / 1000) + 1;
    }

    public void reset() {
        deactivate(FoodType.BANANA);
        deactivate(FoodType.STRAWBERRY);
        deactivate(FoodType.APPLE);
    }
}
