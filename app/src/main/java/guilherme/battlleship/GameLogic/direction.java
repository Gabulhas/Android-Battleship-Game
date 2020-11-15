package guilherme.battlleship.GameLogic;

import android.graphics.Point;

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

    public static int[] directionToArray(directions direction) {

        switch (direction) {

            case UP:
                return new int[]{0, -1};
            case DOWN:
                return new int[]{0, 1};
            case LEFT:
                return new int[]{-1, 0};
            case RIGHT:
                return new int[]{1, 0};
        }
        return new int[]{0, 0};

    }

    public static int[] coordsAndDirection(int x, int y, directions direction) {
        int[] directionArray = directionToArray(direction);
        return new int[]{x + directionArray[0], y + directionArray[1]};
    }

    public static Point pointAndDirection(Point point, directions direction) {

        int[] directionArray = directionToArray(direction);

        return new Point(point.x + directionArray[0], point.y + directionArray[1]);

    }

}
