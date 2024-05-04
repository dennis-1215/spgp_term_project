package kr.ac.tukorea.ge.spgp.termproject.game;

import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

public class Background extends Sprite {
    public Background(int bitmapResId) {
        super(bitmapResId);
        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.height);
    }
}
