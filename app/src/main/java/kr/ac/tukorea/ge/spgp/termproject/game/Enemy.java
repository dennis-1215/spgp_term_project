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
    private float POWER = 1.0f;
    private static float attackTime = 0.0f;
    private static float EXP = 0.0f;
    private int RANDOM_NUMBER;


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
    private Enemy(int type, int level) {
        super(resIds[type], 8);
        setPosition( Metrics.width * random.nextFloat(), -RADIUS, RADIUS);
        this.life = this.maxLife = (3 - type) * 10 * level;
        dy = SPEED * (type+1);
        EXP = 30 + random.nextInt(50);
        RANDOM_NUMBER = random.nextInt(1000);
        if(RANDOM_NUMBER == 0){
            EXP += 10000 * random.nextFloat();
        }
        else if(RANDOM_NUMBER <= 5){
            EXP += 5000;
        }
        else if(RANDOM_NUMBER <= 50) {
            EXP += 1000;
        }
        else if(RANDOM_NUMBER <= 100) {
            EXP += 100;
        }

        POWER += level/10;
    }

    public static Enemy get(int type, int level){
        return new Enemy(type, level);
    }

    @Override
    public void update(float elapsedSeconds){
        super.update(elapsedSeconds);
        if(state == State.attack)
            attackTime += elapsedSeconds;
    }

    public void setAttackState(){
        dy = 0;
        state = State.attack;
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
            attackTime -= ATTACKCOOL;
            return true;
        }
        return false;
    }

    public float getPower(){
        return POWER;
    }

    public float getEXP(){
        return EXP;
    }
}
