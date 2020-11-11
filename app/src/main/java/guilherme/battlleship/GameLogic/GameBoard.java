package guilherme.battlleship.GameLogic;

import android.graphics.Color;

import java.util.ArrayList;

/***
 * Board of a player
 */
public class GameBoard {
    private String player;
    private Ship[] playerShips;
    private ArrayList<ArrayList<Spot>> myBoard;

    public GameBoard(String player) {
        this.player = player;
        this.myBoard = initBoard();

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

    public void attackSpot(int x, int y) {
        this.myBoard.get(x).get(y).destroy();
    }

}
