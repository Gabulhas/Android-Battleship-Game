package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startGamePhone(View view) {
        Intent startGame = new Intent(this, PlayerVSPhone_difficulty.class);
        startActivity(startGame);
    }

    public void multiplayerGame(View view){
        Intent startGame = new Intent(this, GameActivity.class);
        startGame.putExtra("difficulty", "multiplayer");
        startActivity(startGame);

    }
}