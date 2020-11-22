package guilherme.battlleship.GameLogic;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.widget.TextView;


/*
This contains all the information of a spot, like, if it contains a ship, the color/shape, the current color
if it was attacked
 */

public class Spot {
    private static final int neutralColor = Color.parseColor("#90CAF9");

    private boolean isLive;
    private Point point;
    private int liveColor;
    private int deadColor;
    private boolean containsShip;
    private Ship.ShipTypes shipType;
    private GradientDrawable shape;

    public GradientDrawable getShape() {
        return shape;
    }

    public void setShape(GradientDrawable shape) {
        this.shape = shape;
    }

    public Spot(int x, int y) {
        this.isLive = true;
        this.liveColor = Spot.neutralColor;
        this.deadColor = Color.parseColor("#757575");

        this.point = new Point(x, y);
        this.containsShip = false;
        shipType = Ship.ShipTypes.NONE;

        this.shape = initShape();
        shape.setColor(this.liveColor);
    }

    public Spot(int x, int y, Ship ship) {
        this.isLive = true;
        this.liveColor = ship.getColor();
        this.deadColor = Ship.DestroyedColour;

        this.point = new Point(x, y);
        this.containsShip = true;
        this.shipType = ship.getShipType();

        this.shape = initShape();
        shape.setColor(this.liveColor);

    }


    public Ship.ShipTypes getShipType() {
        return shipType;
    }

    public static GradientDrawable neutralShape() {
        GradientDrawable shape = initShape();
        shape.setColor(neutralColor);
        return shape;
    }

    private static GradientDrawable initShape() {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setStroke(2, Color.argb(255, 255, 255, 255));
        return shape;
    }

    public boolean isContainsShip() {
        return containsShip;
    }


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


    @Override
    public String toString() {
        return "Spot{" +
                "is alive=" + isLive +
                ", point=" + point +
                ", liveColor=" + liveColor +
                ", deadColor=" + deadColor +
                ", containsShip=" + containsShip +
                ", shipType=" + shipType +
                ", shape=" + shape +
                '}';
    }
}
