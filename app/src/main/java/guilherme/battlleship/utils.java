package guilherme.battlleship;

import android.graphics.Point;

import java.util.Random;

public class utils {

    public static Point randomPoint(int minX, int maxX, int minY, int maxY) {
        return new Point(randomInt(minX, maxX), randomInt(minY, maxY));
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt((max - min) + 1);
    }

}
