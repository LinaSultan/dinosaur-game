package prakhar17.developer.dinosaurgame.powerup;

/** Facade subsystem: keeps the score and tells when the player earns a shield. */
public class ScoreManager {

    public static final int OBSTACLE_POINTS = 10;
    public static final int APPLE_POINTS = 20;
    private static final int SHIELD_EVERY = 100;     // a shield every 100 points

    private int score = 0;
    private int nextShieldScore = SHIELD_EVERY;

    public void add(int points) {
        score += points;
    }

    public int getScore() {
        return score;
    }

    // Returns true once each time the score reaches 100, 200, 300 ...
    public boolean shieldEarned() {
        if (score >= nextShieldScore) {
            nextShieldScore += SHIELD_EVERY;
            return true;
        }
        return false;
    }

    public void reset() {
        score = 0;
        nextShieldScore = SHIELD_EVERY;
    }
}
