package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet implements IGameObject {
    private static final float SPEED = 15.0f;
    private static final float LENGTH = 1.0f;
    private static final float LASER_WIDTH = 0.1f;

    private float x, y, dx, dy, ex, ey;
    private static Paint paint;

    public Bullet(float x, float y, float angle_radian) {
        this.x = x;
        this.y = y;
        dx = (float) (SPEED * Math.cos(angle_radian));
        dy = (float) (SPEED * Math.sin(angle_radian));
        ex = (float) (LENGTH * Math.cos(angle_radian));
        ey = (float) (LENGTH * Math.sin(angle_radian));
        if (paint == null) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(LASER_WIDTH);
            paint.setColor(Color.RED);
        }
    }

    @Override
    public void update(float elapsedSeconds) {
        x += elapsedSeconds * dx;
        y += elapsedSeconds * dy;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x + ex, y + ey, paint);
    }
}
