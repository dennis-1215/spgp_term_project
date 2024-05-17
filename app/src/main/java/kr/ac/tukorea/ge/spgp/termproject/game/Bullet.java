package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;


public class Bullet extends AnimSprite implements IBoxCollidable {
    private static final float BULLET_WIDTH = 0.68f;
    private static final float BULLET_HEIGHT = BULLET_WIDTH * 40 / 28;
    private static final float SPEED = 20.0f;
    private static float POWER = 10.0f;

    private Bullet(float x, float y, double angle_rad, float power) {
        super(R.mipmap.fireball_sheet, 20);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = (float) (SPEED * Math.cos(angle_rad));
        dy = (float) (SPEED * Math.sin(angle_rad));
        POWER = power;
    }

    public static Bullet get(float x, float y, double angle_rad, float power) {
        return new Bullet(x, y, angle_rad, power);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.bottom < 0 || dstRect.left > Metrics.width || dstRect.right < 0 ) {
            Scene.top().remove(MainScene.Layer.bullet, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public void setPower(float power) { POWER = power;}

    public float getPower() {
        return POWER;
    }
}
