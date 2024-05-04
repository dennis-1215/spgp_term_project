package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Monster implements IGameObject {
    private final RectF dstRect = new RectF();


    public static final Random random = new Random();
    public static Monster random(){
        if(random.nextFloat() < 0.5f) {
            return new MonsterA(random.nextFloat() * Metrics.width,
                    0.0f, 0, 1.0f);
        }
        else{
            return new MonsterB(random.nextFloat() * Metrics.width,
                    0.0f, 0, 1.0f);
        }
    }

    public RectF getDstRect(){
        return dstRect;
    }

    @Override
    public void update(float elapsedSeconds) {
    }

    @Override
    public void draw(Canvas canvas) {
    }
}