package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import guilherme.battlleship.GameLogic.ComputerPlayer;
import guilherme.battlleship.GameLogic.PlayerBoard;
import guilherme.battlleship.GameLogic.Spot;

public class GameActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;
    private PlayerBoard playerBoard; //or 1st player
    private PlayerBoard enemyBoard; //or 2nd player
    private GridLayout play_board; //Board that allows the Player to attack
    private GridLayout my_board; //Board that displays Playing Player's board
    private ComputerPlayer computerPlayer;
    private boolean playerOneTurn = true;
    private boolean playerVsComputer;
    private boolean canPlayerClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("difficulty");

        play_board = findViewById(R.id.play_board);
        my_board = findViewById(R.id.my_board);

        //Player vs Computer Game
        if (!difficulty.equals("multiplayer")) {
            setTitle("Player VS Computer -" + difficulty);
            this.computerPlayer = new ComputerPlayer(difficulty);
            this.playerVsComputer = true;
            playerBoard = new PlayerBoard("Player");
            enemyBoard = new PlayerBoard("Enemy");
        } else {

            setTitle("Player 1");
            this.playerVsComputer = false;

            playerBoard = new PlayerBoard("Player 1");
            enemyBoard = new PlayerBoard("Player 2");
        }


        renderBoard(play_board, true, enemyBoard.getMyBoard());
        renderBoard(my_board, false, playerBoard.getMyBoard());

    }

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


    private void endGame(String winnerText, String points) {
        Intent intent = new Intent(this, EndGame.class);
        intent.putExtra("winnerText", winnerText);
        intent.putExtra("points", points);
        startActivity(intent);
    }


    private void playVsComputer(int x, int y) {
        if (enemyBoard.attackSpot(x, y)) {
            if (enemyBoard.getPlayerShips().size() == 0) {
                //PLAYER WON
                //EXIT HERE
                utils.sendToast("YOU WON", getApplicationContext());

                endGame("YOU WON", "NOT ADDED YET");
                return;
            }
            utils.sendToast("Destroyed a ship. " + enemyBoard.getPlayerShips().size() + " left.", getApplicationContext());
        }

        if (computerPlayer.play(playerBoard)) {
            if (playerBoard.getPlayerShips().size() == 0) {
                utils.sendToast("YOU LOST", getApplicationContext());
                endGame("YOU LOST", "NOT ADDED YET");
                return;
            }
            utils.sendToast("One of your ships was destroyed", getApplicationContext());

        }
    }

    private void swapBoards() {
        this.play_board.setVisibility(View.INVISIBLE);
        this.my_board.setVisibility(View.INVISIBLE);
        findViewById(R.id.my_board_text).setVisibility(View.INVISIBLE);
        findViewById(R.id.attack_board_text).setVisibility(View.INVISIBLE);
        findViewById(R.id.swap_message).setVisibility(View.VISIBLE);

        this.play_board.removeAllViews();
        this.my_board.removeAllViews();
        if (!playerOneTurn) {
            setTitle("Player 2");
            renderBoard(play_board, true, playerBoard.getMyBoard());
            renderBoard(my_board, false, enemyBoard.getMyBoard());
        } else {
            setTitle("Player 1");
            renderBoard(play_board, true, enemyBoard.getMyBoard());
            renderBoard(my_board, false, playerBoard.getMyBoard());
        }
        showBoards();


    }


    private void showBoards() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                play_board.setVisibility(View.VISIBLE);
                my_board.setVisibility(View.VISIBLE);
                findViewById(R.id.my_board_text).setVisibility(View.VISIBLE);
                findViewById(R.id.attack_board_text).setVisibility(View.VISIBLE);
                findViewById(R.id.swap_message).setVisibility(View.INVISIBLE);

            }
        }, 3000);
    }

    private void playVsPlayer(int x, int y) {

        if (playerOneTurn) {
            if (enemyBoard.attackSpot(x, y)) {
                if (enemyBoard.getPlayerShips().size() == 0) {
                    //PLAYER ONE WON
                    //EXIT HERE
                    utils.sendToast("Player 1 WON!", getApplicationContext());

                    endGame("Player 1 WON!", "NOT ADDED YET");
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

                    endGame("Player 2 WON!", "NOT ADDED YET");
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


    private void playerPlay(int x, int y) {
        if (this.playerVsComputer) {
            playVsComputer(x, y);
            return;
        }
        canPlayerClick = false;
        playVsPlayer(x, y);
    }

}