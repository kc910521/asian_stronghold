package com.ck.ind.finddir.bean.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/17.
 */
public class PalmTree implements IObjectScene,Cloneable {

    float x;
    float y;
    int animationIndex = -1;
    Bitmap[] bitmaps ;
    SurfaceView surfaceView;

    public PalmTree(SurfaceView gameView){
        this.surfaceView = gameView;
        this.bitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.tree1_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.tree1_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.tree1_3)
        };
        ImageTools.resizeBitMapBachBeRule(this.bitmaps, 100, 120);

    }


    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        if (animationIndex>=0){
            canvas.drawBitmap(bitmaps[animationIndex], this.x- Constant.MOVE_X_OFFSET, this.y, paint);
        }
    }

    @Override
    public void onLogic() {
        animationIndex ++;
        if (animationIndex >= bitmaps.length){
            animationIndex = 0;
        }
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public IObjectScene clone() throws CloneNotSupportedException {

        return (IObjectScene) super.clone();
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
