package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

public class MonsterA extends Monster {

    private final RectF dstRect = new RectF();
    private static final float MONSTER_OFFSET = 1.0f;
    private static final float SPEED = 1.0f;
    private float dx, dy;

    public MonsterA(float centerX, float centerY, float dx, float dy) {
        super(centerX, centerY);
        dstRect.set(centerX - MONSTER_OFFSET, centerY - MONSTER_OFFSET,
                centerX + MONSTER_OFFSET, centerY + MONSTER_OFFSET);
        this.dx = SPEED*dx;
        this.dy = SPEED*dy;
    }
    private static Bitmap bitmap;
    public static void setBitmap(Bitmap bitmap) { // Alt+Insert -> Setter
        MonsterA.bitmap = bitmap;
    }

    public void update(float elapsedSeconds) {
        float timeDx = this.dx * elapsedSeconds;
        float timeDy = this.dy * elapsedSeconds;
        dstRect.offset(timeDx, timeDy);
        if (dy > 0) {
            if (dstRect.bottom > GameView.SCREEN_HEIGHT) {
                dy = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}