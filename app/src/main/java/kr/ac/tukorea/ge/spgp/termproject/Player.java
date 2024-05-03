package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Player {
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    public Player(Bitmap bitmap) {
        float cx = 4.5f, y = 12.0f;
        float r = 1.0f;
        dstRect.set(cx-r, y, cx+r, y+2*r);

        this.bitmap = bitmap;
    }

    public void update() {
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
