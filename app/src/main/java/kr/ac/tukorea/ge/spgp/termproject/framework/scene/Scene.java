package kr.ac.tukorea.ge.spgp.termproject.framework.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.termproject.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.game.Monster;
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

    public static void finishActivity() {
        //GameView gameView = null;
        //gaveView.getActivity().finish();
        GameActivity.activity.finish();
    }

    protected final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    public void update(float elapsedSeconds) {
        int count = gameObjects.size();
        float nearestY = 0.0f;
        Monster monster = null;
        for (int i = count - 1; i >= 0; i--) {
            IGameObject gameObject = gameObjects.get(i);
            gameObject.update(elapsedSeconds);

            if(player == null) {
                if (gameObject.getClass().getSimpleName().equals("Player")) {
                    player = (Player) gameObject;
                }
            }
            if(gameObject.getClass().getSimpleName().equals("MonsterA") ||
                gameObject.getClass().getSimpleName().equals("MonsterB")){
                monster = (Monster) gameObject;
                if(monster.getDstRect().centerY() > nearestY ){
                    nearestY = monster.getDstRect().centerY();
                    player.setNearEnemyRect(monster.getDstRect());
                }
            }
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
    public void add(IGameObject gameObject) {
        gameObjects.add(gameObject);
        Log.d(TAG, gameObjects.size() + " objects in " + this);
    }
}
