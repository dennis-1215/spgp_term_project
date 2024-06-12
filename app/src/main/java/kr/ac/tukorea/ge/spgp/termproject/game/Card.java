package kr.ac.tukorea.ge.spgp.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.termproject.R;
import kr.ac.tukorea.ge.spgp.termproject.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.termproject.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.GameView;
import kr.ac.tukorea.ge.spgp.termproject.framework.view.Metrics;

/*   - 공격속도 : 기본 3초에 1회 공격, lv당 10%의 쿨타임 감소로 3 * 0.9^lv
   - (lv0 = 3.0, lv1 = 2.7, lv2 = 2.43 ... lv17 = 0.50 이 최대)
   - 공격력 : 기본 데미지 50, lv당 20%의 공격력 증가 50 * 1.2^lv (업그레이드에 제한이 없다.)
   - 투사체 개수 : 업그레이드 당 1개씩 추가 최대 7개까지 증가
   - (lv0 = 정면, lv1,lv2 = 각 좌측/우측 10도 방향으로 추가발사, lv3,lv4 = 20도 ... lv7,lv8 = 40도)
   - 발사횟수 : 업그레이드당 1회 추가(최대 8회)
*/

public class Card extends Sprite {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final Paint stringPaint;
    public enum cardType {attack_speed, damage, fire_num, attack_count, castle_hpup, castle_recovery, castle_defup};
    public cardType type;
    private final Player m_player;
    private final Castle m_castle;

    public float x;
    public final float y = Metrics.height/2;

    protected static int[] levelArray;

    private static final int[] resIds = {
            R.mipmap.fire_speed, R.mipmap.damage_up, R.mipmap.double_shot, R.mipmap.more_shot, R.mipmap.castle_maxhp, R.mipmap.castle_recover, R.mipmap.castle_def_up
    };
    public Card(int i, int rand, Player player, Castle castle) {
        super(resIds[rand]);
        stringPaint = new Paint();
        stringPaint.setColor(Color.BLACK);
        stringPaint.setTextSize(1.2f);

        m_player = player;
        m_castle = castle;

        levelArray = new int[]{m_player.attackSpeedLevel, m_player.damageLevel, m_player.fireNumLevel, m_player.attackCountLevel, m_player.castleHpLevel, m_player.castleRecoveryLevel, m_player.castleDefLevel};

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
            case 4:
                type = cardType.castle_hpup;
                break;
            case 5:
                type = cardType.castle_recovery;
                break;
            case 6:
                type = cardType.castle_defup;
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

        canvas.scale(1/2f, 1/2f, x, y+2f);
        canvas.drawText("LV. " + String.valueOf(levelArray[this.type.ordinal()]), x+0.5f, y+1f, stringPaint);
        canvas.scale(2f, 2f, x, y+2f);
    }

    void apply(Player player, Castle castle){
        switch (type){
            case attack_speed:
                player.attackSpeedLevel += 1;
                player.BULLET_INTERVAL *= 0.95;
                break;

            case damage:
                player.damageLevel += 1;
                player.damage *= 1.2;
                break;

            case fire_num:
                player.fireNumLevel += 1;
                break;

            case attack_count:
                player.attackCountLevel += 1;
                break;

            case castle_hpup:
                player.castleHpLevel += 1;
                castle.setMaxHp((player.castleHpLevel+1) * 100);
                castle.setHp(castle.getHp() + 100);
                break;

            case castle_recovery:
                player.castleRecoveryLevel += 1;
                castle.recoveryLevel += 1;
                castle.RECOVERY_INTERVAL *= 0.9;
                break;

            case castle_defup:
                player.castleDefLevel += 1;
                castle.def += 0.4;
                break;
        }
    }

    void OnClickAction(float coord[]){
        if(this.dstRect.contains(coord[0], coord[1])){
            Log.d(TAG, "choice " + type);
            apply(m_player, m_castle);
            Scene.pop();
        }
    }
}
