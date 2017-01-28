package com.ck.ind.finddir.bean.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.LittleFog;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/9/21.
 */
public final class DragonRoc extends AbsBullet implements Cloneable{

    private Itower itower = null;
    private ObjectFactory objectFactory = null;
    private float t_interval = 3.0f;
    private float t_int_interval ;

    private int maxHeight = 0;

    public DragonRoc(SurfaceView gameView, float x, float y, float sx, float sy, int powerKeyDown) {
        super(gameView, x, y, sx, sy, powerKeyDown);
        attackPower = 8;
        super.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fdragon_plat1)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
        super.resizeMissileBitmap(this.bitmap, 15, 12);
        attackInterval = 1700l;
        this.oid = Constant.DRAGON_ID;
        itower = this.mainScene.getTower();
        objectFactory = ObjectFactory.initObjectFactory(gameView);
        this.maxHeight = -ImageTools.positionConvert(300);
        this.t_int_interval = ImageTools.positionConvert(0.8f);
        //throw line
        //drawLine = new DrawLine(gameView);
    }


    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        super.drawSelf(canvas, paint);

    }

    @Override
    protected void goTrace() {
        if (this.fromEnemy){
            this.x -= this.sx;
            this.y += (7*2)-10;
        }else{
            this.x += this.sx;
            this.y += ((0.08f-(this.powerKeyDown/200f)) * Constant.SCREEN_RATION * this.t_interval *this.t_interval+0.4)+((-1) *this.t_interval)+1;//a = 0.02 上限 0.08下线
            //            //0.5*x^2-2*x+20
            //Log.i("rline",this.powerKeyDown+" is:"+(0.08f-(this.powerKeyDown/200f)));
            this.t_interval += this.t_int_interval;

            //this.y -= (powerKeyDown*2)-10;
        }

        if(this.x<=-50 || this.x >=Constant.SCREEN_WIDTH || this.y<this.maxHeight || this.y>=Constant.SCREEN_HEIGHT+30){
            this.bulletOnGround();
        }
        //show smoke
        MainScene.findMainScence(this.gameView).getObjSenceList().add(
                objectFactory.createObjectFog(LittleFog.class, this.x, this.y)
        );
    }


    @Override
    public void setPositionAndSpeed(int x, int y, float sx, float sy) {
        //限定powerkeydown 模拟火箭随机性
        //因为clone后会访问此方法
        this.powerKeyDown = this.powerKeyDown+(new Random().nextInt(3)-1);

        super.setPositionAndSpeed(x, y, sx, sy);

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
    public AbsBullet clone() throws CloneNotSupportedException {
        return (AbsBullet) super.clone();
    }

    @Override
    protected void reinitImageRes() {
        this.initBulletAttr(
                ImageTools.rotateBitMapBach(
                        new Bitmap[]{
                                BitmapFactory.decodeResource(gameView.getResources(), R.drawable.fdragon_plat1)
                        },
                        this.exeOutPosition(this.powerKeyDown))
                , null);
    }

}