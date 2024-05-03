package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

public class Monster implements IGameObject {
    private final RectF dstRect = new RectF();
    private static final float MONSTER_OFFSET = 1.0f;
    private static final float SPEED = 37.0f;

    public static final Random random = new Random();
    public static Monster random(){
        if(random.nextFloat() < 0.5f) {
            return new MonsterA(random.nextFloat() * GameView.SCREEN_WIDTH,
                    0.0f, 0, 1.0f);
        }
        else{
            return new MonsterB(random.nextFloat() * GameView.SCREEN_WIDTH,
                    0.0f, 0, 1.0f);
        }
    }

    public Monster(float centerX, float centerY) {
        dstRect.set(centerX - MONSTER_OFFSET, centerY - MONSTER_OFFSET,
                centerX + MONSTER_OFFSET, centerY + MONSTER_OFFSET);
    }
    private static Bitmap bitmap;
    public static void setBitmap(Bitmap bitmap) { // Alt+Insert -> Setter
        Monster.bitmap = bitmap;
    }

    @Override
    public void update(float elapsedSeconds) {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
