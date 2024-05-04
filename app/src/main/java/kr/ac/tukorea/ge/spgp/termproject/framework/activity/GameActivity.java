package kr.ac.tukorea.ge.spgp.termproject.framework.activity;

import android.os.Bundle;
import android.util.AttributeSet;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp.termproject.framework.view.GameView;
import kr.ac.tukorea.ge.spgp.termproject.game.MainScene;

public class GameActivity extends AppCompatActivity {
    public static GameActivity activity;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        AttributeSet attr = null;
        gameView = new GameView(this);

        setContentView(gameView);
        new MainScene().push();
        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            gameView.onBackPressed();
        }
    };

    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        gameView.destroyGame();
        super.onDestroy();
    }
}