package guilherme.battlleship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
        String points = getIntent().getStringExtra("points");

        TextView winnerView = (TextView) findViewById(R.id.winner_text);
        TextView pointsView = (TextView) findViewById(R.id.points_text);

        winnerView.setText(winnerText);
        pointsView.setText(points);

    }

    private void goToMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}