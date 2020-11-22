package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import guilherme.battlleship.GameLogic.ComputerPlayer;
import guilherme.battlleship.GameLogic.PlayerBoard;
import guilherme.battlleship.GameLogic.Spot;
import guilherme.battlleship.GameLogic.utils;

public class GameActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private PlayerBoard playerBoard; //or 1st player
    private PlayerBoard enemyBoard; //or 2nd player
    private GridLayout play_board; //Board that allows the Player to attack
    private GridLayout my_board; //Board that displays Playing Player's board
    private ComputerPlayer computerPlayer;
    private String playerName;
    private boolean playerOneTurn = true;
    private boolean playerVsComputer;
    private boolean canPlayerClick = true;
    private int plays = 0;
    private Long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("difficulty");

        play_board = findViewById(R.id.play_board);
        my_board = findViewById(R.id.my_board);

        //Player vs Computer Game
        if (!difficulty.equals("multiplayer")) {

            this.startTime = System.currentTimeMillis() / 1000;

            setTitle("Player VS Computer - " + difficulty.toUpperCase());
            this.computerPlayer = new ComputerPlayer(difficulty);
            this.playerVsComputer = true;
            SharedPreferences oSP = getPreferences(MODE_PRIVATE);
            String playerName = oSP.getString("playername", "NULL");


            playerBoard = new PlayerBoard(playerName);
            enemyBoard = new PlayerBoard("Enemy");
        } else {
            String playerName = "Player 1";

            setTitle(playerName);
            this.playerVsComputer = false;
            TextView player_name = (TextView) findViewById(R.id.player_name);
            player_name.setText(playerName);
            player_name.setVisibility(View.VISIBLE);

            playerBoard = new PlayerBoard("Player 1");
            enemyBoard = new PlayerBoard("Player 2");
        }


        renderBoard(play_board, true, enemyBoard.getMyBoard());
        renderBoard(my_board, false, playerBoard.getMyBoard());

    }

    //this draws the game board in the screen from a set of spots
    public void renderBoard(GridLayout board, Boolean active, ArrayList<ArrayList<Spot>> gameBoard) {

        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {

                GridLayout.LayoutParams param = new GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),
                        GridLayout.spec(GridLayout.UNDEFINED, 1f)
                );

                param.width = 0;
                param.height = 0;

                Spot boardSpot = gameBoard.get(i).get(j);


                TextView spot = new TextView(this);
                spot.setTag(i + "," + j);
                spot.setBackground(boardSpot.getShape());
                if (active) {

                    if (boardSpot.isLive()) {
                        spot.setBackground(Spot.neutralShape());
                    } else {
                        spot.setBackground(boardSpot.getShape());
                    }

                    // This handle the attack
                    spot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!boardSpot.isLive()) {
                                Log.d("ATTACK", "onClick: ded");
                                utils.sendToast("This spot was already shot.", getApplicationContext());
                                return;
                            }
                            if (!canPlayerClick) {
                                return;
                            }
                            String pos = v.getTag().toString();
                            Log.d("ATTACK", "onClick: " + pos);
                            String[] posCords = pos.split(",");
                            playerPlay(Integer.parseInt(posCords[0]), Integer.parseInt(posCords[1]));
                            v.setBackground(boardSpot.getShape());
                        }
                    });

                } else {
                    spot.setBackground(boardSpot.getShape());

                }
                board.addView(spot, param);
            }
        }


    }


    //this makes so you have to double click the back button to quit the game
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.press_twice), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    private void endGame(String winnerText, String player, int points, int plays, int left_ships, String difficulty, long time) {
        Intent intent = new Intent(this, EndGame.class);
        intent.putExtra("winnerText", winnerText);
        intent.putExtra("points", points);
        intent.putExtra("plays", plays);
        intent.putExtra("player", player);
        intent.putExtra("left_ships", left_ships);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("time", time);
        startActivity(intent);
    }

    private void endGame(String winnerText, String player, int points, int plays, int left_ships) {
        Intent intent = new Intent(this, EndGame.class);
        intent.putExtra("winnerText", winnerText);
        intent.putExtra("points", points);
        intent.putExtra("plays", plays);
        intent.putExtra("player", player);
        intent.putExtra("left_ships", left_ships);
        startActivity(intent);
    }


    /*
    Points are calculated this way:
    Since this game there's no direct fight with the opponent, I had to come up with a point system
    that only depends on the Human player plays,

    This is:
    The hit rate (rate of attacks that hit a ship part, in total there are 17)
                                 X
    The inverse of time in seconds that took to finish the game, so, the faster a player finish the game
    the more points it gets


    This is

    /--------------------/
    minimum_plays is the minimum plays required to finish the game, giving you 100 points if you get lucky ;)
    maximum_points is obvious, you get this points if the amount of plays that took you to win was 17

    We get (minimum_plays * maximum_points) / this.plays = Points for plays

    /-------------------/

    Time multiplier is calculated like:

    (1/time to finish)

    the    * 10  only exists to make the points look pretier :)

    /-------------------/

    final formula =  ((minimum_plays * maximum_points) / this.plays) * ((1/time to finish) * 10)


     */
    private int calculate_points(long time) {

        //just in case.
        if (time == 0) {
            time = 1;
        }


        float minimum_plays = 17;
        float maximum_points = 100;
        float time_float = (float) time;
        float time_multiplier = (1 / time_float) * 10;


        return (int) (((minimum_plays * maximum_points) / this.plays) * time_multiplier) * 10;

    }

    //PLAYER VS COMPUTER
    /*
    The player plays and right after the Computer player
     */
    private void playVsComputer(int x, int y) {

        this.plays += 1;

        if (enemyBoard.attackSpot(x, y)) {


            /*
            If the enemy has no boats left we win
             */
            if (enemyBoard.getPlayerShips().size() == 0) {
                //PLAYER WON
                //EXIT HERE
                utils.sendToast("YOU WON", getApplicationContext());
                long time = (System.currentTimeMillis() / 1000) - startTime;
                endGame("YOU WON", this.playerName, calculate_points(time), this.plays, playerBoard.getPlayerShips().size(), computerPlayer.getDifficulty(), time);
                return;
            }
            utils.sendToast("Destroyed a ship. " + enemyBoard.getPlayerShips().size() + " left.", getApplicationContext());
        }

            /*
            If the we have no boats left we lose
             */
        if (computerPlayer.play(playerBoard)) {
            if (playerBoard.getPlayerShips().size() == 0) {
                utils.sendToast("YOU LOST", getApplicationContext());
                endGame("YOU LOST", this.playerName, -1, this.plays, enemyBoard.getPlayerShips().size(), computerPlayer.getDifficulty(), 0);
                return;
            }
            utils.sendToast("One of your ships was destroyed", getApplicationContext());

        }
    }


    //PLAYER VS PLAYER

    /*

    This part does the following:

    Temporarly hide the board
    Shows the "Swap players" text
    Cleans the boards
    Swaps the boards
     */


    private void swapBoards() {
        this.play_board.setVisibility(View.INVISIBLE);
        this.my_board.setVisibility(View.INVISIBLE);
        TextView player_name = (TextView) findViewById(R.id.player_name);
        player_name.setVisibility(View.INVISIBLE);

        findViewById(R.id.my_board_text).setVisibility(View.INVISIBLE);
        findViewById(R.id.attack_board_text).setVisibility(View.INVISIBLE);
        findViewById(R.id.swap_message).setVisibility(View.VISIBLE);

        this.play_board.removeAllViews();
        this.my_board.removeAllViews();
        String playerName;
        if (!playerOneTurn) {
            playerName = "Player 2";
            renderBoard(play_board, true, playerBoard.getMyBoard());
            renderBoard(my_board, false, enemyBoard.getMyBoard());
        } else {
            playerName = "Player 1";
            renderBoard(play_board, true, enemyBoard.getMyBoard());
            renderBoard(my_board, false, playerBoard.getMyBoard());
        }
        setTitle(playerName);
        player_name.setText(playerName);
        showBoards();


    }


    /*
    This one Hides the Swap text and shows the new boards
     */

    private void showBoards() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                play_board.setVisibility(View.VISIBLE);
                my_board.setVisibility(View.VISIBLE);
                findViewById(R.id.player_name).setVisibility(View.VISIBLE);
                findViewById(R.id.my_board_text).setVisibility(View.VISIBLE);
                findViewById(R.id.attack_board_text).setVisibility(View.VISIBLE);
                findViewById(R.id.swap_message).setVisibility(View.INVISIBLE);

            }
        }, 3000);
    }


    /*
    this handles players vs players
     */
    private void playVsPlayer(int x, int y) {

        if (playerOneTurn) {
            if (enemyBoard.attackSpot(x, y)) {
                if (enemyBoard.getPlayerShips().size() == 0) {
                    //PLAYER ONE WON
                    //EXIT HERE
                    utils.sendToast("Player 1 WON!", getApplicationContext());

                    endGame("Player 1 WON!", "Player 1", -1, this.plays, playerBoard.getPlayerShips().size());
                    return;
                }
                utils.sendToast("Destroyed a ship. " + enemyBoard.getPlayerShips().size() + " left.", getApplicationContext());
            }
            this.playerOneTurn = false;

        } else {
            if (playerBoard.attackSpot(x, y)) {
                if (playerBoard.getPlayerShips().size() == 0) {
                    //PLAYER ONE WON
                    //EXIT HERE
                    utils.sendToast("Player 2 WON!", getApplicationContext());

                    endGame("Player 2 WON!", "Player 2", -1, this.plays, enemyBoard.getPlayerShips().size());
                    return;
                }
                utils.sendToast("Destroyed a ship. " + playerBoard.getPlayerShips().size() + " left.", getApplicationContext());
            }
            this.playerOneTurn = true;

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swapBoards();
                canPlayerClick = true;

            }
        }, 1000);


    }


    /*
    this handles if it's a PVP scenario or a PVC scenario
     */

    private void playerPlay(int x, int y) {
        if (this.playerVsComputer) {
            playVsComputer(x, y);
            return;
        }
        canPlayerClick = false;
        playVsPlayer(x, y);
    }

}