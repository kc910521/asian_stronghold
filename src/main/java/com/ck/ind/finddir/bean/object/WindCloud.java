package com.ck.ind.finddir.bean.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;
import com.ck.ind.finddir.ui.MyPaint;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/17.
 */
public class WindCloud implements IObjectScene,Cloneable {

    float x;
    float y;
    int animationIndex = -1;
    Bitmap[] bitmaps ;
    SurfaceView surfaceView;
    Random randomLife = new Random();
    long createTimeStamp = 0;
    long lifePrior = 0;//life still
    Paint paint1 = new Paint();
    ObjectFactory objectFactory = null;

    //position when createdS
    private float bornX;
    private float bornY;
    private IMainScene mainScene = null;
    private float speedX = 0;

    public WindCloud(SurfaceView gameView){
        this.surfaceView = gameView;
        this.bitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.windcloud)
        };
        ImageTools.resizeBitMapBachBeRule(this.bitmaps, 120, 60);
        objectFactory = ObjectFactory.initObjectFactory(gameView);
        this.speedX = ImageTools.positionConvert(1.0f);
    }


    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        if (animationIndex>=0){
            canvas.drawBitmap(bitmaps[animationIndex], this.x - Constant.MOVE_X_OFFSET, this.y, paint1);
        }
    }

    @Override
    public void onLogic() {
        this.animationIndex ++;
        if (this.animationIndex > (bitmaps.length-1)){
            this.animationIndex = 0;
        }
        if (createTimeStamp==0 && paint1.getAlpha()<=200){//step 1 ,fadein

            paint1.setAlpha(paint1.getAlpha() + 10);
            if ((paint1.getAlpha()+10) > 200){//last loop,get cloud lifestamp

                lifePrior = this.findLifeStamp();
            }
        }else{
            if (lifePrior>System.currentTimeMillis()){
                //canvas.drawBitmap(bitmaps[animationIndex], this.x- Constant.MOVE_X_OFFSET, this.y, paint1);
            }else{//life is over
                if (paint1.getAlpha()<=10){
                    //initial position x always be maintower's X value
                    objectFactory.createObject(WindCloud.class, (int)this.bornX, (int)this.bornY);
                    //if over desprant,remove from object
                    MainScene.findMainScence(this.surfaceView).getObjSenceList().remove(this);

                    return ;
                }else{
                    paint1.setAlpha(paint1.getAlpha() - 10);
                    //canvas.drawBitmap(bitmaps[animationIndex], x - Constant.MOVE_X_OFFSET, y, paint1);

                }
            }
        }

        this.x += speedX;
    }

    private long findLifeStamp(){
        createTimeStamp = System.currentTimeMillis();
        return createTimeStamp+(randomLife.nextInt(10000) % (4001) + 6000);
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.bornX = x;
        this.bornY = y;
        //set null for onDraw execute
        createTimeStamp = 0;
        paint1.setAlpha(30);
        MainScene.findMainScence(this.surfaceView).getObjSenceList().add(this);

    }

    @Override
    public WindCloud clone() throws CloneNotSupportedException {
        WindCloud iObjectScene = (WindCloud) super.clone();
        //iObjectScene.paint1 = (MyPaint)this.paint1.clone();
        iObjectScene.paint1 = new Paint();
        return iObjectScene;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
