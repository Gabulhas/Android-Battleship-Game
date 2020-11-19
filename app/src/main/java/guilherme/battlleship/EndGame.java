package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String winnerText = getIntent().getStringExtra("winnerText");
        String player = getIntent().getStringExtra("player");
        int points = getIntent().getIntExtra("points", -1);
        int plays = getIntent().getIntExtra("plays", -1);
        int left_ships = getIntent().getIntExtra("left_ships", -1);

        TextView winnerView = (TextView) findViewById(R.id.winner_text);
        TextView pointsView = (TextView) findViewById(R.id.points_text);

        winnerView.setText(winnerText);
        pointsView.setText(points);

        saveToDB(winnerText, player, points, plays, left_ships);

    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

    private void saveToDB(String winnerText, String player, int points, int plays, int left_ships) {

        Log.d("STDB", "saveToDB: " +  winnerText+ " " +  player+ " " + points+ " " +  plays + "" + left_ships);
    }

}