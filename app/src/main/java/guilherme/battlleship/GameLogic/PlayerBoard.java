package guilherme.battlleship.GameLogic;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/***
 * Board of a player
 */
public class PlayerBoard {
    /*
    player is the player name
    plays is the number of times that the player with this board played
    playerShips contains information of which ships are alive
    myBoard is the board that we have our ships where the other playern will attack
     */
    private String player;
    private int plays = 0;
    private ArrayList<Ship> playerShips = new ArrayList<>();
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
                    this.myBoard.get(i).set(point.y, new Spot(i, point.y, ship));
                }

                break;
            case LEFT:
                for (int i = point.x; i > point.x - size; i--) {
                    this.myBoard.get(i).set(point.y, new Spot(i, point.y, ship));
                }
                break;
            case DOWN:
                for (int i = point.y; i < point.y + size; i++) {
                    this.myBoard.get(point.x).set(i, new Spot(point.x, i, ship));
                }
                break;
            case UP:
                for (int i = point.y; i > point.y - size; i--) {
                    this.myBoard.get(point.x).set(i, new Spot(point.x, i, ship));
                }
                break;
        }
        this.playerShips.add(ship);
    }

    /* We check if all the positions of a ship are:
        - Inside the board
        - there's no other ship where we want to place the parts

     */
    private Boolean checkPlacement(Point point, int size, direction.directions direction) {
        Log.d("testing", "placeShip: " + point + " " + size + " " + direction);
        switch (direction) {
            case RIGHT:
                Log.d("switch testing", direction + "");
                if (point.x + size > 9) {
                    return false;
                }
                for (int i = point.x; i < point.x + size; i++) {
                    if (this.myBoard.get(i).get(point.y).isContainsShip()) {
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
                    if (this.myBoard.get(i).get(point.y).isContainsShip()) {
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
                    if (this.myBoard.get(point.x).get(i).isContainsShip()) {
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
                    if (this.myBoard.get(point.x).get(i).isContainsShip()) {
                        return false;
                    }
                }
                break;
        }

        return true;
    }

    public ArrayList<Ship> getPlayerShips() {
        return playerShips;
    }


    public Boolean isInBoard(Point point) {
        int x = point.x;
        int y = point.y;
        return (x <= 9) && (x >= 0) && (y <= 9) && (y >= 0);
    }

    public Spot getSpotOnPoint(int x, int y) {
        return this.myBoard.get(x).get(y);
    }

    public Spot getSpotOnPoint(Point point) {
        return this.myBoard.get(point.x).get(point.y);
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

    public void destroyShip(Ship ship) {

        for (int i = 0; i < this.playerShips.size(); i++) {
            Ship myShip = this.playerShips.get(i);
            if (myShip.getShipType().equals(ship.getShipType())) {
                this.playerShips.remove(i);
                return;
            }
        }
    }

    /*
    We can think of this function like if someone else was playing with us and the other player placed
    an attack and we had to place our markers in our bottom board
     */
    public boolean attackSpot(int x, int y) {
        Spot attackedSpot = this.myBoard.get(x).get(y);
        Log.d("ATTACK", "attackSpot: Attacking " + this.player + " on " + attackedSpot);
        attackedSpot.destroy();

        if (attackedSpot.isContainsShip()) {
            Ship ship = getShipOnSpot(attackedSpot);
            ship.destroyPart();
            Log.d("SHIP", "SHIP" + ship.getLiveParts());
            if (!ship.isLive()) {
                destroyShip(ship);
                return true;
            }

        }
        return false;

    }

}
