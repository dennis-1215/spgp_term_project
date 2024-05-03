package kr.ac.tukorea.ge.spgp.termproject.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;


public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public MainScene() {
        Metrics.setGameSize(9.0f, 16.0f);

        for(int i = 0; i < 10; i++){
            add(Monster.random());
        }

        this.player = new Player();
        add(player);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                if (pts[1] < 1.0) {
                    pop();
                }
                return true;
        }
        // 서브씬 테스트용 Ctrl+Click
        if (event.getPointerCount() >= 2) {
            new ChoiceScene().push();
        }
        return false;
    }
}
