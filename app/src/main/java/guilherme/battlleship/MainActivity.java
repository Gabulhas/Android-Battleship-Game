package guilherme.battlleship;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences oSP = getPreferences(MODE_PRIVATE);
        this.playerName = oSP.getString("playername", "NULL");
        if (playerName.equals("NULL")) {
            pedirNome(null);
        }
        changeNameTV();

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
                if (playerName.equals("NULL")) {
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
        SharedPreferences oSP = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor oEditor = oSP.edit();
        oEditor.putString("playername", name);
        oEditor.apply();
        changeNameTV();


    }
}