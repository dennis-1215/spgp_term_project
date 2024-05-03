package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Player implements IGameObject{
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    private float tx, ty, dx, dy;
    private float x, y, offset;

    public Player() {
        x = Metrics.width / 2;
        y = 2 * Metrics.height / 3;
        dstRect.set(x-offset, y-offset, x+offset, y+offset);

        this.bitmap = BitmapPool.get(R.mipmap.playersprite);
    }

    @Override
    public void update(float elapsedSeconds) {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
