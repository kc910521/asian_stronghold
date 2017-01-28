package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/5.
 */
public class Boy extends AbsEnemyObj implements IEnemy,Cloneable {//,

    //private int actionIndex = 0;

    public Boy(SurfaceView surfaceView) {
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.pikemen_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.pikemen_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.pikemen_3)
        };
        attackBitmaps = new Bitmap[]{
/*                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.pikemen_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.pikemen_2),*/
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.pikemen_att_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.pikemen_att_2)
        };
        super.resizeBitMapBach(runningBitmap,32,32);
        super.resizeBitMapBach(attackBitmaps,32,32);

        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();
        this.buildingAttPower = 8;

        this.runSpeed = ImageTools.positionConvert(3);

        this.HP = this.maxHp = 3;

        this.attackInterval = 1000;
        this.meLeeRange = 1;
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
    protected void afterUnderAttack() {
        if (!this.inMelee){
            this.runSpeed = ImageTools.positionConvert(4.5);
        }

    }

    @Override
    public void setCurPostion(int x, int y) {
        super.setCurPostion(x, y);
        this.runSpeed = ImageTools.positionConvert(3);
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



}
