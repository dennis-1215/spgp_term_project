package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.BuildConfig;
import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class ChoiceScene extends Scene {
    private static final String TAG = ChoiceScene.class.getSimpleName();
    private final Player player;

    private static ArrayList<Integer> options;
    public static final Random random = new Random();


    public enum Layer {
        bg, castle, enemy, bullet, player, ui, controller, cards, COUNT
    }
    public ChoiceScene() {
        Scene scene = Scene.top();

        initLayers(Layer.COUNT);

        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for(int e = enemies.size() - 1; e >= 0; e--){
            Enemy enemy = (Enemy) enemies.get(e);
            add(Layer.enemy, enemy);
        }
        add(Layer.bg, new Background(R.mipmap.background));

        add(Layer.castle, new Castle());
        this.player = new Player();
        add(Layer.player, player);
        ArrayList<IGameObject> uis = scene.objectsAt(MainScene.Layer.ui);

        add(Layer.ui, uis.get(0));
        add(Layer.ui, uis.get(1));
        add(Layer.ui, uis.get(2));

        for(int i = 1; i <= 3; ++i) {
            add(Layer.cards, new Card(i, player.getOptions().get(random.nextInt(player.getOptions().size())), player));
        }
    }

    @Override
    public void update(float elapsedSeconds) {
        //super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ArrayList<IGameObject> cards = this.objectsAt(Layer.cards);
            for(int c = cards.size() - 1; c >= 0; c--){
                Card card = (Card) cards.get(c);
                card.OnClickAction(Metrics.fromScreen(event.getX(), event.getY()));
            }
            Scene.pop();
            return true;
        }
        return false;
    }
}
