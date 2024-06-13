package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Castle extends Sprite implements IBoxCollidable {
    private static final float CASTLE_WIDTH = Metrics.width*2;
    private static final float CASTLE_HEIGHT = Metrics.height / 5;
    private float hp = 100.0f;
    private float maxHp = 100.0f;
    protected static Gauge gauge = new Gauge(0.1f, R.color.castle_gauge_fg, R.color.castle_gauge_bg);
    private Paint hpPaint;

    public static float RECOVERY_INTERVAL = 5.0f;
    private float recoveryCoolTime = 0.f;
    public float recoveryLevel = 0;
    public float def = 0;


    public Castle(){
        super(R.mipmap.castle);
        setPosition(Metrics.width/2, Metrics.height * 14/ 15, CASTLE_WIDTH, CASTLE_HEIGHT);

        hpPaint = new Paint();
        hpPaint.setColor(Color.RED);
        hpPaint.setTextSize(500f);
    }

    @Override
    public void update(float elapsedSeconds){
        super.update(elapsedSeconds);

        recoveryCoolTime += elapsedSeconds;
        if(recoveryCoolTime >= RECOVERY_INTERVAL) {
            if (hp < maxHp) {
                hp += 0.2f * recoveryLevel;
                recoveryCoolTime = 0.0f;
            }
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public boolean decreaseHp(float power) {
        if(power - def > 0){
            hp -= (power - def);
        }
        return hp <= 0;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.save();

        float width = dstRect.width() * 0.5f;
        canvas.translate(x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        gauge.draw(canvas, (float)hp / maxHp);
        canvas.restore();
    }

    public float getHp(){
        return hp;
    }

    public void setMaxHp(float num) {maxHp = num;}
    public void setHp(float num) {hp = num;}

    public void init() {
        recoveryLevel = 0;
        RECOVERY_INTERVAL = 5.0f;
        maxHp = 100.0f;
        hp = 100.0f;
        def = 0;
    }

}
