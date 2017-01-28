package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.Explosion;
import com.ck.ind.finddir.bean.object.ShootFog;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/22.
 */
public final class AutoDefence extends AbsBullet implements Cloneable{


    private int fadeXmile = 0;

    public AutoDefence(SurfaceView gameView, float x, float y, float sx, float sy, int powerKeyDown) {
        super(gameView, x, y, sx, sy, powerKeyDown);
        attackPower = 2;
        super.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.tuhuoqiang)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
        super.resizeMissileBitmap(this.bitmap, 4, 4);

        attackInterval = 800l;
        this.oid = Constant.AUTO_DEF_ID;
        this.fadeXmile = Constant.SCREEN_WIDTH * 7 / 8;

    }

    @Override
    protected void goTrace() {
        this.x += this.sx;
        this.y += this.sy;//

        if(this.x >= fadeXmile || this.y<-30 || this.y>=Constant.SCREEN_HEIGHT+30){
            this.bulletOnGround();
        }
    }

/*    @Override
    protected void calCauseDamange() {
        //super.calCauseDamange();
        //override with blank body for games efficiency
    }*/

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
    public void setPositionAndSpeed(int x, int y, float sx, float sy) {
        super.setPositionAndSpeed(x, y, sx, sy);
        ShootFog explosion = (ShootFog) this.objectFactory.createShootFog(ShootFog.class,x,y);
        MainScene.findMainScence(this.gameView).getObjSenceList().add(explosion);
    }
}
