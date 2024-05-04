package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;

public class Player extends Sprite {
    private Bitmap bitmap;
    private RectF dstRect = new RectF();
    private RectF targetRect = new RectF();
    private static final float BULLET_INTERVAL = 1.5f;
    private static final float offset = 1.25f;
    private float bulletCoolTime = 1.0f;
    private double targetAngle;

    public Player() {
        super(R.mipmap.playersprite);
        setPosition(Metrics.width/2, Metrics.height * 7 / 8, offset);

        bitmap = BitmapPool.get(R.mipmap.playersprite);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        bulletCoolTime -= elapsedSeconds;
        if (bulletCoolTime <= 0) {
            fireBall();
            bulletCoolTime = BULLET_INTERVAL;
        }
    }

    public void setNearEnemyPos(float[] target){
        float dx = (target[0] - getPosition()[0]);
        float dy = (target[1] - getPosition()[1]);
        targetAngle = Math.toDegrees(Math.acos(dx / Math.sqrt(dx*dx + dy*dy))) * dy / Math.abs(dy);
    }

    private void fireBall() {
        Scene.top().add(new Bullet(x, y, Math.toRadians(targetAngle)));
    }

    @Override
    public void draw(Canvas canvas) {
        if (dx != 0) {
            canvas.drawBitmap(bitmap, null, dstRect, null);
        }
        super.draw(canvas);
    }
}
