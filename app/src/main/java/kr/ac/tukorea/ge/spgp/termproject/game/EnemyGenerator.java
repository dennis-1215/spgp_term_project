package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;

public class EnemyGenerator implements IGameObject {
    private final Random random = new Random();
    private float enemyTime = 0;
    private float gameTime = 0.0f;
    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;
        gameTime += elapsedSeconds;
        if (enemyTime < 0) {
            for (int i = 0; i < gameTime / 30; ++i) {
                generate();
                enemyTime = random.nextInt(3) + 0.5f;
            }
        }
    }

    private void generate() {
        Scene scene = Scene.top();

        int level = random.nextInt(3);
        scene.add(MainScene.Layer.enemy, Enemy.get(level, gameTime));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
