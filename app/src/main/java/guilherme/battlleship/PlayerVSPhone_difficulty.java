package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayerVSPhone_difficulty extends AppCompatActivity {

    private static final String TAG = "Difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_v_s_phone_difficulty);
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
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);

        startActivity(intent);

    }

}