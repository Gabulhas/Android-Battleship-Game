package guilherme.battlleship.GameLogic;

import android.graphics.Color;


public class Ship {

    private int size;
    private int liveParts;
    private Spot[] parts;
    private Color color;
    private Boolean isLive;

    /**
     *
     * @param size Size of the ship
     * @param color Color of the ship
     * @param headX X coordinate of the head.
     * @param headY Y coordinate of the head.
     *
     */

    public Ship(int size, Color color, int headX, int headY, String tailDirection){
        this.color = color;
        this.size = size;
        this.liveParts = size;

    }

}
