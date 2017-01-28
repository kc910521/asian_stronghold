package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.EnemyShootFog;
import com.ck.ind.finddir.bean.object.Explosion;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/13.
 */
public class JingTower extends AbsEnemyObj implements IEnemy,Cloneable{

    private int lastActionIndex = 0;
    private int wreckIndex = 1;

    public JingTower(SurfaceView surfaceView){
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.jingtower_mov01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.jingtower_mov02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.jingtower_mov03)
        };
        attackBitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.jingtower_att01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.jingtower_att02)
        };
        super.resizeBitMapBach(runningBitmap,210,270);
        super.resizeBitMapBach(attackBitmaps,210,270);
        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.HP = this.maxHp = 550;
        this.attackRange = ImageTools.positionConvert(450);
        this.attackInterval = 2500;
        this.attackPower = 3;
        this.meLeeRange = 1;
        this.buildingAttPower = 12;
        this.runSpeed = ImageTools.positionConvert(1.0);
        this.bloodstainInHurt = false;
    }

    @Override
    protected boolean attackAction() {
        if (this.shotActionIndex+1 >= attackBitmaps.length ){//射击的最后动作完成
            Itower.effectHpValue(-this.attackPower);
            this.lastAttackTime = System.currentTimeMillis();
            //this.shotActionIndex = 0;
            //烟起
            EnemyShootFog explosion = (EnemyShootFog)ObjectFactory.initObjectFactory(this.surfaceView).createEnemyShootFog(EnemyShootFog.class,
                    x + (this.getSize() >> 2),
                    y + (this.getHeight() >> 2)
            );
            /*new Explosion(
                    this.surfaceView,new Bitmap[]{
                        BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.shootfog_1)
                    },
                    x-BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.shootfog_1).getWidth()+15,
                    y);*/
            MainScene.findMainScence(this.surfaceView).getObjSenceList().add(explosion);
            this.shotActionIndex = -1;
            return true;
        }else{
            shotActionIndex ++;
        }
        x -= this.runSpeed;
        return false;
    }

    @Override
    protected boolean meleeAttackAction() {
        if (this.attackBitmaps != null){
            this.runSpeed = 0;
            if (this.shotActionIndex+1 >= attackBitmaps.length ){
                //射击的最后动作完成
                Itower.effectHpValue(-this.buildingAttPower);
                this.lastAttackTime = System.currentTimeMillis();
                this.shotActionIndex = -1;
                return true;
            }
            shotActionIndex ++;
        }
        return false;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public IEnemy clone() {
        IEnemy iEnemy = null;
        try {
            iEnemy = (IEnemy) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return iEnemy;
    }

    // override generate method of remains
    @Override
    protected IRemains generateRemain() {
        return WreckFactory.initWFactory(this.surfaceView).produceCrasherWreck(this.x, this.y, this.getSize(),this.getHeight(),wreckIndex);
    }

}
