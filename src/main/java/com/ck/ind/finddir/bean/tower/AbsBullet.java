package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;
import android.graphics.Matrix;
import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.Explosion;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Iterator;
import java.util.List;

/**
 * Created by KCSTATION on 2015/8/19.
 */
public abstract class AbsBullet implements Cloneable{

    //database id
    protected String oid = "";
    protected SurfaceView gameView = null;
    //position x,y
    protected float x = 0;
    protected float y = 300;

    //running bitmap
    protected Bitmap[] bitmap;
    //running bitmap index
    protected int goBitmapIndex = -1;
    //exploded bitmap
    //protected Bitmap[] expBitmaps;
    protected MainScene mainScene = null;

    //speed x,y...
    protected  float sx;
    protected  float sy;

    protected float t = 0;
    private float tmInterval = 0.3f;
    //bitmap getHeight
    protected int size;
    //bullet attack power
    protected int attackPower;
    //press power
    protected int powerKeyDown = 0;

    protected long attackInterval = 1000l;



    //�Ƿ����
    protected boolean explodeFlag = false;
    protected Explosion explosion = null;

    protected Boolean fromEnemy = false;
    //weapon level
    protected int wpLevel = 1;

    private int minBoomHeight = 0;

    protected ObjectFactory objectFactory = null;



    protected AbsBullet(SurfaceView gameView,
                  float x,float y,float sx,float sy,int powerKeyDown
    ){
        this.gameView = gameView;

        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        if(powerKeyDown>10 || powerKeyDown<0){
            powerKeyDown = 10;
        }
        this.sx *= powerKeyDown;
        this.powerKeyDown = powerKeyDown;
        this.mainScene = MainScene.findMainScence(this.gameView);
        this.minBoomHeight = -ImageTools.positionConvert(200);
        this.tmInterval = ImageTools.positionConvert(0.3f);
        this.objectFactory = ObjectFactory.initObjectFactory(this.gameView);
    }

    protected void resizeMissileBitmap(Bitmap[] bitmaps, int width, int height){
        if (bitmaps != null && bitmaps.length > 0){
            ImageTools.resizeBitMapBachBeRule(bitmaps,width,height);
        }

    }

    //��ʼ��������ͼ��
    protected void initBulletAttr(Bitmap[] bulletBitmap,Bitmap[] expBitmaps){
        this.bitmap = bulletBitmap;
        //this.expBitmaps = expBitmaps;
        this.size = bitmap[0].getHeight();
    }
    //���������+��ը
    protected void bulletOnGround(){
        explosion = this.objectFactory.createBoomObject(Explosion.class,x,y,null);// new Explosion(gameView,expBitmaps,x,y);
        explodeFlag = true;
    }
    //MAIN:��������й켣
    protected abstract void goTrace();


    public void go(){


        //
        if (explodeFlag){// &&
            List<AbsBullet> listTmp = this.mainScene.getBulletList();
            synchronized (listTmp){
                listTmp.remove(this);
            }

            //gameView.getBoomEffectList().add(explosion);
            if(explosion!=null){
                this.mainScene.getObjSenceList().add(explosion);
            }

        }else{
            goTrace();
            if( x >= (Constant.SCREEN_WIDTH + (Constant.SCREEN_WIDTH>>2))
                    || y >= (Constant.SCREEN_HEIGHT + 25)
                    || x <= (Constant.MOVE_X_OFFSET_MAX_L )
                    || y <= this.minBoomHeight  ){// || y>-Constant.SCREEN_HEIGHT
                // Log.i("boom",(x>=(Constant.SCREEN_HEIGHT-55))+","+(y>=(Constant.SCREEN_WIDTH-100)));
                Log.i("mtest","out of in father,x:"+x+",y:"+y+",x max:"+(Constant.SCREEN_WIDTH ));
                this.bulletOnGround();
                return;
            }else{
                this.calCauseDamange();
                t += tmInterval;

            }
            //this.go();
            //���������߶���
            if (this.goBitmapIndex>=this.bitmap.length-1){
                this.goBitmapIndex= 0;
            }else{
                this.goBitmapIndex++;
            }

            //canvas.drawBitmap(this.bitmap[this.goBitmapIndex],x- Constant.MOVE_X_OFFSET,y,paint);
        }

    }

    /**
     * �жϻ��жԷ�
     * ���Զ���Щ������˺�
     * ֻ������Ƿ�����Ҫ����
     */
    protected void calCauseDamange(){
        Iterator<IEnemy> enemyIterator = this.mainScene.getEnemyList().iterator();
        while (enemyIterator.hasNext()){
            IEnemy enemy = enemyIterator.next();
            if (this.x>enemy.getX() && this.y>enemy.getY() && this.x<(enemy.getX()+enemy.getSize()) && this.y < enemy.getY()+enemy.getHeight()){
                //Log.i("enemy die", this.x + "," + enemy.getX());//
                synchronized (this.mainScene.getEnemyList()){
                    enemy.getDamange(this.getAttackPower());
                    //this.mainScene.getEnemyList().remove(enemy);
                }

                this.bulletOnGround();
            }
        }
    }





    public void drawSelf(Canvas canvas,Paint paint){

/*        if (explodeFlag){// &&
            List<AbsBullet> listTmp = this.mainScene.getBulletList();
            synchronized (listTmp){
                listTmp.remove(this);
            }

            //gameView.getBoomEffectList().add(explosion);
            if(explosion!=null){
                this.mainScene.getObjSenceList().add(explosion);
            }

        }else{

            this.go();
            //���������߶���
            if (this.goBitmapIndex>=this.bitmap.length-1){
                this.goBitmapIndex= 0;
            }else{
                this.goBitmapIndex++;
            }

            canvas.drawBitmap(this.bitmap[this.goBitmapIndex],x- Constant.MOVE_X_OFFSET,y,paint);
        }*/
        if (this.goBitmapIndex >= 0 && this.bitmap.length > this.goBitmapIndex){
            canvas.drawBitmap(this.bitmap[this.goBitmapIndex],x- Constant.MOVE_X_OFFSET,y,paint);
        }else{
            //�Է��������һ�ν�������
        }

    }


    public Long getAttackInterval() {
        return attackInterval;
    }

    /**
     *
     * @param x
     * @param y
     * @param sx speed in x
     * @param sy speed in y
     */
    public void setPositionAndSpeed(int x,int y,float sx,float sy){
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;

    }

    @Override
    public AbsBullet clone() throws CloneNotSupportedException {
        return (AbsBullet) super.clone();
    }

    public String getOid() {
        return oid;
    }


    public void setFromEnemy(Boolean fromEnemy) {
        this.fromEnemy = fromEnemy;
    }

    public void setPowerKeyDown(int powerKeyDown) {

        if(powerKeyDown>10 || powerKeyDown<0){
            powerKeyDown = 10;
        }
        //this.sx *=powerKeyDown;
        this.powerKeyDown = powerKeyDown;
        this.reinitImageRes();
    }
    //���¼���ͼƬ
    protected  void reinitImageRes(){
    }

    //����Ƕ�
    protected int exeOutPosition(int power){
        return -((power-5)*10);

    }

    public int getWpLevel() {
        return wpLevel;
    }

    public void setWpLevel(int wpLevel) {
        this.wpLevel = wpLevel;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
