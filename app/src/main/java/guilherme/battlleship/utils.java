package guilherme.battlleship;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import guilherme.battlleship.GameLogic.Spot;

public class utils {

    public static Point randomPoint(int minX, int maxX, int minY, int maxY) {
        return new Point(randomInt(minX, maxX), randomInt(minY, maxY));
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return min + random.nextInt((max - min) + 1);
    }

    public static void sendToast(String messsage, Context context) {

        Toast.makeText(context, messsage, Toast.LENGTH_SHORT).show();

    }

}
