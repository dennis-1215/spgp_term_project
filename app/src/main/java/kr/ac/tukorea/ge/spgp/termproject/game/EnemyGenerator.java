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
            for (int i = 0; i <=(int) gameTime / 40; ++i) {
                generate();
                enemyTime = random.nextInt(3) + 1.5f;
            }
        }
    }

    private void generate() {
        Scene scene = Scene.top();

        int type = random.nextInt(3);
        int level = (int) (gameTime/30);
        int diff = (int) (gameTime/60);

        int i = 1;
        while(gameTime > 180 * i){
            level += 3;
            i++;
        }

        i = 1;

        while(gameTime > 300 * i){
            diff += 5;
            i++;
        }

        scene.add(MainScene.Layer.enemy, Enemy.get(type, 1+level+diff));
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
