package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;

public class EnemyGenerator implements IGameObject {
    private final Random random = new Random();
    private float enemyTime = 0;
    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;
        if (enemyTime < 0) {
            int level = random.nextInt(3);
            Scene.top().add(new Enemy(level));
            enemyTime = random.nextFloat() + 0.5f;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
