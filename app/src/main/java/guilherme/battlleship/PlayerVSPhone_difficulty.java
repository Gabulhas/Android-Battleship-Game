package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class PlayerVSPhone_difficulty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_v_s_phone_difficulty);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void selectDifficulty(View view) {

        String difficulty = "";

        switch (view.getId()) {
            case R.id.easy_button:
                difficulty = "easy";
                break;
            case R.id.medium_button:
                difficulty = "medium";
                break;
            case R.id.hard_button:
                difficulty = "hard";
                break;

            default:
                difficulty = "easy";
                break;

        }

        Intent thisIntent = getIntent();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("PlayerName", thisIntent.getStringExtra("PlayerName"));

        startActivity(intent);

    }

}