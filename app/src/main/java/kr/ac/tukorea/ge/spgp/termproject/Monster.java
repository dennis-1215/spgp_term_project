package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Monster {

    private final RectF dstRect = new RectF();
    private static final float MONSTER_OFFSET = 1.0f;
    private float dx, dy;

    public Monster(float centerX, float centerY, float dx, float dy) {
        dstRect.set(centerX - MONSTER_OFFSET, centerY - MONSTER_OFFSET,
                centerX + MONSTER_OFFSET, centerY + MONSTER_OFFSET);
        this.dx = dx;
        this.dy = dy;
    }
    private static Bitmap bitmap;
    public static void setBitmap(Bitmap bitmap) { // Alt+Insert -> Setter
        Monster.bitmap = bitmap;
    }

    public void update() {
        dstRect.offset(dx, dy);

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
