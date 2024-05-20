package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.res.BitmapPool;

public class TimeScore implements IGameObject {
    private final Bitmap bitmap;
    private final float right, top, dstCharWidth, dstCharHeight;
    private final Rect srcRect = new Rect();
    private final RectF dstRect = new RectF();
    private final int srcCharWidth, srcCharHeight;
    private float score, displayScore;

    public TimeScore(int mipmapId, float right, float top, float width) {
        this.bitmap = BitmapPool.get(mipmapId);
        this.right = right;
        this.top = top;
        this.dstCharWidth = width;
        this.srcCharWidth = (bitmap.getWidth()-10) / 10;
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void setScore(int score) {
        this.score = this.displayScore = score;
    }

    @Override
    public void update(float elapsedSeconds) {
        int diff = (int)(score - displayScore);
        if (diff == 0) return;
        if (-10 < diff && diff < 0) {
            displayScore--;
        } else if (0 < diff && diff < 10) {
            displayScore++;
        } else {
            displayScore += diff / 10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int minute = (int)this.displayScore/60;
        int sec = (int)this.displayScore%60;
        float x = right;

        // 초 출력
        if(sec == 0){
            int digit = sec % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }
        while (sec > 0) {
            int digit = sec % 10;
            srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
            x -= dstCharWidth;
            dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            sec /= 10;
        }

        // 콜론 : 출력
        srcRect.set(bitmap.getWidth()-10, 0, bitmap.getWidth(), srcCharHeight);
        x -= dstCharWidth/2;
        dstRect.set(x, top, x + dstCharWidth/2, top + dstCharHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);

        // 분 출력
        int digit = minute % 10;
        srcRect.set(digit * srcCharWidth, 0, (digit + 1) * srcCharWidth, srcCharHeight);
        x -= dstCharWidth;
        dstRect.set(x, top, x + dstCharWidth, top + dstCharHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void add(float amount) {
        score += amount;
    }

    public int getScore() {
        return (int)score;
    }
}
