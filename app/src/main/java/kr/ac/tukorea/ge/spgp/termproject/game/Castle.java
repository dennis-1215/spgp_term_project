package kr.ac.tukorea.ge.spgp.termproject.game;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Castle extends Sprite {
    private static final float CASTLE_WIDTH = Metrics.width*2;
    private static final float CASTLE_HEIGHT = Metrics.height / 5;

    public Castle(){
        super(R.mipmap.castle);
        setPosition(Metrics.width/2, Metrics.height * 14/ 15, CASTLE_WIDTH, CASTLE_HEIGHT);
    }
}
