package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Player implements IGameObject{
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    private static final float BULLET_INTERVAL = 1.0f/3.0f;
    private static final float offset = 1.25f;
    private float x, y, angle;
    private float bulletCoolTime;

    public Player() {
        x = Metrics.width / 2;
        y = 4 * Metrics.height / 5;
        angle = -90;
        dstRect.set(x-offset, y-offset, x+offset, y+offset);

        this.bitmap = BitmapPool.get(R.mipmap.playersprite);
    }

    @Override
    public void update(float elapsedSeconds) {
        bulletCoolTime -= elapsedSeconds;
        if (bulletCoolTime <= 0) {
            Bullet bullet = new Bullet(x, y, (float) Math.toRadians(angle));
            Scene.top().add(bullet);
            bulletCoolTime = BULLET_INTERVAL;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
