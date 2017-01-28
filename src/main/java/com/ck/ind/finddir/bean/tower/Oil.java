package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by KCSTATION on 2015/9/1.
 */
public class Oil extends AbsBullet  {

    private int maxThrowY = 0;
    private float t_interval = 3.0f;
    //投掷角度和x轴相关 %3=0 then =1
    private int throwRation = 0;
    private Random randomPower = null;

    //爆炸范围和伤害
    private int boomRadius = 15;
    private final int boomDmg = 10;

    public Oil(SurfaceView gameView, float x, float y, float sx, float sy, int powerKeyDown) {
        super(gameView, x, y, sx, sy, powerKeyDown);
        Log.i("oil", "x:" + x + ",y" + y);
        attackPower = 35;
        super.initBulletAttr(
                new Bitmap[]{
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.oilbomb_01),
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.oilbomb_02),
                        BitmapFactory.decodeResource(gameView.getResources(), R.drawable.oilbomb_03)
                }
                ,
                null
        );
        super.resizeMissileBitmap(this.bitmap, 15, 15);
        attackInterval = 5000l;
        this.size = bitmap[0].getHeight();
        this.oid = Constant.OIL_ID;
        randomPower = new Random();
        this.boomRadius = ImageTools.positionConvert(15);
    }

    @Override
    protected void goTrace() {

        if (this.y < maxThrowY){
            this.x += this.sx;
            this.y += (Constant.G * (2 + this.throwRation)) *t *t+sy *t+0.5f;;//a = 0.02 上限 0.08下线
        }else{
            this.bulletOnGround();
        }

    }

    @Override
    protected void bulletOnGround() {
        super.bulletOnGround();
        //落地再造成伤害
        Iterator<IEnemy> enemyIterator = this.mainScene.getEnemyList().iterator();

        while (enemyIterator.hasNext()){
            IEnemy enemy = enemyIterator.next();
            //屏幕一半范围内,收到爆炸伤害
            if (enemy.getX() <= (Constant.SCREEN_WIDTH>>1) ){

                if (this.x + this.boomRadius >enemy.getX()
                        && this.y + this.boomRadius >enemy.getY()
                        && this.x - this.boomRadius < (enemy.getX()+enemy.getSize())
                        && this.y - this.boomRadius < enemy.getY()+enemy.getHeight()){
                    //Log.i("enemy die", this.x + "," + enemy.getX());//
                    synchronized (this.mainScene.getEnemyList()){
                        enemy.getDamange(this.boomDmg);
                        //this.mainScene.getEnemyList().remove(enemy);
                    }

                }

            }

        }


    }

    @Override
    public void setPositionAndSpeed(int x, int y, float sx, float sy) {
        super.setPositionAndSpeed(x, y, sx, sy);

        //mainScene.getTower().getHeight() + (Constant.EXPLOSITION_Y/10)
        this.maxThrowY = mainScene.getTower().getY() + (mainScene.getTower().getHeight()>>1) + randomPower.nextInt((mainScene.getTower().getHeight()>>1)+
                ImageTools.positionConvert(30));
        this.throwRation = this.x%3 == 0?1:0;
        this.goBitmapIndex = randomPower.nextInt(this.bitmap.length);
    }

    @Override
    public AbsBullet clone() throws CloneNotSupportedException {
        return (AbsBullet) super.clone();
    }


}
