package guilherme.battlleship.GameLogic;

import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import guilherme.battlleship.utils;

import static guilherme.battlleship.GameLogic.direction.directions.*;

/***
 * Board of a player
 */
public class PlayerBoard {
    private String player;
    private Ship[] playerShips;
    private ArrayList<ArrayList<Spot>> myBoard;

    public PlayerBoard(String player) {
        this.player = player;
        this.myBoard = initBoard();
        randomPlaceShips();

    }

    private static ArrayList<ArrayList<Spot>> initBoard() {

        ArrayList<ArrayList<Spot>> tempBoard = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tempBoard.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                tempBoard.get(i).add(new Spot(i, j));
            }
        }

        return tempBoard;
    }


    public void randomPlaceShips() {
        Random ran = new Random();


        randomPlaceShip(Ship.Battleship(direction.randomDirection()));
        randomPlaceShip(Ship.Carrier(direction.randomDirection()));
        randomPlaceShip(Ship.Destroyer(direction.randomDirection()));
        randomPlaceShip(Ship.Submarine(direction.randomDirection()));
        randomPlaceShip(Ship.Cruiser(direction.randomDirection()));

    }


    private void randomPlaceShip(Ship ship) {
        Point rp = utils.randomPoint(0, 9, 0, 9);

        while (!checkPlacement(rp, ship.getSize(), ship.getdirection())) {
            rp = utils.randomPoint(0, 9, 0, 9);
        }
        placeShip(ship, rp);
    }

    private void placeShip(Ship ship, Point point) {
        Log.d("placement", "placeShip: " + point + " " + ship.getSize() + " " + ship.getdirection());

        int size = ship.getSize();
        switch (ship.getdirection()) {
            case RIGHT:
                for (int i = point.x; i < point.x + size; i++) {
                    this.myBoard.get(point.y).set(i, new Spot(i, point.y, ship));
                }

                break;
            case LEFT:
                for (int i = point.x; i > point.x - size; i--) {
                    this.myBoard.get(point.y).set(i, new Spot(i, point.y, ship));
                }
                break;
            case DOWN:
                for (int i = point.y; i < point.y + size; i++) {
                    this.myBoard.get(i).set(point.x, new Spot(point.x, i, ship));
                }
                break;
            case UP:
                for (int i = point.y; i > point.y - size; i--) {
                    this.myBoard.get(i).set(point.x, new Spot(point.x, i, ship));
                }
                break;
        }

    }

    private Boolean checkPlacement(Point point, int size, direction.directions direction) {
        Log.d("testing", "placeShip: " + point + " " + size + " " + direction);
        size = size - 1; //because the point is also a part
        switch (direction) {
            case RIGHT:
                Log.d("switch testing", direction + "");
                if (point.x + size > 9) {
                    return false;
                }
                for (int i = point.x; i < point.x + size; i++) {
                    if (this.myBoard.get(point.y).get(i).isContainsShip()) {
                        return false;
                    }
                }
                break;
            case LEFT:
                Log.d("switch testing", direction + "");
                if (point.x - size < 0) {
                    return false;
                }
                for (int i = point.x; i > point.x - size; i--) {
                    if (this.myBoard.get(point.y).get(i).isContainsShip()) {
                        return false;
                    }
                }
                break;
            case DOWN:
                Log.d("switch testing", direction + "");
                if (point.y + size > 9) {
                    return false;
                }
                for (int i = point.y; i < point.y + size; i++) {
                    if (this.myBoard.get(i).get(point.x).isContainsShip()) {
                        return false;
                    }
                }
                break;
            case UP:
                Log.d("switch testing", direction + "");
                if (point.y - size < 0) {
                    return false;
                }
                for (int i = point.y; i > point.y - size; i--) {
                    if (this.myBoard.get(i).get(point.x).isContainsShip()) {
                        return false;
                    }
                }
                break;
        }

        return true;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Ship[] getPlayerShips() {
        return playerShips;
    }

    public void setPlayerShips(Ship[] playerShips) {
        this.playerShips = playerShips;
    }


    public ArrayList<ArrayList<Spot>> getMyBoard() {
        return myBoard;
    }

    public Ship getShipOnSpot(Spot spot) {
        for (Ship ship : playerShips) {
            if (ship.getShipType().equals(spot.getShipType())) return ship;
        }
        return null;
    }

    public void attackSpot(int x, int y) {
        Spot attackedSpot = this.myBoard.get(x).get(y);
        attackedSpot.destroy();

        if (attackedSpot.isContainsShip()) {
            getShipOnSpot(attackedSpot).destroyPart();
        }

    }

}
