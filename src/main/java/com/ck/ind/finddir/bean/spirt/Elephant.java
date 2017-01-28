package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/5.
 */
public class Elephant extends AbsEnemyObj implements IEnemy,Cloneable {//,

    //private int actionIndex = 0;
    private int wreckIndex = 2;

    public Elephant(SurfaceView surfaceView) {
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.elephant_mov02),

                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.elephant_mov02),

                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_mov03),

                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_mov03),

                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_mov04),

                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_mov04),

                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_mov05),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_mov05)
        };
        attackBitmaps = new Bitmap[]{
/*                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.pikemen_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.pikemen_2),*/

                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att03),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att03),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.elephant_att03)
        };
        super.resizeBitMapBach(runningBitmap,210,180);
        super.resizeBitMapBach(attackBitmaps,210,180);

        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();
        this.buildingAttPower = 45;

        this.runSpeed = ImageTools.positionConvert(5);
        this.HP = this.maxHp = 850;

        this.attackInterval = 1000;
        this.meLeeRange = 1;
        //this.bloodstainInHurt = false;
    }


/*    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        //this.x +=+ Constant.MOVE_X_OFFSET;
        this.go(canvas,paint);
        if (this.actionIndex >= runningBitmap.length ){
            this.actionIndex = 0;
        }
        canvas.drawBitmap(ImageTools.adjustPhotoRotation(runningBitmap[actionIndex],this.rotateRation), this.x- Constant.MOVE_X_OFFSET, this.y, paint);

        this.actionIndex++;
    }*/

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
    public IEnemy clone() {
        IEnemy iEnemy = null;
        try {
            iEnemy = (IEnemy) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return iEnemy;
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


    // override generate method of remains
    @Override
    protected IRemains generateRemain() {
        return WreckFactory.initWFactory(this.surfaceView).produceCrasherWreck(this.x, this.y, this.getSize(),this.getHeight(),wreckIndex);
    }
}
