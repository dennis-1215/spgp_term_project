package kr.ac.tukorea.ge.spgp.termproject;

import android.view.MotionEvent;

import java.util.Random;

public class ChoiceScene extends Scene {
    private static final String TAG = ChoiceScene.class.getSimpleName();
    private float time;
    private final Random random = new Random();

    public ChoiceScene() {
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Scene.pop();
            return true;
        }
        return false;
    }
}
