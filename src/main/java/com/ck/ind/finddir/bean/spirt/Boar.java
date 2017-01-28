package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/5.
 */
public class Boar extends AbsEnemyObj implements IEnemy,Cloneable {//,

    //private int actionIndex = 0;
    private final int recoverPoint = 65;

    private Long actionTmStamp = 0L;

    private boolean isUnderAtt = false;


    public Boar(SurfaceView surfaceView) {
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.boar_mov1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.boar_mov2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.boar_mov3)
        };
        super.resizeBitMapBach(runningBitmap,32,30);

        //攻击力为负值
        this.buildingAttPower = -this.recoverPoint;
        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.runSpeed = ImageTools.positionConvert(1);
        this.HP = this.maxHp = 6;
        this.attackRange = -9999;
    }


    @Override
    public void onLogic() {
        super.onLogic();
        //不被攻击过6s 一个动作
        if (!this.isUnderAtt  && (System.currentTimeMillis()-this.actionTmStamp) > 6000){
            this.actionTmStamp = System.currentTimeMillis();
            this.runSpeed = ImageTools.positionConvert((new Random()).nextInt(3)-1);
        }
    }

    private void wildsAI(){

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

    //irritated
    @Override
    protected void afterUnderAttack() {
        this.isUnderAtt = true;
        this.runSpeed = ImageTools.positionConvert(5);
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
