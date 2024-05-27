package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
    private float time;
    private final Paint stringPaint;

    private KillScore kill;
    private TimeScore times;
    private float timescore;
    private float killscore;
    private boolean skip;
    private float skip_time;
    public enum Layer {
        bg, castle, enemy, bullet, player, ui, controller, cards, COUNT
    }

    Typeface tf;
    public GameOverScene() {
        scene = Scene.top();
        stringPaint = new Paint();
        stringPaint.setColor(Color.BLACK);
        stringPaint.setTextSize(1.0f);
        stringPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));

        initLayers(Layer.COUNT);

        add(Layer.castle, scene.objectsAt(MainScene.Layer.castle).get(0));
        this.player = new Player();
        add(Layer.player, player);


        kill = (KillScore)scene.objectsAt(MainScene.Layer.ui).get(0);
        times = (TimeScore)scene.objectsAt(MainScene.Layer.ui).get(1);

        timescore = (int) times.getScore() / 60 * 100 + times.getScore() % 60 * 10;
        killscore = kill.getScore() / 100 * 10000 + kill.getScore() % 100 * 10;
    }

    @Override
    public void update(float elapsedSeconds) {
        //super.update(elapsedSeconds);
        time += elapsedSeconds;
        if(skip){
            skip_time += elapsedSeconds;
        }
    }


    @Override
    public void draw(Canvas canvas){

        if(time >= 5.0f){
            skip = true;
        }
        canvas.save();
        canvas.scale(0.8f, 0.8f, 0.0f, 10.0f);
        if(time >= 1.0f){
            canvas.drawText("생존시간 " +   times.getScore() / 60 + "분" + times.getScore() % 60 + "초", 0.1f, 1.0f, stringPaint);
        }
        if(time >= 2.0f){
            canvas.drawText("시간점수 " +   String.valueOf((int)timescore), 0.1f, 2.0f, stringPaint);
        }
        if(time >= 3.0f){
            canvas.drawText("처치 " + kill.getScore(), 0.1f, 4.0f, stringPaint);
        }
        if(time >= 4.0f){
            canvas.drawText("처치점수 " + String.valueOf((int)killscore), 0.1f, 5.0f, stringPaint);
        }

        if(time >= 5.0f){
            canvas.drawText("총점수 " + String.valueOf((int)timescore + (int)killscore), 2.0f, 8.0f, stringPaint);
        }

        canvas.restore();

        if(skip && skip_time < 3.0f){
            canvas.save();
            canvas.scale(0.5f, 0.5f, 2.0f, 13.0f);
            canvas.drawText("wait (" + (int)(3.0f - skip_time) + ")", 2.0f, 11.0f, stringPaint);
            canvas.restore();
        }
        else if(skip && skip_time >= 3.0f) {
            canvas.save();
            canvas.scale(0.5f, 0.5f, 0.0f, 13.0f);
            canvas.drawText("Touch to Screen ", 0.0f, 11.0f, stringPaint);
            canvas.drawText("go to Lobby... ", 4.0f, 12.0f, stringPaint);
            canvas.restore();
        }


    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            skip = true;
            time += 10.0f;

            if(skip_time >= 3.0f){
                scene.popAll();
            }
            return true;
        }
        return false;
    }
}
