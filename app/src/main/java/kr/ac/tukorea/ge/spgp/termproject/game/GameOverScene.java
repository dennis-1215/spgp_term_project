package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class GameOverScene extends Scene {
    private static final String TAG = GameOverScene.class.getSimpleName();
    private final Player player;
    private final Scene scene;

    private static ArrayList<Integer> options;
    public static final Random random = new Random();


    public enum Layer {
        bg, castle, enemy, bullet, player, ui, controller, cards, COUNT
    }
    public GameOverScene() {
        scene = Scene.top();

        initLayers(Layer.COUNT);

        add(Layer.castle, scene.objectsAt(MainScene.Layer.castle).get(0));
        this.player = new Player();
        add(Layer.player, player);
    }

    @Override
    public void update(float elapsedSeconds) {
        //super.update(elapsedSeconds);
    }


    @Override
    public void draw(Canvas canvas){

    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Scene.popAll();
            return true;
        }
        return false;
    }
}
