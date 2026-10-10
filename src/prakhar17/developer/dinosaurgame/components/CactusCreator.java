
package prakhar17.developer.dinosaurgame.components;

import java.io.IOException;

public class CactusCreator extends ObstacleCreator {

    @Override
    public Obstacle createObstacle() throws IOException {
        return new Cactus();
    }
}
