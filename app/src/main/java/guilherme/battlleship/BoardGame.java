package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BoardGame extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game);
        Intent intent = getIntent();

        GridLayout play_board = findViewById(R.id.play_board);
        GridLayout my_board = findViewById(R.id.my_board);
        setTitle("Player");

        fillBoard(play_board, true);
        fillBoard(my_board, false);

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

    public void fillBoard(GridLayout board, Boolean active) {
        GradientDrawable shape = new GradientDrawable();

        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.argb(255, 185, 226, 235));

        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {

                //para todos os spots estarem dentro do GridLayout com o mesmo tamanho
                GridLayout.LayoutParams param = new GridLayout.LayoutParams(
                        GridLayout.spec(GridLayout.UNDEFINED, 1f),
                        GridLayout.spec(GridLayout.UNDEFINED, 1f)
                );
                param.width = 0;
                param.height = 0;

                TextView spot = new TextView(this);
                GradientDrawable tempShape = shape;
                spot.setTag(i + "," + j);

                tempShape.setStroke(2, Color.argb(255, 255, 255, 255));
                spot.setBackground(tempShape);


                if (active) {
                    spot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            spotPressed(v);
                        }
                    });

                }
                board.addView(spot, param);
            }
        }


    }

    public void spotPressed(View v) {
        v.setBackgroundColor(Color.RED);

    }
}