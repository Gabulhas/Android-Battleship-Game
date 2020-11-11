package guilherme.battlleship.GameLogic;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.widget.TextView;


public class Spot {
    private boolean isLive;
    private int liveColor;
    private int deadColor;
    private boolean containsShip;
    private int shipNum;
    private GradientDrawable shape;

    public GradientDrawable getShape() {
        return shape;
    }

    public void setShape(GradientDrawable shape) {
        this.shape = shape;
    }

    public Spot(int x, int y) {
        this.isLive = true;
        this.liveColor = Color.parseColor("#90CAF9");
        this.deadColor = Color.parseColor("#757575");

        this.point = new Point(x, y);
        this.containsShip = false;
        this.shipNum = -1;

        this.shape = initShape();
        shape.setColor(this.liveColor);
    }

    public Spot(int x, int y, int shipNum, int liveColor, int deadColor) {
        this.isLive = true;
        this.liveColor = liveColor;
        this.deadColor = deadColor;

        this.point = new Point(x, y);
        this.containsShip = true;
        this.shipNum = shipNum;

        this.shape = initShape();
        shape.setColor(this.liveColor);

    }
    private static GradientDrawable initShape(){
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setStroke(2, Color.argb(255, 255, 255, 255));
        return shape;
    }

    public boolean isContainsShip() {
        return containsShip;
    }

    public void setContainsShip(boolean containsShip) {
        this.containsShip = containsShip;
    }

    public int getShipNum() {
        return shipNum;
    }

    public void setShipNum(int shipNum) {
        this.shipNum = shipNum;
    }


    public int getCurrentColor() {

        if (isLive()) {
            return liveColor;
        }
        return deadColor;

    }

    private Point point;


    public boolean isLive() {
        return isLive;
    }

    public void destroy() {
        isLive = false;
        shape.setColor(deadColor);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
