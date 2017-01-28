package com.ck.ind.finddir.toolkits;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

import com.ck.ind.finddir.Constant;

/**
 * 基础工具集
 * Created by KCSTATION on 2015/10/29.
 */
public final class ImageTools {
    private ImageTools(){
    }
    /**
     * rotate bitmap
     * from
     * http://blog.csdn.net/wang_shaner/article/details/7859637
     * @param bm
     * @param orientationDegree
     * @return
     */
    public static Bitmap adjustPhotoRotation(Bitmap bm, int orientationDegree)
    {
        //for efficient  to return
        if (orientationDegree == 0){
            return bm;
        }

        Matrix m = new Matrix();

        if(orientationDegree<0 || orientationDegree>360){
            orientationDegree = Math.abs(orientationDegree)%360;
        }
        m.setRotate(orientationDegree, (float) (bm.getWidth()>> 1), (float) (bm.getHeight() >> 1));

        try {
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        } catch (OutOfMemoryError ex) {
            Log.i("bitmap rotate crash:", ex.toString());
        }
        return null;
    }

    /**
     * resize bitmap size
     * @param width
     * @param height
     * @return
     */
    public static Bitmap[] resizeBitMapBach(Bitmap[] bitmaps,int width,int height){
        // Bitmap[] bitmap1 = new Bitmap[bitmap.length];
        for (int i=0;i<bitmaps.length;i++) {
            if (bitmaps[i]!=null) {
                bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i], width, height, true);
            }
        }
        return  bitmaps;
    }

    public static Bitmap resizeBitMapSingle(Bitmap bitmap,int width,int height){
        // Bitmap[] bitmap1 = new Bitmap[bitmap.length];
            if (bitmap != null) {
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            }
        return  bitmap;
    }

    /**
     * 以调试机htc528之标准缩放图片
     * 批量
     * @param bitmaps
     * @param width
     * @param height
     * @return
     */
    public static Bitmap[] resizeBitMapBachBeRule(Bitmap[] bitmaps,int width,int height){
        // Bitmap[] bitmap1 = new Bitmap[bitmap.length];
        for (int i=0;i<bitmaps.length;i++) {
            if (bitmaps[i]!=null) {
                bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i],
                        positionConvert(width),
                        positionConvert(height),
                        true);
            }
        }
        return  bitmaps;
    }

    //单体
    public static Bitmap resizeBitmapSingleBeRule(Bitmap bitmap,int width,int height){
        // Bitmap[] bitmap1 = new Bitmap[bitmap.length];
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    positionConvert(width),
                    positionConvert(height),
                    true);
        }
        return  bitmap;
    }

    /**
     * convert number to adapt to this screen
     * @param aPos
     * @return
     */
    public static int positionConvert(int aPos){
        if (Constant.SCREEN_HEIGHT == 0){
            Log.i("msg","==============,又是0了！aPos："+aPos);
        }

        return (int)(aPos/ Constant.SCREEN_HEIGHT_SP * Constant.SCREEN_HEIGHT);
    }
    public static double positionConvert(double aPos){
        if (Constant.SCREEN_HEIGHT == 0){
            Log.i("msg","==============,又是0了！aPos："+aPos);
        }

        return aPos/ Constant.SCREEN_HEIGHT_SP * Constant.SCREEN_HEIGHT;
    }

    public static float positionConvert(float aPos){
        if (Constant.SCREEN_HEIGHT == 0){
            Log.i("msg","==============,又是0了！aPos："+aPos);
        }

        return aPos/ Constant.SCREEN_HEIGHT_SP * Constant.SCREEN_HEIGHT;
    }

    /**
     * 重力方程参数
     * the more screen height,the less result
     * and get High limit
     * @param g
     * @return
     */
    public static float formulateThrowLineG(float g){
        float fTmp = (float)Math.ceil(g*10 * Constant.SCREEN_HEIGHT_SP /( Constant.SCREEN_HEIGHT - ((Constant.SCREEN_HEIGHT - Constant.SCREEN_HEIGHT_SP)/2)));
        return  fTmp/10;
    }

    /**
     * rotate bitmap
     * from
     * http://blog.csdn.net/wang_shaner/article/details/7859637
     * @param bitmap
     * @param orientationDegree
     * @return
     */
    public static Bitmap[] rotateBitMapBach(Bitmap[] bitmap,int orientationDegree){
        Matrix m = new Matrix();
        if(orientationDegree<0){
            orientationDegree = 360+orientationDegree;
        }
        for (int i = 0; i<bitmap.length; i++){
            m.setRotate(orientationDegree, bitmap[i].getWidth() / 2.0f, bitmap[i].getHeight() / 2.0f);//ck 我改了这

            try {
                Bitmap bm1 = Bitmap.createBitmap(bitmap[i], 0, 0, bitmap[i].getWidth(), bitmap[i].getHeight(), m, true);
                bitmap[i] = bm1;
            } catch (OutOfMemoryError ex) {
                Log.i("bitmapr", ex.toString());
            }
        }
        return bitmap;
    }

    /**
     * 得到导航栏高度
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    //invoke c/c++
    static {
//        System.loadLibrary("hello-android-jni");
    }
    public native String getMsgFromJni2();

    public native void rotateBitmap(Object zBitmap);
}
