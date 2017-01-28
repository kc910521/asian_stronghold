package com.ck.ind.finddir.bean.wreck;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.toolkits.ImageTools;

/**
 * Created by KCSTATION on 2015/8/12.
 */
public class Pikemen_remains implements IRemains,Cloneable{
    private Canvas canvas = null;
    private Paint paint = null;
    private Bitmap[] bitmaps = null;
    private int x;
    private int y;
    private int rekType = 0;
    private Bitmap showBitmap = null;

    public Pikemen_remains(SurfaceView surfaceView){
        this.bitmaps = new Bitmap[]{
                BitmapFactory.decodeResource(surfaceView.getResources(), R.drawable.wreck_arch),
                BitmapFactory.decodeResource(surfaceView.getResources(), R.drawable.wreck_blk),
                BitmapFactory.decodeResource(surfaceView.getResources(), R.drawable.wreck_pik)
        };
        ImageTools.resizeBitMapBachBeRule(this.bitmaps,35,20);
    }


    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmaps[rekType], this.x- Constant.MOVE_X_OFFSET, this.y, paint);
    }

    @Override
    public void setPosition(int x, int y, int width, int height, int reType) {
        this.x = x;
        this.y = y;
        this.rekType = reType;
        if (width !=0 && height != 0){
            this.showBitmap = ImageTools.resizeBitMapSingle(this.bitmaps[reType], width, height);
        }else{
            this.showBitmap = this.bitmaps[reType];
        }
    }

    public Pikemen_remains clone() throws CloneNotSupportedException {
        return (Pikemen_remains) super.clone();
    }

    @Override
    public int getBitmapsLength() {
        return bitmaps.length;
    }
}
