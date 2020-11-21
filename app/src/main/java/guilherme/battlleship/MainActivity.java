package guilherme.battlleship;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import guilherme.battlleship.GameLogic.utils;
import guilherme.battlleship.other.database_connection;

public class MainActivity extends AppCompatActivity {

    private String playerName;
    private database_connection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle(" ");

        db = new database_connection(this);
        SharedPreferences oSP = getSharedPreferences("settings", Context.MODE_PRIVATE);
        this.playerName = oSP.getString("playername", "NULL");
        if (playerName.equals("NULL") || playerName.equals("") || playerName.equals(" ")) {
            pedirNome(null);
        }
        changeNameTV();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    public void startGamePhone(View view) {
        Intent startGame = new Intent(this, PlayerVSPhone_difficulty.class);
        startActivity(startGame);
    }


    public void multiplayerGame(View view) {
        Intent startGame = new Intent(this, GameActivity.class);
        startGame.putExtra("difficulty", "multiplayer");
        startActivity(startGame);

    }

    public void leaderboard(View view) {

        if(db.numberOfRows() < 1){
            utils.sendToast("No Scores yet, play once.", this);
            return;
        }
        Intent leaderboard = new Intent(this, leaderboard.class);
        startActivity(leaderboard);
    }


    public void pedirNome(@Nullable View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("What's your name:");
        alert.setMessage("Name:");
        playerName = "NULL";
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                saveName(value);
                Log.d("NAME", "Name: " + value);
                return;
            }
        });
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (playerName.equals("NULL") || playerName.trim().equals("")) {
                    pedirNome(null);
                }
                return;
            }
        });

        alert.show();
    }

    private void changeNameTV() {
        TextView playerNameTV = (TextView) findViewById(R.id.player_name_main);
        String baseText = getString(R.string.playing_as);
        playerNameTV.setText(baseText + playerName);
    }

    private void saveName(String name) {
        playerName = name;
        SharedPreferences oSP = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor oEditor = oSP.edit();
        oEditor.putString("playername", name);
        oEditor.commit();
        changeNameTV();


    }
}