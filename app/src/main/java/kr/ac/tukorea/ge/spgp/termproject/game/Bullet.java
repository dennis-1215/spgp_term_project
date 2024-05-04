package kr.ac.tukorea.ge.spgp.termproject.game;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;


public class Bullet extends Sprite {
    private static final float BULLET_WIDTH = 0.68f;
    private static final float BULLET_HEIGHT = BULLET_WIDTH * 40 / 28;
    private static final float SPEED = 20.0f;

    public Bullet(float x, float y, double angle_rad) {
        super(R.mipmap.fireball);
        setPosition(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        dx = (float) (SPEED * Math.cos(angle_rad));
        dy = (float) (SPEED * Math.sin(angle_rad));
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.bottom < 0 || dstRect.left > Metrics.width || dstRect.right < 0 ) {
            Scene.top().remove(this);
        }
    }

}
