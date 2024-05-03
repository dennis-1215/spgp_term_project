package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class MonsterA extends Monster {
    private static Bitmap bitmap;
    private final RectF dstRect = new RectF();
    private static final float MONSTER_OFFSET = 1.0f;
    private static final float SPEED = 1.0f;
    private float dx, dy;

    public MonsterA(float centerX, float centerY, float dx, float dy) {
        dstRect.set(centerX - MONSTER_OFFSET, centerY - MONSTER_OFFSET,
                centerX + MONSTER_OFFSET, centerY + MONSTER_OFFSET);
        this.dx = SPEED*dx;
        this.dy = SPEED*dy;

        bitmap = BitmapPool.get(R.mipmap.monster_a);
    }

    public void update(float elapsedSeconds) {
        float timeDx = this.dx * elapsedSeconds;
        float timeDy = this.dy * elapsedSeconds;
        dstRect.offset(timeDx, timeDy);
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
                dy = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}