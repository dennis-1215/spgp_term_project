package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Castle extends Sprite implements IBoxCollidable {
    private static final float CASTLE_WIDTH = Metrics.width*2;
    private static final float CASTLE_HEIGHT = Metrics.height / 5;
    private static float hp = 1000.0f;

    public Castle(){
        super(R.mipmap.castle);
        setPosition(Metrics.width/2, Metrics.height * 14/ 15, CASTLE_WIDTH, CASTLE_HEIGHT);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public boolean decreaseHp(float power) {
        hp -= power;
        return hp <= 0;
    }

    public float getHp(){
        return hp;
    }
}
