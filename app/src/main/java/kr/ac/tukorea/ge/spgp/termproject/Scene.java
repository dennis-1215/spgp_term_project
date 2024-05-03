package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class Scene {

    private static ArrayList<Scene> stack = new ArrayList<>();

    public static Scene top() {
        int top = stack.size() - 1;
        if (top < 0) return null;
        return stack.get(top);
    }
    public static void push(Scene scene) {
        stack.add(scene);
    }
    public void push() {
        push(this);
    }
    public static void pop() {
        int top = stack.size() - 1;
        if (top < 0) return;
        stack.remove(top);
    }

    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();

    public void update(float elapsedSeconds) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.update(elapsedSeconds);
        }
    }

    public void draw(Canvas canvas) {
        for (IGameObject gameObject : gameObjects) {
            gameObject.draw(canvas);
        }
    }

    public boolean onTouch(MotionEvent event) {
        return false;
    }
}
