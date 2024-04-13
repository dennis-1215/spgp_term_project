package kr.ac.tukorea.ge.spgp.termproject;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class GameView extends View {
    private final Activity activity;


    public GameView(Context context) {
        super(context);
        this.activity = (Activity) context;
    }
}
