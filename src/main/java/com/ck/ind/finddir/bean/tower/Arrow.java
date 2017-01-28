package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/22.
 */
public final class Arrow extends AbsBullet implements Cloneable{

    private Itower itower = null;
    private int enemyYSpeed = 0;

    public Arrow(SurfaceView gameView, float x, float y, float sx, float sy, int powerKeyDown) {
        super(gameView, x, y, sx, sy, powerKeyDown);
        attackPower = 1;
        super.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.arrow_plat)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
        super.resizeMissileBitmap(this.bitmap, 14, 10);

        attackInterval = 1000l;
        this.oid = Constant.ARROWS_ID;
        itower = this.mainScene.getTower();

        this.enemyYSpeed = ImageTools.positionConvert((3*2)-10);
    }

    @Override
    protected void goTrace() {
        if (this.fromEnemy){
            this.x -= this.sx;
            this.y += this.enemyYSpeed;
            Log.i("msg","==========================+++enemy x=========this.sx:"+this.sx+",H:"+Constant.SCREEN_HEIGHT);
        }else{
            this.x += this.sx;
            this.y -= this.sy;//(powerKeyDown * 2)-10;
        }

        if(this.x<=-50 || this.x >=(Constant.SCREEN_WIDTH+40) || this.y<-30 || this.y>=Constant.SCREEN_HEIGHT+30){
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
    protected void bulletOnGround() {
        //super.bulletOnGround();
        explodeFlag = true;
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
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.arrow_plat)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
    }
}
