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
 * Created by KCSTATION on 2015/8/5.
 */
public class Messenger extends AbsEnemyObj implements IEnemy,Cloneable {//,

    //private int actionIndex = 0;
    private final int recoverPoint = 50;

    public Messenger(SurfaceView surfaceView) {
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.messenger_mov1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.messenger_mov2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.messenger_mov3),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.messenger_mov4)
        };
        super.resizeBitMapBach(runningBitmap,49,50);

        //攻击力为负值
        this.buildingAttPower = -this.recoverPoint;
        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.runSpeed = -ImageTools.positionConvert(4);
        this.HP = this.maxHp = 7;
        this.isFlyUnit = true;
        this.attackRange = -9999;
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
    protected void goSpecLogic() {
        if (this.x> Constant.SCREEN_WIDTH ){
            this.destory();
        }
    }

    //irritated
    @Override
    public void getDamange(int damagePoint) {
        super.getDamange(damagePoint);
        this.runSpeed = -ImageTools.positionConvert(8);
    }

    @Override
    protected IRemains generateRemain() {
        Itower itower = MainScene.findMainScence(this.surfaceView).getTower();
        if (this.getX() < (itower.getX()+itower.getWidth()+ ImageTools.positionConvert(90) ) ){
            Itower.effectHpValue(-this.buildingAttPower);
        }
        return WreckFactory.initWFactory(this.surfaceView).producePikeWreck(this.x, this.y + (this.height * 9 / 10));
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
