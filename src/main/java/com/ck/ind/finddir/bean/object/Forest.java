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
 * Created by KCSTATION on 2015/9/4.
 */
public class Forest implements IObjectScene {

    float x;
    float y;
    int height;
    int width;
    Bitmap bitmap ;
    SurfaceView surfaceView;

    public Forest(SurfaceView gameView){
        this.surfaceView = gameView;
        bitmap = BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.forest_back);
        ImageTools.resizeBitmapSingleBeRule(this.bitmap, 180, 35);
        height = bitmap.getHeight();
        width = bitmap.getWidth();
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap,this.x- Constant.MOVE_X_OFFSET,this.y,paint);
    }

    @Override
    public void onLogic() {

    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        //MainScene.findMainScence(this.surfaceView).getObjSenceList().add(this);
    }


    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public IObjectScene clone() throws CloneNotSupportedException {
        return (IObjectScene) super.clone();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
