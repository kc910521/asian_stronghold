package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.EnemyFactory;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/17.
 */
public class Crasher extends AbsEnemyObj implements IEnemy,Cloneable{


    private boolean isAlive = true;
    private int wreckIndex = 0;

    public Crasher(SurfaceView surfaceView){
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.crash_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.crash_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.crash_3)
        };
        attackBitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.crash_att2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.crash_att1)
        };
        super.resizeBitMapBach(runningBitmap,98,90);
        super.resizeBitMapBach(attackBitmaps,98,90);

        this.height = this.runningBitmap[0].getHeight();
        this.size = this.runningBitmap[0].getWidth();
        this.runSpeed = ImageTools.positionConvert(3);
        this.buildingAttPower = 19;
        this.HP = this.maxHp = 30;
        this.attackInterval = 3000;
        this.meLeeRange = 1;
        this.bloodstainInHurt = false;
    }


    @Override
    protected boolean attackAction() {
        Itower.effectHpValue(-this.attackPower);
        this.destory();
        return true;
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
    public IEnemy clone() throws CloneNotSupportedException {
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
        //generate passenger if hp in dead > -maxHp/10
        if (this.HP > - (this.maxHp/10)){
            EnemyFactory ef = EnemyFactory.findFactory(null);
            MainScene.findMainScence(null).getEnemyList().add(ef.createPointObject(Boy.class, this.x, this.y));
            MainScene.findMainScence(null).getEnemyList().add(ef.createPointObject(Boy.class, this.x, this.y + (this.size>>1)));
            MainScene.findMainScence(null).getEnemyList().add(ef.createPointObject(Boy.class, this.x, this.y));
            MainScene.findMainScence(null).getEnemyList().add(ef.createPointObject(Boy.class, this.x + (this.height>>1), this.y +(this.size>>2)));
        }
        //generate wreck
        return WreckFactory.initWFactory(this.surfaceView).produceCrasherWreck(this.x, this.y, this.getSize(),this.getHeight(),wreckIndex);
    }
}
