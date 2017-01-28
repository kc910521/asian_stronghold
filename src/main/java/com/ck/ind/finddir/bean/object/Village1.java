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
 * Created by KCSTATION on 2015/8/17.
 */
public class Village1 implements IObjectScene {
    private float x;
    private float y;
    private int height;
    private int width;

    private int flourishPoint = 10;



    /**
     * quantity of men
     */
    public static int MEN_QT = 3;
    public static int WOMEN_QT = 2;

    Bitmap bitmap ;
    SurfaceView surfaceView;

    public Village1(SurfaceView gameView){
        this.surfaceView = gameView;
        bitmap = ImageTools.resizeBitmapSingleBeRule(BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.village_e1),330,251) ;
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
        MainScene.findMainScence(this.surfaceView).getObjSenceList().add(this);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
}
