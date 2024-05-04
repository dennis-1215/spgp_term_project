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
        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller.ordinal(), new EnemyGenerator());
        add(Layer.castle.ordinal(), new Castle());
        this.player = new Player();
        add(Layer.player.ordinal(), player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        checkCollision();
    }

    private void checkCollision() {
        ArrayList<IGameObject> enemies = layers.get(Layer.enemy.ordinal());
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            ArrayList<IGameObject> bullets = layers.get(Layer.bullet.ordinal());
            for (int b = bullets.size() - 1; b >= 0; b--) {
                Bullet bullet = (Bullet)bullets.get(b);
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !!");
                    remove(Layer.bullet.ordinal(), bullet);
                    remove(Layer.enemy.ordinal(), enemy);
//                    removed = true;
                    break;
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
        // 서브씬 테스트용 Ctrl+Click
        if (event.getPointerCount() >= 2) {
            new ChoiceScene().push();
        }
        return false;
    }
}
