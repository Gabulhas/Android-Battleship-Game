package guilherme.battlleship.GameLogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    public static int[][] shufflePossiblePoints(int[][] points) {
        int[][] intArray = points;
        List<int[]> intList = Arrays.asList(points);
        Collections.shuffle(intList);
        intList.toArray(intArray);
        return intArray;
    }

    public static int[][] moveToStart(int[][] points, int position) {
        Log.d("MOVE", "moveToStart: before: " + debugPoints(points));
        int[] tempA = points[0];
        points[0] = points[position];
        points[position] = tempA;

        Log.d("MOVE", "moveToStart: after: " + debugPoints(points) + " position:" + position);

        return points;
    }

    public static String debugPoints(int[][] points) {
        String result = "";

        for (int i = 0; i < points.length; i++) {

            result += "[";
            for (int j = 0; j < points[i].length; j++) {

                result += points[i][j] + " ";
            }

            result += "] ";
        }
        return result;
    }

}
