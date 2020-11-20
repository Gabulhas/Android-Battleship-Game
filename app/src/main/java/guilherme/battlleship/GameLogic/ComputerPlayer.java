package guilherme.battlleship.GameLogic;

import android.graphics.Point;
import android.util.Log;

import java.util.Stack;

/**
 * easy - totally random
 * medium - random but next play selects random point near a ship destruction
 * hard - plays on checkerboard (increases change of finding a ship) and tries to destroy the ship before trying to locate the next one
 */
public class ComputerPlayer {

    private Difficulty difficulty;
    private Point foundBoat;
    private Stack<Point> foundBoatParts = new Stack<>();

    private int[][] possiblePoints = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};


    private int checkerBoardPattern = 0;

    //crawl: when the PC finds a ship and tries to finish it off, so it "crawls" since the starting point UP, DOWN, LEFT, RIGHT


    public ComputerPlayer(String difficulty) {
        Point point = new Point();

        if (difficulty.equals("easy")) {
            this.difficulty = Difficulty.EASY;
        } else if (difficulty.equals("medium")) {
            this.difficulty = Difficulty.MEDIUM;
        } else if (difficulty.equals("hard")) {
            this.difficulty = Difficulty.HARD;
        }
    }


    public String getDifficulty() {

        switch (difficulty) {

            case EASY:
                return "easy";
            case MEDIUM:
                return "medium";
            case HARD:
                return "hard";
        }

        return "easy";
    }

    public boolean play(PlayerBoard enemyBoard) {
        switch (difficulty) {

            case EASY:
                return playEasy(enemyBoard);
            case MEDIUM:
                return playMedium(enemyBoard);
            case HARD:
                Log.d("STACK", "play: There are " + this.foundBoatParts.size() + " Points in the stack");
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

            boolean itCouldAttack = false;

            for (int i = 0; i < possiblePoints.length; i++) {
                Point nextPoint = new Point(foundBoat.x + possiblePoints[i][0], foundBoat.y + possiblePoints[i][1]);
                if (enemyBoard.isInBoard(nextPoint) && enemyBoard.getSpotOnPoint(nextPoint).isLive()) {
                    destroyedShip = enemyBoard.attackSpot(nextPoint.x, nextPoint.y);
                    itCouldAttack = true;
                    //if it attacked the ship
                    if (enemyBoard.getSpotOnPoint(nextPoint.x, nextPoint.y).isContainsShip()) {
                        this.foundBoat = nextPoint;
                    }
                    break;
                }
            }
            //in case of neither the points around are available or got already destroyed
            if (!itCouldAttack) {
                foundBoat = null;
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
                ln = numbers[0][utils.randomInt(0, 4)];
            } else {
                ln = numbers[1][utils.randomInt(0, 4)];
            }

        } else {
            if (fn % 2 == 0) {
                ln = numbers[1][utils.randomInt(0, 4)];
            } else {
                ln = numbers[0][utils.randomInt(0, 4)];
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

    //plays on checkerboard like patter
    //crawls near destroyed attacked ships
    private boolean playHard(PlayerBoard enemyBoard) {
        boolean destroyedShip = false;
        Log.d("PPS", "playHard: Possible points" + utils.debugPoints(this.possiblePoints));

        if (this.foundBoatParts.isEmpty()) {
            Spot spot = randomSpotCheckerBoard(enemyBoard);
            destroyedShip = enemyBoard.attackSpot(spot.getPoint().x, spot.getPoint().y);
            if (spot.isContainsShip()) {
                this.foundBoatParts.push(spot.getPoint());
            }
            Log.d("NEW_POINT", "playMedium: " + foundBoat);
        } else {

            boolean itCouldAttack = false;
            //gets the first on the stack, AKA latest part found
            foundBoat = this.foundBoatParts.peek();

            for (int i = 0; i < possiblePoints.length; i++) {
                Point nextPoint = new Point(foundBoat.x + possiblePoints[i][0], foundBoat.y + possiblePoints[i][1]);
                if (enemyBoard.isInBoard(nextPoint) && enemyBoard.getSpotOnPoint(nextPoint).isLive()) {
                    destroyedShip = enemyBoard.attackSpot(nextPoint.x, nextPoint.y);
                    itCouldAttack = true;
                    if (destroyedShip) {
                        Point first = this.foundBoatParts.firstElement();
                        this.foundBoatParts.clear();
                        this.foundBoatParts.push(first);
                        break;
                    }

                    //if it attacked a ship
                    if (enemyBoard.getSpotOnPoint(nextPoint.x, nextPoint.y).isContainsShip()) {
                        this.foundBoatParts.push(nextPoint);
                        // prioritize "good direction", since boats are straight
                        this.possiblePoints = utils.moveToStart(this.possiblePoints, i);
                        break;
                    } else {
                        Point first = this.foundBoatParts.firstElement();
                        this.foundBoatParts.clear();
                        this.foundBoatParts.push(first);
                    }
                    break;
                }
            }
            //in case of neither the points around are available or got already destroyed
            if (!itCouldAttack) {
                this.possiblePoints = utils.shufflePossiblePoints(this.possiblePoints);
                this.foundBoatParts.pop();
                return playHard(enemyBoard);
            }
        }
        return destroyedShip;
    }


    private enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
}
