package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Enemy extends Sprite implements IBoxCollidable {
    public static final Random random = new Random();
    private static final float SPEED = 0.5f;
    private static final float RADIUS = 0.5f;
    private static final int[] resIds = {
            R.mipmap.monster_a, R.mipmap.monster_b, R.mipmap.monster_c
    };
    private int life, maxLife;
    private Enemy(int level) {
        super(resIds[level]);
        setPosition( Metrics.width * random.nextFloat(), -RADIUS, RADIUS);
        this.life = this.maxLife = (3 - level) * 10;
        dy = SPEED * (level+1);
    }

    public static Enemy get(int level){
        return new Enemy(level);
    }

    @Override
    public void update(float elapsedSeconds){
        super.update(elapsedSeconds);
        if(dstRect.bottom > Metrics.height * 8.5 / 10){
            dy = 0;
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }


    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }
}
