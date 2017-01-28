package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.Bloodstain;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/5.
 */
public class Bird extends AbsEnemyObj implements IEnemy,Cloneable {//,

    //private int actionIndex = 0;
    private final int recoverPoint = 25;

    private float bird_interval;

    public Bird(SurfaceView surfaceView) {
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.bird_mov1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.bird_mov2)
        };
        super.resizeBitMapBach(runningBitmap,15,10);

        //攻击力为负值
        this.buildingAttPower = -this.recoverPoint;
        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.runSpeed = ImageTools.positionConvert(6);
        this.HP = this.maxHp = 1;
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

    //irritated
    @Override
    public void getDamange(int damagePoint) {
        super.getDamange(damagePoint);
        this.DeadposY = this.y + ImageTools.positionConvert(30);
        isdeadAndFly = true;
    }

    @Override
    protected IRemains generateRemain() {
        Itower itower = MainScene.findMainScence(this.surfaceView).getTower();
        if (this.getX() < (itower.getX()+itower.getWidth()+ ImageTools.positionConvert(20) ) ){
            Itower.effectHpValue(-this.buildingAttPower);
        }
        return WreckFactory.initWFactory(this.surfaceView).producePikeWreck(this.x, this.y + (this.height * 9 / 10));
    }

    @Override
    protected void damageToFlyDraw(){
        if (this.y<=this.DeadposY && this.y > -20 && this.x < Constant.SCREEN_WIDTH && this.HP <= 0){//
            rotateRation+=23;
            this.x -= 1.5 *this.bird_interval;
            this.y += Constant.G *this.bird_interval *this.bird_interval+(-5) *this.bird_interval+0.5f;
            Log.i("pos","x:"+x+",y:"+y+",bird_interval:"+bird_interval);
            this.bird_interval += this.tmInterval;
        }else{
            Itower itower = Itower.initUserTower(this.surfaceView);
            if (this.getX() <= itower.getX()+itower.getWidth() ){
                itower.effectHpValue(this.recoverPoint);
            }
            this.destory();
        }

    }



    @Override
    public int destory() {
        //死后掉落在城市，城市得到生命
        Itower itower = MainScene.findMainScence(this.surfaceView).getTower();
        if (this.HP <= 0 ){
            if (this.x <= (itower.getWidth() + itower.getX())){
                Itower.effectHpValue(this.recoverPoint);
            }else{
                MainScene.findMainScence(this.surfaceView).getObjSenceList().add(
                        ObjectFactory.initObjectFactory(this.surfaceView).createBloodstain(Bloodstain.class, this.x, this.y + this.height * 9 / 10)
                );
            }

        }
        return super.destory();
    }

    @Override
    public void setCurPostion(int x, int y) {
        super.setCurPostion(x, y);
        this.bird_interval = 0;
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
