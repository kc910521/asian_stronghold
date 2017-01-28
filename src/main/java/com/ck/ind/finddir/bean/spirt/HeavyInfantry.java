package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.Explosion;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/13.
 */
public class HeavyInfantry extends AbsEnemyObj implements IEnemy,Cloneable{

    private int lastActionIndex = 0;

    public HeavyInfantry(SurfaceView surfaceView){
        super(surfaceView);
        //this.surfaceView = surfaceView;
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.infantry_mov03),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.infantry_mov02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.infantry_mov01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.infantry_mov02)
        };
        attackBitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.infantry_mov03),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.infantry_att1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.infantry_att2)
        };
        super.resizeBitMapBach(runningBitmap,32,33);
        super.resizeBitMapBach(attackBitmaps,32,33);

        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.HP = this.maxHp = 9;
/*        this.attackRange = 450;
        this.attackInterval = 1000;*/
        this.attackInterval = 1000;
        this.meLeeRange = 1;
        this.attackPower = 2;
        this.buildingAttPower = 5;
        this.runSpeed = ImageTools.positionConvert(2.0);
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
    public IEnemy clone() {
        IEnemy iEnemy = null;
        try {
            iEnemy = (IEnemy) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return iEnemy;
    }


}
