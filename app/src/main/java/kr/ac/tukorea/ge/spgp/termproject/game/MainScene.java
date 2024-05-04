package kr.ac.tukorea.ge.spgp.termproject.game;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;


public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public enum Layer {
        enemy, bullet, castle, player, controller, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());


        add(Layer.castle, new Castle());
        this.player = new Player();
        add(Layer.player, player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
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
