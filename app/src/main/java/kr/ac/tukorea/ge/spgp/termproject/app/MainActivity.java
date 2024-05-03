package kr.ac.tukorea.ge.spgp.termproject.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp.termproject.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.termproject.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onBtnStartGame(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }
}