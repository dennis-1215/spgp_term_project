package kr.ac.tukorea.ge.spgp.termproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;


public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public MainScene() {
        Resources res = GameView.res;
        Bitmap monsterBitmapA = BitmapFactory.decodeResource(res, R.mipmap.monster_a);
        Bitmap monsterBitmapB = BitmapFactory.decodeResource(res, R.mipmap.monster_b);

        MonsterA.setBitmap(monsterBitmapA);
        MonsterB.setBitmap(monsterBitmapB);

        for(int i = 0; i < 10; i++){
            gameObjects.add(Monster.random());
        }

        Bitmap playerBitmap = BitmapFactory.decodeResource(res, R.mipmap.playersprite);
        this.player = new Player(playerBitmap);
        gameObjects.add(player);
        Log.d(TAG, "Loaded playerBitmap = " + playerBitmap);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                if (pts[1] < 1.0) {
                    pop();
                }
                return true;
        }
        // 서브씬 테스트용 Ctrl+Click
        if (event.getPointerCount() >= 2) {
            new ChoiceScene().push();
        }
        return false;
    }
}
