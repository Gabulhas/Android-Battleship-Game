package guilherme.battlleship.GameLogic;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

import guilherme.battlleship.utils;

/**
 * easy - totally random
 * medium - random but next play selects random point near a ship destruction
 * hard - plays on checkerboard (increases change of finding a ship) and tries to destroy the ship before trying to locate the next one
 */
public class ComputerPlayer {

    private Difficulty difficulty;
    private Point foundBoat;
    private int checkerBoardPattern = 0;

    //crawl: when the PC finds a ship and tries to finish it off, so it "crawls" since the starting point UP, DOWN, LEFT, RIGHT
    private Point lastCrawl;
    private direction.directions lastCrawlDirection;
    private ArrayList<direction.directions> crawledDirections;

    public ComputerPlayer(String difficulty) {

        if (difficulty.equals("easy")) {
            this.difficulty = Difficulty.EASY;
        } else if (difficulty.equals("medium")) {
            this.difficulty = Difficulty.MEDIUM;
        } else if (difficulty.equals("hard")) {
            this.difficulty = Difficulty.HARD;
        }
    }

    public boolean play(PlayerBoard enemyBoard) {
        switch (difficulty) {

            case EASY:
                return playEasy(enemyBoard);
            case MEDIUM:
                return playMedium(enemyBoard);
            case HARD:
                return playHard(enemyBoard);
        }
        return false;
    }

    private Spot randomSpot(PlayerBoard enemyBoard) {
        Point rp = utils.randomPoint(0, 9, 0, 9);
        Spot spot = enemyBoard.getSpotOnPoint(rp);

        //checks if the spot was already shot
        while (!spot.isLive()) {
            Log.d("RANDOM_ATTACK", "randomSpot: " + spot.toString());
            rp = utils.randomPoint(0, 9, 0, 9);
            spot = enemyBoard.getSpotOnPoint(rp);
        }
        return spot;

    }

    private boolean playEasy(PlayerBoard enemyBoard) {
        Spot spot = randomSpot(enemyBoard);
        Log.d("EASY_ATTACK", "randomSpot: " + spot.toString());
        return enemyBoard.attackSpot(spot.getPoint().x, spot.getPoint().y);

    }

    private boolean playMedium(PlayerBoard enemyBoard) {
        boolean destroyedShip = false;

        if (foundBoat == null) {
            Spot spot = randomSpot(enemyBoard);
            destroyedShip = enemyBoard.attackSpot(spot.getPoint().x, spot.getPoint().y);
            if (spot.isContainsShip()) {
                this.foundBoat = spot.getPoint();
            }
            Log.d("NEW_POINT", "playMedium: " + foundBoat);
        } else {

            int[][] possiblePoints = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            boolean itCouldAttack = false;

            for (int i = 0; i < possiblePoints.length; i++) {
                Point nextPoint = new Point(foundBoat.x + possiblePoints[i][0], foundBoat.y + possiblePoints[i][1]);
                if (enemyBoard.isInBoard(nextPoint) && enemyBoard.getSpotOnPoint(nextPoint).isLive()) {
                    destroyedShip = enemyBoard.attackSpot(nextPoint.x, nextPoint.y);
                    itCouldAttack = true;
                    break;
                }
            }
            foundBoat = null;
            //in case of neither the points around are available or got already destroyed
            if (!itCouldAttack) {
                return playMedium(enemyBoard);
            }
        }
        return destroyedShip;

    }


    /*
    Full explanation on why I'm using a Checkerboard pattern to find a ship.
    Since **ALL** ships have 2 or more parts, if we play on checkerboard, lets say, as an example
    we play on Odd X and Even Y points, even the smallest ship (size 2) will be included in that
    checkerboard, which means that instead of playing with 100 spots we play with 50, basically
    duplicating our chances of finding a ship


     */
    private Point generateRandomPointCheckerBoard() {
        int[][] numbers = {{0, 2, 4, 6, 8}, {1, 3, 5, 7, 9}};


        int fn = utils.randomInt(0, 9);
        int ln;

        if (this.checkerBoardPattern == 1) {

            if (fn % 2 == 0) {
                ln = numbers[0][utils.randomInt(1, 4)];
            } else {
                ln = numbers[1][utils.randomInt(1, 4)];
            }

        } else {
            if (fn % 2 == 0) {
                ln = numbers[1][utils.randomInt(1, 4)];
            } else {
                ln = numbers[0][utils.randomInt(1, 4)];
            }
        }

        return new Point(fn, ln);


    }

    private Spot randomSpotCheckerBoard(PlayerBoard enemyBoard) {
        Spot spot = enemyBoard.getSpotOnPoint(generateRandomPointCheckerBoard());
        while (!spot.isLive()) {
            spot = enemyBoard.getSpotOnPoint(generateRandomPointCheckerBoard());
        }
        return spot;
    }

    private boolean playHard(PlayerBoard enemyBoard) {
        boolean destroyedShip = false;

        if (foundBoat == null) {
            Spot spot = randomSpotCheckerBoard(enemyBoard);
            destroyedShip = enemyBoard.attackSpot(spot.getPoint().x, spot.getPoint().y);
            if (spot.isContainsShip()) {
                this.foundBoat = spot.getPoint();
            }
        } else {
            if (this.lastCrawlDirection == null) {
                direction.directions dir = direction.randomDirection();

            }
            // randomly selects a point around the found boat
            Spot spot = enemyBoard.getSpotOnPoint(direction.pointAndDirection(this.foundBoat, this.lastCrawlDirection));
            destroyedShip = enemyBoard.attackSpot(spot.getPoint().x, spot.getPoint().y);
            if (spot.isContainsShip()) {
                this.foundBoat = spot.getPoint();
            }
            this.crawledDirections.add(this.lastCrawlDirection);

            this.crawledDirections.add(this.lastCrawlDirection);




        }

        return destroyedShip;

    }


    private enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
}
