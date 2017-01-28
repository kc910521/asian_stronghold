package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/9/21.
 */
public final class Rocket extends AbsBullet implements Cloneable{

    private Itower itower = null;


    public Rocket(SurfaceView gameView, float x, float y, float sx, float sy, int powerKeyDown) {
        super(gameView, x, y, sx, sy, powerKeyDown);
        attackPower = 6;

/*        super.initBulletAttr(
                new Bitmap[]{
                        super.adjustPhotoRotation(BitmapFactory.decodeResource(gameView.getResources(), R.drawable.rocket_plat),
                                this.exeOutPosition(this.powerKeyDown))}
                ,null);*/
        super.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.rocket_plat)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
        super.resizeMissileBitmap(this.bitmap,18,13);
        attackInterval = 1000l;
        this.oid = Constant.ROCKET_ID;
        itower = this.mainScene.getTower();
    }

    @Override
    protected void goTrace() {
        Log.i("firecrow", "rocket pos" + this.x + "," + this.y);
        if (this.fromEnemy){
            this.x -= this.sx;
            this.y += (7*2)-10;
        }else{
            this.x += this.sx;
            this.y += this.sy;
            Log.i("firecrow", "rocket pos" + this.x+","+this.y);
        }

        if(this.x<=-50 || this.x >=Constant.SCREEN_WIDTH || this.y<-30 || this.y>=Constant.SCREEN_HEIGHT+30){
            this.bulletOnGround();
        }
    }

    @Override
    protected void calCauseDamange() {
        if (this.fromEnemy){
            if (this.x>itower.getX() && this.y>itower.getY() && this.x<(itower.getX()+itower.getWidth()) && this.y < itower.getY()+itower.getHeight()){
                synchronized (this.mainScene.getTower()){
                    Itower.effectHpValue(-this.attackPower);
                    //this.mainScene.getEnemyList().remove(enemy);
                }

                this.bulletOnGround();
            }

        }else{
            super.calCauseDamange();
        }

    }

    @Override
    public AbsBullet clone() throws CloneNotSupportedException {
        return (AbsBullet) super.clone();
    }

    @Override
    protected void reinitImageRes() {
        this.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.rocket_plat)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
    }

}