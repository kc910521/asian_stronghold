package com.ck.ind.finddir.bean.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public class Bloodstain implements IObjectScene,Cloneable{
    private SurfaceView mySurfaceView;
    private Bitmap[] bitmaps;
    private float x;
    private float y;
    private int animateIndex = 0;
    private Random random = null;

    public Bloodstain(SurfaceView mySurfaceView){
        this.mySurfaceView = mySurfaceView;
            bitmaps =  new Bitmap[]{
                    BitmapFactory.decodeResource(mySurfaceView.getResources(), R.drawable.blood_1),
                    BitmapFactory.decodeResource(mySurfaceView.getResources(), R.drawable.blood2)
            };
        ImageTools.resizeBitMapBachBeRule(this.bitmaps, 18, 14);
        this.random = new Random();

    }

    public void onDraw(Canvas canvas,Paint paint){

/*        if (animateIndex > this.bitmaps.length-1){
            return;
        }*/
        //if (animateIndex>=0){
        canvas.drawBitmap(bitmaps[animateIndex], x - Constant.MOVE_X_OFFSET, y, paint);
        //}

        //animateIndex ++;
        //drawSelf(canvas,paint);
    }

    @Override
    public void onLogic() {
/*        if ((transprantRate-10)<0){
            //bitmaps = null;//clear bitmap
            MainScene.findMainScence(this.mySurfaceView).getObjSenceList().remove(this);
            return ;
        }else{
            paint1.setAlpha(this.transprantRate-=10);
            this.x++;
        }*/


    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.animateIndex = this.random.nextInt(this.bitmaps.length);
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
