package guilherme.battlleship.GameLogic;

import guilherme.battlleship.utils;

public class direction {
    public enum directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public static directions randomDirection() {

        return directions.values()[utils.randomInt(0, directions.values().length - 1)];

    }
}
