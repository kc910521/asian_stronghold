package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.EnemyShootFog;
import com.ck.ind.finddir.bean.object.Explosion;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.play.PlayScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/13.
 */
public class Matchlocker extends AbsEnemyObj implements IEnemy,Cloneable{

    private int shootFogXFix = 0;


    public Matchlocker(SurfaceView surfaceView){
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.matchlocker_mov_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.matchlocker_mov_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.matchlocker_mov_3)
        };
        attackBitmaps= new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.matchlocker_att_1),
                null,null,null,
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.matchlocker_att_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.matchlocker_att_1)
        };
        super.resizeBitMapBach(runningBitmap,50,49);
        super.resizeBitMapBach(attackBitmaps,50,49);
        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.HP = this.maxHp = 9;
        this.attackRange = ImageTools.positionConvert(400);
        this.attackInterval = 15000;
        this.attackPower = 8;
        this.buildingAttPower = 8;
        this.runSpeed = ImageTools.positionConvert(1.4);

        this.shootFogXFix = ImageTools.positionConvert(50);
    }

/*    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        this.go(canvas,paint);
        if (this.actionIndex >= runningBitmap.length ){
            this.actionIndex = 0;
        }
        canvas.drawBitmap(ImageTools.adjustPhotoRotation(runningBitmap[actionIndex], this.rotateRation), this.x- Constant.MOVE_X_OFFSET, this.y, paint);
        this.actionIndex++;
    }*/

    @Override
    protected boolean attackAction() {
        if (this.shotActionIndex+1 >= attackBitmaps.length ){//射击的最后动作完成
            Itower.effectHpValue(-this.attackPower);
            this.lastAttackTime = System.currentTimeMillis();
            //this.shotActionIndex = 0;
            //烟起
            EnemyShootFog explosion = (EnemyShootFog)ObjectFactory.initObjectFactory(this.surfaceView).createEnemyShootFog(EnemyShootFog.class,
                    x - this.shootFogXFix ,
                    y + (this.getHeight() >> 2)
            );

            /*new Explosion(this.surfaceView,new Bitmap[]{
                    BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.shootfog_1)
            },x-BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.shootfog_1).getWidth()+15,y);*/

            MainScene.findMainScence(this.surfaceView).getObjSenceList().add(explosion);
            this.shotActionIndex = -1;
            return true;
        }else{
            shotActionIndex ++;
        }
        return false;
    }

    @Override
    public int destory() {
        IRemains pikemenRemains = WreckFactory.initWFactory(this.surfaceView).producePikeWreck(this.x+18, this.y+18);
        IRemains.remainList.add(pikemenRemains);
        this.isAlive = false;
        if (MainScene.findMainScence(null) == null){
            PlayScene.findMainScence(this.surfaceView).getEnemyList().remove(this);
        }else{
            MainScene.findMainScence(this.surfaceView).getEnemyList().remove(this);
        }
        return 0;
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
