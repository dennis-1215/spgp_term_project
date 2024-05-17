package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.GameView;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update(float elapsedSeconds) {
        Scene scene = Scene.top();
        if (scene == null) return;

        ArrayList<IGameObject> players = scene.objectsAt(MainScene.Layer.player);
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        ArrayList<IGameObject> castles = scene.objectsAt(MainScene.Layer.castle);
        for(int p = players.size() - 1; p >= 0; p--) {
            Player player = (Player) players.get(p);
            for (int c = castles.size() - 1; c >= 0; c--) {
                Castle castle = (Castle) castles.get(c);

                for (int e = enemies.size() - 1; e >= 0; e--) {
                    Enemy enemy = (Enemy) enemies.get(e);
                    if (CollisionHelper.collides(enemy, castle)) {
                        enemy.setAttackState();
                        if (enemy.attack()) {
                            castle.decreaseHp(enemy.getPower());
                        }
                    }


                    ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.bullet);
                    for (int b = bullets.size() - 1; b >= 0; b--) {
                        Bullet bullet = (Bullet) bullets.get(b);
                        if (CollisionHelper.collides(enemy, bullet)) {
                            Log.d(TAG, "Collision !!");
                            scene.remove(MainScene.Layer.bullet, bullet);
                            boolean dead = enemy.decreaseLife(bullet.getPower());
                            if (dead) {
                                player.addExp(enemy.getEXP());
                                scene.remove(MainScene.Layer.enemy, enemy);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}