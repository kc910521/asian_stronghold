package com.ck.ind.finddir.bean.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.MySurfaceView;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.List;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public class Explosion implements IObjectScene,Cloneable{
    SurfaceView mySurfaceView;
    Bitmap[] bitmaps;
    int transprantRate = 255;
    float x;
    float y;
    Paint paint1 = new Paint();
    private int animateIndex = 0;

    public Explosion(SurfaceView mySurfaceView){
        this.mySurfaceView = mySurfaceView;
  //      if (bitmapTp == null){
        this.bitmaps =  new Bitmap[]{
                BitmapFactory.decodeResource(mySurfaceView.getResources(), R.drawable.ball_exp1)
        };
/*        }else{
            this.bitmaps = bitmapTp;
        }*/
        ImageTools.resizeBitMapBachBeRule(this.bitmaps,89,85);
    }

    public void onDraw(Canvas canvas,Paint paint){

/*        if (animateIndex > this.bitmaps.length-1){
            return;
        }*/
/*        if(paint1.getAlpha()>=0 && animateIndex>=0){
            Log.i("newmod","bitmaps:"+bitmaps);
            canvas.drawBitmap(bitmaps[animateIndex],x- Constant.MOVE_X_OFFSET,y,paint1);
        }*/
        if (this.transprantRate > 0 && animateIndex>=0){
            canvas.drawBitmap(bitmaps[animateIndex], x - Constant.MOVE_X_OFFSET, y, paint1);
        }
        //animateIndex ++;
        //drawSelf(canvas,paint);
    }

    @Override
    public void onLogic() {
        if ((this.transprantRate-10)<0){
            //bitmaps = null;//clear bitmap
            MainScene.findMainScence(this.mySurfaceView).getObjSenceList().remove(this);
            return ;
        }else{
            paint1.setAlpha(this.transprantRate-=10);
            this.x++;
        }


    }

    public void setBitmaps(Bitmap[] bitmaps){
        this.bitmaps = bitmaps;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.transprantRate = 255;
        paint1 = new Paint();
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getX() {
        return x;
    }


    @Override
    public IObjectScene clone() throws CloneNotSupportedException {
        return (IObjectScene) super.clone();
    }
}
