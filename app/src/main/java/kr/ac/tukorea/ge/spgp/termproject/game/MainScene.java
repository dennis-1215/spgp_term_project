package kr.ac.tukorea.ge.spgp.termproject.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;


public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public enum Layer {
        bg, castle, enemy, bullet, player, controller, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());

        add(Layer.bg, new Background(R.mipmap.background));

        add(Layer.castle, new Castle());
        this.player = new Player();
        add(Layer.player, player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        float nearestY = 0.0f;
        for (ArrayList<IGameObject> objects : layers) {
            int count = objects.size();
            for (int i = count - 1; i >= 0; i--) {
                IGameObject gameObject = objects.get(i);
                if (gameObject.getClass().getSimpleName().equals("Enemy")) {
                    Enemy enemy = (Enemy) gameObject;
                    if (enemy.getPosition()[1] > nearestY) {
                        nearestY = enemy.getPosition()[1];
                        player.setNearEnemyPos(enemy.getPosition());
                    }
                }
            }
        }
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
        return false;
    }


}
