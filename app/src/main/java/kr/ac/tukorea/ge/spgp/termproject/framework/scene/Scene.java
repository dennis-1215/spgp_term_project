package kr.ac.tukorea.ge.spgp.termproject.framework.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.termproject.BuildConfig;
import kr.ac.tukorea.ge.spgp.termproject.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.game.Enemy;
import kr.ac.tukorea.ge.spgp.termproject.game.Player;

public class Scene {
    private static ArrayList<Scene> stack = new ArrayList<>();
    private static final String TAG = Scene.class.getSimpleName();
    private Player player = null;

    public static Scene top() {
        int top = stack.size() - 1;
        if (top < 0) {
            Log.e(TAG, "Scene Stack is empty in Scene.top()");
            return null;
        }
        return stack.get(top);
    }
    public static void push(Scene scene) {
        Scene prev = top();
        if (prev != null) {
            prev.onPause();
        }
        stack.add(scene);
        scene.onStart();
    }
    public void push() {
        push(this);
    }
    public static void pop() {
        Scene scene = top();
        if (scene == null) {
            Log.e(TAG, "Scene Stack is empty in Scene.pop()");
            return;
        }
        scene.onEnd();
        stack.remove(scene);

        scene = top();
        if (scene == null) {
            Log.i(TAG, "Last scene is being popped");
            finishActivity();
            return;
        }
        scene.onResume();
    }

    public static void popAll() {
        int count = stack.size();
        for (int i = count - 1; i >= 0; i--) {
            Scene scene = stack.get(i);
            scene.onEnd();
        }
        stack.clear();
        finishActivity();
    }

    public static void finishActivity() {
        //GameView gameView = null;
        //gaveView.getActivity().finish();
        GameActivity.activity.finish();
    }

    public static void pauseTop() {
        Log.i(TAG, "Pausing Game");
        Scene scene = top();
        if (scene != null) {
            scene.onPause();
        }
    }

    public static void resumeTop() {
        Log.i(TAG, "Resuming Game");
        Scene scene = top();
        if (scene != null) {
            scene.onResume();
        }
    }


    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();

    public int count() {
        int count = 0;
        for (ArrayList<IGameObject> objects : layers) {
            count += objects.size();
        }
        return count;
    }

    protected void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update(float elapsedSeconds) {
        float nearestY = 0.0f;
        Enemy enemy = null;
        for (ArrayList<IGameObject> objects : layers) {
            int count = objects.size();
            for (int i = count - 1; i >= 0; i--) {
                IGameObject gameObject = objects.get(i);
                gameObject.update(elapsedSeconds);

                if (player == null) {
                    if (gameObject.getClass().getSimpleName().equals("Player")) {
                        player = (Player) gameObject;
                    }
                }
                if (gameObject.getClass().getSimpleName().equals("Enemy")) {
                    enemy = (Enemy) gameObject;
                    if (enemy.getPosition()[1] > nearestY) {
                        nearestY = enemy.getPosition()[1];
                        player.setNearEnemyPos(enemy.getPosition());
                    }
                }
            }
        }
    }

    protected static Paint bboxPaint;
    public void draw(Canvas canvas) {
        for (ArrayList<IGameObject> objects: layers) {
            for (IGameObject gobj : objects) {
                gobj.draw(canvas);
            }
        }
        if (BuildConfig.DEBUG) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (ArrayList<IGameObject> objects: layers) {
                for (IGameObject gobj : objects) {
                    if (gobj instanceof IBoxCollidable) {
                        RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                        canvas.drawRect(rect, bboxPaint);
                    }
                }
            }
        }
    }

    public boolean onTouch(MotionEvent event) {
        return false;
    }
    // Overridables

    protected void onStart() {
    }
    protected void onEnd() {
    }

    protected void onPause() {
    }
    protected void onResume() {
    }

    public boolean onBackPressed() {
        return false;
    }

    // Game Object Management
    public void add(int layerIndex, IGameObject gameObject) {
        ArrayList<IGameObject> objects = layers.get(layerIndex);
        objects.add(gameObject);
    }
    public void remove(int layerIndex, IGameObject gameObject) {
        ArrayList<IGameObject> objects = layers.get(layerIndex);
        objects.remove(gameObject);
    }
}
