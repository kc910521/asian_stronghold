package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public class Bullet extends AbsBullet implements Cloneable{




    public Bullet(SurfaceView gameView,float x,float y,float sx,float sy,int powerKeyDown
    ){


        super(gameView, x, y, sx, sy, powerKeyDown);

        super.initBulletAttr(
                new Bitmap[]{
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball),
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball_mov2),
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball_mov3)
                }
                ,
                new Bitmap[]{
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ball_exp1)
                }
        );
        super.resizeMissileBitmap(this.bitmap,10,10);
        //super.resizeMissileBitmap(this.expBitmaps,69,65);
        this.attackPower = 11;
        attackInterval = 3000l;
        this.oid = Constant.CATAPULT_ID;
    }

    @Override
    protected void goTrace() {
        this.x += sx * t;
        this.y += Constant.G *t *t+sy *t + sx;// -49*this.x+2*7*this.x-1
    }


    @Override
    public AbsBullet clone() throws CloneNotSupportedException {
        return (AbsBullet) super.clone();
    }


    @Override
    public int getAttackPower() {
        return super.getAttackPower()+getWpLevel();
    }

    @Override
    public void setWpLevel(int wpLevel) {
        super.setWpLevel(wpLevel);
        this.attackPower = 11 + (wpLevel*3);


    }
}
