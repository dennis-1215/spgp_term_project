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
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private Activity activity;

    private final ArrayList<IGameObject> gameObjects = new ArrayList<>();
    private final Player player;


    public GameView(Context context, AttributeSet attr){
        super(context, attr);
        this.activity = (Activity) context;

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.RED);

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100f);

        setFullScreen(); // default behavior?

        Resources res = getResources();
        Bitmap monsterBitmapA = BitmapFactory.decodeResource(res, R.mipmap.monster_a);
        Bitmap monsterBitmapB = BitmapFactory.decodeResource(res, R.mipmap.monster_b);

        MonsterA.setBitmap(monsterBitmapA);
        MonsterB.setBitmap(monsterBitmapB);

        for(int i = 0; i < 10; i++){
            gameObjects.add(Monster.random());
        }

        Bitmap fighterBitmap = BitmapFactory.decodeResource(res, R.mipmap.playersprite);
        this.player = new Player(fighterBitmap);
        gameObjects.add(player);

        scheduleUpdate();
    }

    // Handler is android.os.Handler.
    // do not import java.util.logging.Handler
    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    private long previousNanos = 0;
    private float elapsedSeconds;
    @Override
    public void doFrame(long nanos) {
        long elapsedNanos = nanos - previousNanos;
        elapsedSeconds = elapsedNanos / 1_000_000_000f;
        if (previousNanos != 0) {
            update();
        }
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
        previousNanos = nanos;
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

    public static final float SCREEN_WIDTH = 9.0f;
    public   static final float SCREEN_HEIGHT = 16.0f;
    private final Matrix transformMatrix = new Matrix();
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint = new Paint();
    private final Paint fpsPaint = new Paint();

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
        for(IGameObject gameObject : gameObjects){
            gameObject.draw(canvas);
        }
        canvas.restore();

        int fps = (int) (1.0f / elapsedSeconds);
        canvas.drawText("FPS: " + fps, 100f, 200f, fpsPaint);
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
        for(IGameObject gameObject : gameObjects){
            gameObject.update(elapsedSeconds);
        }
    }
}
