package com.ck.ind.finddir.thread;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.MySurfaceView;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.play.ISurfaceView;
import com.ck.ind.finddir.scene.MainScene;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public class DrawThread extends Thread{
    private boolean isWorking = true;
    public static boolean isLogicWorking = true;
    private int sleepSpan  = 60;
    private ISurfaceView gameView = null;
    private SurfaceHolder surfaceHolder = null;
    private IMainScene mainScene = null;

    private int slint = 2;

    public  DrawThread(ISurfaceView gameView){
        this.gameView = gameView;
        this.surfaceHolder = gameView.getHolder();
        this.mainScene = gameView.getMainScene();

    }
    @Override
    public void run() {
        while (this.isWorking){
            if (DrawThread.isLogicWorking){//
                slint++;
                //=====================.logic
                gameView.doFastLogic();

                if (slint%2 == 0) {
                    gameView.doNormalLogic();
                }
                if (slint%50 == 0){
                    gameView.doSlowestLogic();
                }
                gameView.getMainScene().fixIsLaunchReady();
            }
            //===================part of drawing
            Canvas canvas = surfaceHolder.lockCanvas(
                    new Rect(Constant.MOVE_X_OFFSET_MAX_L, 0, Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));
            if (canvas != null){
                gameView.doBackgroundDraw(canvas);
                //2.draw
                gameView.doDraw(canvas);

                surfaceHolder.unlockCanvasAndPost(canvas);
            }else {
                Log.i("unknown error","canvas is null");
            }

            //canvas.drawBitmap(icon, rect.left,rect.top,null);

            // unlock painting ,submit image drawed

            try {
                Thread.sleep(sleepSpan);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (slint == 100000){
                slint = 2;
            }
        }
        //super.run();
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
}
