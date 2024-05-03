package kr.ac.tukorea.ge.spgp.termproject;

import android.os.Bundle;
import android.util.AttributeSet;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AttributeSet attr = null;
        GameView gameView = new GameView(this, attr);

        setContentView(R.layout.activity_game);
        //setContentView(gameView);

        new MainScene().push();
    }
}