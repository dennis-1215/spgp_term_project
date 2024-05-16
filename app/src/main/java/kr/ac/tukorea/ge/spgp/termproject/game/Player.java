package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;

public class Player extends AnimSprite {
    public enum State{
        idle, fire
    }


    public static int attackSpeedLevel = 0;
    public static int damageLevel = 0;
    public static int fireNumLevel = 0;
    public static int attackCountLevel = 0;

    private static final float BULLET_INTERVAL = 1.5f;
    private static final float offset = 0.75f;
    private float bulletCoolTime = 2.5f;
    private static int level = 0;
    private static float expMax = 10.0f;
    private static float exp = 0.0f;
    private double targetAngle;

    protected State state = State.idle;
    protected static Rect[][] srcRectsArray = {
            new Rect[] {
                    new Rect(0, 0, 40, 35)
            },
            new Rect[] {
                    new Rect(0, 0, 40, 35),
                    new Rect(0 + 40 * 1, 0, 40 + 40 * 1, 35),
                    new Rect(0 + 40 * 2, 0, 40 + 40 * 2, 35),
                    new Rect(0 + 40 * 3, 0, 40 + 40 * 3, 35)
            },
    };
    public Player() {
        super(R.mipmap.playersprite, 8);
        setPosition(Metrics.width/2, Metrics.height * 7 / 8, offset);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        Rect[] rects = srcRectsArray[state.ordinal()];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        bulletCoolTime -= elapsedSeconds;
        if (bulletCoolTime <= 0) {
            fire();
            fireBall();
            bulletCoolTime = BULLET_INTERVAL;
        }
        else if (bulletCoolTime <= BULLET_INTERVAL - 0.2f){
            idle();
        }
    }

    public void fire() {
        state = State.fire;
    }

    public void idle() {
        state = State.idle;
    }

    public void setNearEnemyPos(float[] target){
        float dx = (target[0] - getPosition()[0]);
        float dy = (target[1] - getPosition()[1]);
        targetAngle = Math.toDegrees(Math.acos(dx / Math.sqrt(dx*dx + dy*dy))) * dy / Math.abs(dy);
    }

    private void fireBall() {
        Scene.top().add(MainScene.Layer.bullet, Bullet.get(x, y, Math.toRadians(targetAngle)));
    }

    public void addExp(float ex){
        exp += ex;
    }

    public boolean levelUpCheck(){
        if(exp >= expMax){
            exp = exp - expMax;
            expMax += 100.0f;
            level += 1;
            return true;
        }
        else return false;
    }

    public int getLevel(){
        return level;
    }
    public float getExp(){
        return exp;
    }

}
