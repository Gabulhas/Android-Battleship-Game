package guilherme.battlleship;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import guilherme.battlleship.other.database_connection;

public class EndGame extends AppCompatActivity {

    private database_connection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = new database_connection(this);

        String winnerText = getIntent().getStringExtra("winnerText");
        String difficulty = getIntent().getStringExtra("difficulty");
        int points = getIntent().getIntExtra("points", -1);
        int plays = getIntent().getIntExtra("plays", -1);
        int left_ships = getIntent().getIntExtra("left_ships", -1);
        long time = getIntent().getLongExtra("time", -1);

        TextView winnerView = (TextView) findViewById(R.id.winner_text);
        TextView pointsView = (TextView) findViewById(R.id.points_text);
        TextView statsView = (TextView) findViewById(R.id.other_stats);

        winnerView.setText(winnerText);
        if (points != -1) {
            pointsView.setText(String.format("%s: %d", getString(R.string.points_string), points));
            if (!difficulty.equals("NULL")) {

                SharedPreferences oSP = getSharedPreferences("settings", Context.MODE_PRIVATE);
                String playerName = oSP.getString("playername", "NULL");
                AppCompatImageButton shareButton = (AppCompatImageButton) findViewById(R.id.share_button);
                shareButton.setVisibility(View.VISIBLE);

                statsView.setText(String.format("%s: %s\n%s: %d\n%s: %d\n%s: %d%s\n%s: %d", getString(R.string.stats_difficulty), difficulty, getString(R.string.stats_plays_string), plays, getString(R.string.stas_ships_left_string), left_ships, getString(R.string.stats_hit_rate), ((17 * 100) / plays), "%", getString(R.string.stats_time), time));
                saveToDB(playerName, points, plays, left_ships, difficulty, time);
            }
        } else {
            pointsView.setText(" ");
            statsView.setText(String.format("%s: %d\n%s: %d\n%s: %d%s", getString(R.string.stats_plays_string), plays, getString(R.string.stas_ships_left_string), left_ships, getString(R.string.stats_hit_rate), ((17 * 100) / plays), "%"));
        }


    }

    public void share(View view) {

        String difficulty = getIntent().getStringExtra("difficulty");
        int points = getIntent().getIntExtra("points", -1);
        int plays = getIntent().getIntExtra("plays", -1);
        int left_ships = getIntent().getIntExtra("left_ships", -1);
        long time = getIntent().getLongExtra("time", -1);
        SharedPreferences oSP = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String playerName = oSP.getString("playername", "NULL");


        String sending_string = String.format("I won the Battleship Game!\n\nMY STATS\n%s: %d\n%s: %s\n%s: %d\n%s: %d\n%s: %d%s\n%s: %d", getString(R.string.points_string), points, getString(R.string.stats_difficulty), difficulty, getString(R.string.stats_plays_string), plays, getString(R.string.stas_ships_left_string), left_ships, getString(R.string.stats_hit_rate), ((17 * 100) / plays), "%", getString(R.string.stats_time), time);

        Log.d("SHARING", "share: " + sending_string);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sending_string);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMainActivity(null);
    }

    public void goToMainActivity(@Nullable View view) {


        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

    private void saveToDB(String player, int points, int plays, int left_ships, String difficulty, long time) {
        Log.d("STDB", "saveToDB: " + " " + player + " " + points + " " + plays + " " + left_ships + " " + time);
        db.insertScore(player, points, plays, left_ships, difficulty, time);
    }

}