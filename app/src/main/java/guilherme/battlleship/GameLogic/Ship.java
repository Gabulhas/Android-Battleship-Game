package guilherme.battlleship.GameLogic;

import android.graphics.Color;


public class Ship {

    public enum ShipTypes {
        CARRIER,
        BATTLESHIP,
        CRUISER,
        SUBMARINE,
        DESTROYER,
        NONE
    }

    private static final int carrierColour = Color.parseColor("#FFF57F17");
    private static final int battleShipColour = Color.parseColor("#FF1B5E20");
    private static final int cruiserColour = Color.parseColor("#FF00C853");
    private static final int submarineColour = Color.parseColor("#FF7C4DFF");
    private static final int destroyerColour = Color.parseColor("#FFC51162");
    public static final int DestroyedColour = Color.parseColor("#FFDD2C00");


    private final int size;
    private final direction.directions direction;
    private final int color;
    private final ShipTypes shipType;
    private int liveParts;

    /**
     * @param size  Size of the ship
     * @param color Color of the ship
     */

    public Ship(int size, int color, direction.directions direction, ShipTypes shipType) {
        this.shipType = shipType;
        this.color = color;
        this.size = size;
        this.liveParts = size;
        this.direction = direction;

    }

    public ShipTypes getShipType() {
        return shipType;
    }


    public static Ship Carrier(direction.directions direction) {
        return new Ship(5, carrierColour, direction, ShipTypes.CARRIER);
    }

    public static Ship Battleship(direction.directions direction) {
        return new Ship(4, battleShipColour, direction, ShipTypes.BATTLESHIP);
    }

    public static Ship Cruiser(direction.directions direction) {
        return new Ship(3, cruiserColour, direction, ShipTypes.CRUISER);
    }

    public static Ship Submarine(direction.directions direction) {
        return new Ship(3, submarineColour, direction, ShipTypes.SUBMARINE);
    }

    public static Ship Destroyer(direction.directions direction) {
        return new Ship(2, destroyerColour, direction, ShipTypes.DESTROYER);
    }


    public int getSize() {
        return size;
    }

    public int getLiveParts() {
        return liveParts;
    }

    public void destroyPart() {
        this.liveParts = this.liveParts - 1;
    }

    public direction.directions getdirection() {
        return direction;
    }

    public int getColor() {
        return color;
    }

    public Boolean isLive() {
        return getLiveParts() > 0;
    }

}
