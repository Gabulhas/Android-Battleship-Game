package guilherme.battlleship.GameLogic;

import android.graphics.Color;
import android.graphics.Point;
import android.widget.TextView;


public class Spot {
    private boolean isLive;
    private TextView spotButton;
    private int liveColor;
    private Point point;

    public Spot(TextView spotButton, int liveColor, int x, int y) {
        this.isLive = true;
        this.spotButton = spotButton;
        this.liveColor = liveColor;
        this.point = new Point(x, y);

    }

    public boolean isLive() {
        return isLive;
    }

    public void destroy() {
        isLive = false;
        liveColor = Color.argb(255, 100, 1, 1); //dead
    }

    public TextView getSpotButton() {
        return spotButton;
    }

    public void setSpotButton(TextView spotButton) {
        this.spotButton = spotButton;
    }

    public int getLiveColor() {
        return liveColor;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
