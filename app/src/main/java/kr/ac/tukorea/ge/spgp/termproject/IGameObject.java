package kr.ac.tukorea.ge.spgp.termproject;

import android.graphics.Canvas;

public interface IGameObject {
    public void update(float elapsedSeconds);
    public void draw(Canvas canvas);
}
