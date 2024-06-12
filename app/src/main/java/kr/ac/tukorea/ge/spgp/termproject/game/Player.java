package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.res.Sound;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;

public class Player extends AnimSprite {
    private static final String TAG = Player.class.getSimpleName();

    public enum State{
        idle, fire
    }

    public static int attackSpeedLevel = 0;
    public static int damageLevel = 0;
    public static int fireNumLevel = 0;
    public static int attackCountLevel = 0;
    public static int castleHpLevel = 0;
    public static int castleRecoveryLevel = 0;
    public static int castleDefLevel = 0;

    public static float BULLET_INTERVAL = 2.0f;
    private static final float offset = 0.75f;
    private float bulletCoolTime = 2.5f;
    private float multishotCoolTime = 0.f;
    private int multishot = 0;
    private static int level = 0;
    private static float expMax = 100.0f;
    private static float exp = 0.0f;
    private double targetAngle;
    public static float damage = 15.0f;

    protected static Gauge gauge = new Gauge(0.1f, R.color.player_gauge_fg, R.color.player_gauge_bg);


    protected ArrayList<Integer> levelOptions = new ArrayList<Integer>(0);
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

        canvas.save();
        float width = dstRect.width() * 4.0f;
        canvas.translate(x - width/5, -0.5f);
        canvas.scale(width*0.9f, width);
        gauge.draw(canvas, (float)exp / expMax);
        canvas.restore();
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        bulletCoolTime -= elapsedSeconds;
        multishotCoolTime += elapsedSeconds;
        if (bulletCoolTime <= 0) {
            multishot = attackCountLevel;
            multishotCoolTime = 0.f;
            fire();
            shoot();
            bulletCoolTime = BULLET_INTERVAL;
        }
        else if (bulletCoolTime <= BULLET_INTERVAL - BULLET_INTERVAL/10.f){
            idle();
        }

        if(multishot > 0 && multishotCoolTime >= 0.1f - attackSpeedLevel * 0.005){
            shoot();
            multishot--;
            multishotCoolTime = 0.0f;
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

    private void shoot(){
        Sound.playEffect(R.raw.fireball);
        for (int i = 0; i <= fireNumLevel; ++i) {
            if (i % 2 == 1) {
                fireBall(10 * ((i + 1) / 2));
            } else {
                fireBall(-10 * ((i + 1) / 2));
            }
        }
    }

    private void fireBall(float angle) {
        Scene.top().add(MainScene.Layer.bullet, Bullet.get(x, y, Math.toRadians(targetAngle + angle), damage));
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

    public ArrayList<Integer> getOptions(){
        levelOptions.clear();

        if(attackSpeedLevel < 13) {
            levelOptions.add(0);
        }
        levelOptions.add(1);
        if(fireNumLevel < 7){
            levelOptions.add(2);
        }
        if(attackCountLevel < 8){
            levelOptions.add(3);
        }

        levelOptions.add(4);
        if(castleRecoveryLevel < 5) {
            levelOptions.add(5);
        }

        levelOptions.add(6);

        Log.d(TAG, "level options : " + levelOptions);

        return levelOptions;
    }
    public void init(){
        level = 0;
        attackSpeedLevel = 0;
        damageLevel = 0;
        fireNumLevel = 0;
        attackCountLevel = 0;
        castleDefLevel = 0;
        castleHpLevel = 0;
        castleRecoveryLevel = 0;

        expMax = 100.0f;
        exp = 0.0f;
        BULLET_INTERVAL = 2.0f;
        damage = 15.0f;


    }
}
