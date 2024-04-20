package kr.ac.tukorea.ge.spgp.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;


public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private Activity activity;
    private final Bitmap monsterBitmap;
    private final RectF monsterRect = new RectF(3.5f, 7.0f, 5.5f, 9.0f);
    private float monsterDx = 0.0f, monsterDy = 0.06f;


    public GameView(Context context, AttributeSet attr){
        super(context, attr);
        this.activity = (Activity) context;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.RED);
        setFullScreen(); // default behavior?

        Resources res = getResources();
        monsterBitmap = BitmapFactory.decodeResource(res, R.mipmap.monster_a);

        scheduleUpdate();
    }

    // Handler is android.os.Handler.
    // do not import java.util.logging.Handler
    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long nanos) {
        update();
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
    };

    public void setFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        setSystemUiVisibility(flags);
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private static final float SCREEN_WIDTH = 9.0f;
    private static final float SCREEN_HEIGHT = 16.0f;
    private final Matrix transformMatrix = new Matrix();
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(transformMatrix);
        canvas.drawRect(borderRect, borderPaint);
        canvas.drawBitmap(monsterBitmap, null, monsterRect, null);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            update();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void update() {
        monsterRect.offset(monsterDx, monsterDy);
        Log.d(TAG, "monsterRect = " + monsterRect); // Shift+Ctrl+Backspace 활용법 설명
        if (monsterDy > 0) {
            if (monsterRect.bottom > SCREEN_HEIGHT) {
                monsterDy = 0;
            }
        }
    }
}
