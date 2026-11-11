package prakhar17.developer.dinosaurgame.powerup;

import prakhar17.developer.dinosaurgame.utils.GameConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** Facade subsystem: draws the score and the active powers at the top right. */
public class Hud implements GameConstants {

    private static final int X = GW - 300;

    public void draw(Graphics pen, int score, PowerManager powers) {
        pen.setFont(new Font("Arial", Font.BOLD, 24));
        pen.setColor(new Color(83, 83, 83));
        pen.drawString("SCORE: " + score, X, 60);

        pen.setFont(new Font("Arial", Font.BOLD, 16));
        int y = 95;                                      // every active line goes under the previous one

        if (powers.isAppleShown()) {
            pen.setColor(new Color(40, 150, 70));
            pen.drawString("APPLE: +" + (ScoreManager.APPLE_POINTS * powers.getScoreMultiplier()), X, y);
            y += 25;
        }
        if (powers.isDoubleScoreActive()) {
            pen.setColor(new Color(200, 150, 0));
            pen.drawString("x2 SCORE: " + powers.secondsLeft(FoodType.BANANA) + "s", X, y);
            y += 25;
        }
        if (powers.isHigherJumpActive()) {
            pen.setColor(new Color(210, 40, 60));
            pen.drawString("HIGH JUMP: " + powers.secondsLeft(FoodType.STRAWBERRY) + "s", X, y);
        }
    }
}
