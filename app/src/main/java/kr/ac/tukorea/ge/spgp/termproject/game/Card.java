package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.GameView;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

/*   - 공격속도 : 기본 3초에 1회 공격, lv당 10%의 쿨타임 감소로 3 * 0.9^lv
   - (lv0 = 3.0, lv1 = 2.7, lv2 = 2.43 ... lv17 = 0.50 이 최대)
   - 공격력 : 기본 데미지 50, lv당 20%의 공격력 증가 50 * 1.2^lv (업그레이드에 제한이 없다.)
   - 투사체 개수 : 업그레이드 당 1개씩 추가 최대 9개까지 증가
   - (lv0 = 정면, lv1,lv2 = 각 좌측/우측 10도 방향으로 추가발사, lv3,lv4 = 20도 ... lv7,lv8 = 40도)
   - 발사횟수 : 업그레이드당 1회 추가(최대 8회)
*/

public class Card extends Sprite {
    private final Paint stringPaint;
    public enum cardType {attack_speed, damage, fire_num, attack_count};
    public cardType type;

    public float x;
    public final float y = Metrics.height/2;

    protected static String[] textArray = {"공격속도증가","공격력증가", "발사체증가", "공격횟수증가" };
    private static final int[] resIds = {
            R.mipmap.fire_speed, R.mipmap.damage_up, R.mipmap.double_shot, R.mipmap.more_shot
    };
    public Card(int i, int rand) {
        super(resIds[rand]);
        stringPaint = new Paint();
        stringPaint.setColor(Color.BLACK);
        stringPaint.setTextSize(1.2f);

        switch (rand){
            case 0:
                type = cardType.attack_speed;
                break;
            case 1:
                type = cardType.damage;
                break;
            case 2:
                type = cardType.fire_num;
                break;
            case 3:
                type = cardType.attack_count;
                break;
        }
        switch (i){
            case 1:
                x = Metrics.width/6 - 1f;
                setPosition(Metrics.width/6, y, Metrics.width / 2.5f, Metrics.height/3);
                break;
            case 2:
                x = Metrics.width/2- 1f;
                setPosition(Metrics.width/2, y, Metrics.width / 2.5f, Metrics.height/3);
                break;
            case 3:
                x = Metrics.width*5/6- 1f;
                setPosition(Metrics.width*5/6, y, Metrics.width / 2.5f, Metrics.height/3);
                break;
            default:
                break;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, null, dstRect, null);

        canvas.scale(1/3f, 1/3f, x, y+2f);
        canvas.drawText(textArray[this.type.ordinal()], x, y, stringPaint);
        canvas.scale(3.0f, 3.0f, x, y+2f);
    }

    void apply(Player player){

    }

    void OnClickAction(float x, float y){
        if(this.dstRect.contains(x, y)){
            //apply();
        }
    }
}
