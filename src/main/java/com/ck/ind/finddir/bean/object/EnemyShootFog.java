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

/**
 * Created by KCSTATION on 2015/8/3.
 */
public class EnemyShootFog implements IObjectScene,Cloneable{
    private SurfaceView mySurfaceView;
    private Bitmap[] bitmaps;
    private int transprantRate = 255;
    private float x;
    private float y;
    private Paint paint1 = null;
    private int animateIndex = 0;
    private float moveSpeedX= 0;

    public EnemyShootFog(SurfaceView mySurfaceView){
        this.mySurfaceView = mySurfaceView;
            bitmaps =  new Bitmap[]{
                    BitmapFactory.decodeResource(mySurfaceView.getResources(), R.drawable.ene_shootfog)
            };
        ImageTools.resizeBitMapBachBeRule(this.bitmaps, 58, 40);
        moveSpeedX = -ImageTools.positionConvert(2.0f);
    }

    public void onDraw(Canvas canvas,Paint paint){

/*        if (animateIndex > this.bitmaps.length-1){
            return;
        }*/
        if (transprantRate > 0 && animateIndex>=0){
            canvas.drawBitmap(bitmaps[animateIndex], x - Constant.MOVE_X_OFFSET, y, paint1);
        }

        //animateIndex ++;
        //drawSelf(canvas,paint);
    }

    @Override
    public void onLogic() {
        if ((transprantRate-9)<0){
            //bitmaps = null;//clear bitmap
            MainScene.findMainScence(this.mySurfaceView).getObjSenceList().remove(this);
            return ;
        }else{
            paint1.setAlpha(this.transprantRate-=9);
            this.x += moveSpeedX;
        }
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
