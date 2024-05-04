package kr.ac.tukorea.ge.spgp.termproject.game;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;


public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public MainScene() {
        add(new EnemyGenerator());

        this.player = new Player();
        add(player);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        checkCollision();
    }

    private void checkCollision() {
        int count = gameObjects.size();
        for (int i1 = count - 1; i1 >= 0; i1--) {
            count = gameObjects.size();
            if (i1 >= count) {
                i1 = count - 1; // enemy 와 bullet 이 모두 삭제된 경우에는 count 가 더 많이 줄었을 수도 있다.
            }
            IGameObject o1 = gameObjects.get(i1);
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
//            boolean removed = false;
            count = gameObjects.size();
            for (int i2 = count - 1; i2 >= 0; i2--) {
                IGameObject o2 = gameObjects.get(i2);
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    Log.d(TAG, "Collision !!");
                    remove(bullet);
                    remove(enemy);
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
