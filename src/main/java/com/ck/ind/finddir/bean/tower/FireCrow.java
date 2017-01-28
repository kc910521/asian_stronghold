package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.LittleFog;
import com.ck.ind.finddir.factory.BulletFactory;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/22.
 */
public final class FireCrow extends AbsBullet implements Cloneable{

    private Itower itower = null;
    //武器特性：分发子火箭
    private boolean isDispersed = false;
    //shoot timestamp
    private Long lastShootTS;

    private ObjectFactory objectFactory = null;


    public FireCrow(SurfaceView gameView, float x, float y, float sx, float sy, int powerKeyDown) {
        super(gameView, x, y, sx, sy, powerKeyDown);
        attackPower = 17;
/*        super.initBulletAttr(
                new Bitmap[]{
                        super.adjustPhotoRotation(BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fcrow_plat),
                                this.exeOutPosition(this.powerKeyDown))}
                ,null);*/
        super.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fcrow_plat)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
        super.resizeMissileBitmap(this.bitmap,28,20);
        attackInterval = 3000l;
        this.oid = Constant.FIRE_CROW_ID;
        itower = this.mainScene.getTower();
        objectFactory = ObjectFactory.initObjectFactory(this.gameView);
    }

    @Override
    protected void goTrace() {

        if (this.fromEnemy){
            this.x -= this.sx;
            this.y += this.sy;
        }else{
            this.x += this.sx;
            this.y -= this.sy;
        }
        Log.i("firecrow",((System.currentTimeMillis()-lastShootTS)%5)+"");

        if(this.x<=-50 || this.x >=(Constant.SCREEN_WIDTH*2/3) || this.y < -50 || this.y>=Constant.SCREEN_HEIGHT+30){
            this.bulletOnGround();
        }else if(System.currentTimeMillis()-lastShootTS > (2100-(this.getWpLevel()*200)) && !isDispersed){//submissile launch
            isDispersed = true;
            BulletFactory bulletFactory = BulletFactory.initFactory(this.gameView);

            int rocketPKD = 0;
            if (this.powerKeyDown - 2 > 0){
                rocketPKD = this.powerKeyDown - 2;
            }

            try {
                bulletFactory.createRockets(rocketPKD, (int) this.x, (int) this.y,6,false);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }else if((System.currentTimeMillis()-lastShootTS)%5 == 0){
            //show more smoke
            MainScene.findMainScence(this.gameView).getObjSenceList().add(
                    objectFactory.createObjectFog(LittleFog.class, this.x, this.y)
            );
        }
        //show smoke
        MainScene.findMainScence(this.gameView).getObjSenceList().add(
                objectFactory.createObjectFog(LittleFog.class, this.x, this.y)
        );
    }

    @Override
    protected void calCauseDamange() {
        if (this.fromEnemy){
            if (this.x>itower.getX() && this.y>itower.getY() && this.x<(itower.getX()+itower.getWidth()) && this.y < itower.getY()+itower.getHeight()){
                synchronized (this.mainScene.getTower()){
                    Itower.effectHpValue(-this.attackPower);
                    //this.mainScene.getEnemyList().remove(enemy);
                }
                this.bulletOnGround();
            }

        }else{
            super.calCauseDamange();
        }

    }

    @Override
    public void setPositionAndSpeed(int x, int y, float sx, float sy) {
        this.lastShootTS = System.currentTimeMillis();
        super.setPositionAndSpeed(x, y, sx, sy);
    }

    @Override
    public AbsBullet clone() throws CloneNotSupportedException {
        return (AbsBullet) super.clone();
    }

    @Override
    protected void reinitImageRes() {
        this.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fcrow_plat)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
    }

}
