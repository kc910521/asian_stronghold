package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.List;

/**
 * Created by KCSTATION on 2015/8/13.
 */
public class Archer extends AbsEnemyObj implements IEnemy,Cloneable{


    public Archer(SurfaceView surfaceView){
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.archer_mov_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.archer_mov_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.archer_mov_3),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.archer_mov_2)
                
        };
        attackBitmaps= new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.archer_att_01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.archer_att_02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.archer_att_03),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.archer_att_04)
        };
        super.resizeBitMapBach(runningBitmap,35,35);
        super.resizeBitMapBach(attackBitmaps,35,35);

        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.HP = this.maxHp = 2;
        this.attackRange = ImageTools.positionConvert(420);
        this.attackInterval = 5000;
        this.attackPower = 8;
        this.buildingAttPower = 2;

        this.runSpeed = ImageTools.positionConvert(2);
    }



    @Override
    protected boolean attackAction() {

        if (this.shotActionIndex >= (attackBitmaps.length-1) ){//射击的最后动作完成
            //Itower.effectHpValue(-this.attackPower);
            //shoot arrows
            List<AbsBullet> arrows = null;
            try {
                arrows = MainScene.findMainScence(this.surfaceView).getBulletFactory().createArrows(3, 1, true, this.x, this.y);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            MainScene.findMainScence(this.surfaceView).getBulletList().addAll(arrows);
            this.lastAttackTime = System.currentTimeMillis();
            this.shotActionIndex = -1;
//            canvas.drawBitmap(attackBitmaps[shotActionIndex], this.x- Constant.MOVE_X_OFFSET, this.y, paint);
            //this.shotActionIndex++;
            return true;
        }else{
            //canvas.drawBitmap(attackBitmaps[shotActionIndex], this.x- Constant.MOVE_X_OFFSET, this.y, paint);
            this.shotActionIndex++;
        }
        return false;
    }

    @Override
    public void setCurPostion(int x, int y) {
        super.setCurPostion(x, y);
        this.runSpeed = ImageTools.positionConvert(2);
    }

    @Override
    protected void afterUnderAttack() {
        this.runSpeed = ImageTools.positionConvert(1);
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
