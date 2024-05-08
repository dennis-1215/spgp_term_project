package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Enemy extends AnimSprite implements IBoxCollidable {
    protected enum State {
        running, attack
    }
    protected State state = State.running;
    public static final Random random = new Random();
    private static final float SPEED = 0.5f;
    private static final float RADIUS = 0.5f;
    private static final float ATTACKCOOL = 2.0f;
    private static final float POWER = 1.0f;
    private static float attackTime = 0.0f;
    private static float EXP = 0.0f;


    protected static Rect[][] srcRectsArray = {
            new Rect[] {
                    new Rect(0 + 32 * 0, 32, 32 + 32 * 0, 62),
                    new Rect(0 + 32 * 1, 32, 32 + 32 * 1, 62),
                    new Rect(0 + 32 * 2, 32, 32 + 32 * 2, 62),
                    new Rect(0 + 32 * 3, 32, 32 + 32 * 3, 62),

             },
            new Rect[] {
                    new Rect(0 + 32 * 0, 0, 32 + 32 * 0, 31),
                    new Rect(0 + 32 * 1, 0, 32 + 32 * 1, 31),
                    new Rect(0 + 32 * 2, 0, 32 + 32 * 2, 31),
                    new Rect(0 + 32 * 3, 0, 32 + 32 * 3, 31),
            },
    };

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRectsArray[state.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }

    private static final int[] resIds = {
            R.mipmap.monster_a, R.mipmap.monster_b, R.mipmap.monster_c
    };
    private float life, maxLife;
    private Enemy(int level) {
        super(resIds[level], 8);
        setPosition( Metrics.width * random.nextFloat(), -RADIUS, RADIUS);
        this.life = this.maxLife = (3 - level) * 10;
        dy = SPEED * (level+1);
        EXP = 10 + random.nextInt(100);
        if(random.nextFloat() < 0.1){
            EXP += 1000;
        }
    }

    public static Enemy get(int level){
        return new Enemy(level);
    }

    @Override
    public void update(float elapsedSeconds){
        super.update(elapsedSeconds);
        attackTime += elapsedSeconds;
        if(dstRect.bottom > Metrics.height * 8.5 / 10){
            dy = 0;
            state = State.attack;
        }

    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }


    public boolean decreaseLife(float power) {
        life -= power;
        return life <= 0;
    }

    public boolean attack(){
        if(attackTime >= ATTACKCOOL){
            attackTime = 0.0f;
            return true;
        }
        else return false;
    }

    public float getPower(){
        return POWER;
    }

    public float getEXP(){
        return EXP;
    }
}
