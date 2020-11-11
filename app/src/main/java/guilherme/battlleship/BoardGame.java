package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import guilherme.battlleship.GameLogic.GameBoard;
import guilherme.battlleship.GameLogic.Spot;

public class BoardGame extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    GameBoard playerBoard;
    GameBoard enemyBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Player");
        setContentView(R.layout.activity_board_game);

        GridLayout play_board = findViewById(R.id.play_board);
        GridLayout my_board = findViewById(R.id.my_board);


        playerBoard = new GameBoard("Player");
        enemyBoard = new GameBoard("Enemy");
        Spot forTesting = new Spot(2, 3, 0, Color.parseColor("#651FFF"), Color.parseColor("#d50000"));

        enemyBoard.getMyBoard().get(2).set(3, forTesting);
        renderBoard(play_board, true, enemyBoard.getMyBoard());
        renderBoard(my_board, false, playerBoard.getMyBoard());

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

    public void renderBoard(GridLayout board, Boolean active, ArrayList<ArrayList<Spot>> gameBoard) {
        //para todos os spots estarem dentro do GridLayout com o mesmo tamanho

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

                Log.d("noTag", "fillBoard: " + boardSpot.getCurrentColor());

                spot.setBackground(boardSpot.getShape());


                if (active) {
                    spot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!boardSpot.isLive()) {
                                Log.d("ATTACK", "onClick: ded");
                                return;
                            }
                            String pos = v.getTag().toString();
                            Log.d("ATTACK", "onClick: " + pos);
                            String[] posCords = pos.split(",");
                            playerPlay(Integer.parseInt(posCords[0]), Integer.parseInt(posCords[1]));
                            v.setBackground(boardSpot.getShape());
                        }
                    });

                }
                board.addView(spot, param);
            }
        }


    }

    private void playerPlay(int x, int y) {
        enemyBoard.attackSpot(x, y);

    }

}