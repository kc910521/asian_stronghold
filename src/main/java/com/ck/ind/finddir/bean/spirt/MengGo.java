package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/17.
 */
public class MengGo extends AbsEnemyObj implements IEnemy,Cloneable{


    private boolean isAlive = true;
    //进入战场走的米数
    private int runX = 0;
    private boolean inFlee = false;

    private int fleeSpeed = -2;

    public MengGo(SurfaceView surfaceView){
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.menggo_mov03),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.menggo_mon02),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.menggo_mov01),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.menggo_mon02)
        };
        attackBitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.menggo_mon02)
        };
        super.resizeBitMapBach(runningBitmap,32,34);
        super.resizeBitMapBach(attackBitmaps,32,34);
        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.runSpeed = ImageTools.positionConvert(2.0);
        this.buildingAttPower = 18;
        this.HP = this.maxHp = 12;
        this.meLeeRange = 1;
    }


    @Override
    protected boolean attackAction() {
        Itower.effectHpValue(-this.attackPower);
        this.destory();
        return true;
    }

//MainScene.sceneBean.mg_isdead = true;
    @Override
    protected void goSpecLogic() {
        if (this.inFlee){
            if (this.x>Constant.SCREEN_WIDTH ){
                this.destory();
            }else{
                this.runSpeed = this.fleeSpeed;
            }
        }else{
            if (runX >= Constant.SCREEN_WIDTH/4){
                this.inFlee = true;
            }else{
                runX += this.runSpeed;
            }
        }
    }

    @Override
    public void getDamange(int damagePoint) {
        super.getDamange(damagePoint);
        if (this.HP <=0){
            MainScene.sceneBean.mg_isdead = true;
        }
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

}
