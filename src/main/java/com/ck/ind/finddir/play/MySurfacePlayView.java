package com.ck.ind.finddir.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.object.WindCloud;
import com.ck.ind.finddir.bean.object.WindCloudP;
import com.ck.ind.finddir.bean.scene.SceneBeanMenu;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.thread.DrawThread;
import com.ck.ind.finddir.toolkits.ImgResource;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public final class MySurfacePlayView extends SurfaceView implements SurfaceHolder.Callback,ISurfaceView {


    private Paint paint = null;
    //Timer timer = null;
    //TimerTask timerTask = null;

    private PlayScene mainSceneMenu = null;
    private DrawThread drawThread = null;
    private Iterator<IEnemy> enemyIterator = null;
    private Iterator<IObjectScene> objectSceneIterator = null;
    //private Iterator<IRemains> remainsIterator = null;
    private ObjectFactory objectFactory = null;
    //Context context = null;
    private SceneBeanMenu sceneBeanMenu = null;

    public MySurfacePlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this.context = context;
        this.requestFocus();
        this.setFocusableInTouchMode(true);
        getHolder().addCallback(this);

        WindowManager wm = (WindowManager) this.getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //Constant.SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
        Constant.SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight();
        Constant.MIN_ACTIVE_UPON = Constant.SCREEN_HEIGHT/5;
        mainSceneMenu = PlayScene.findMainScence(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("log", "onDraw");
        super.onDraw(canvas);
        this.doDraw(canvas);
    }

    /**
     * loop on thread
     * %2 speed logic
     */
    public void doNormalLogic(){
        //go login
        objectSceneIterator = mainSceneMenu.getObjSenceList().iterator();
        while (objectSceneIterator.hasNext()){
            objectSceneIterator.next().onLogic();
        }
        enemyIterator = mainSceneMenu.getEnemyList().iterator();
        while (enemyIterator.hasNext()){
            enemyIterator.next().onLogic();
        }
        if(mainSceneMenu.isNeedActive()){
            if (mainSceneMenu.getEnemyList().size()<=15){
                sceneBeanMenu.enemyApproach();
            }
        }

    }
    /**
     * loop on thread
     * %1 speed logic
     */
    public void doFastLogic(){
        //go login
        //bullet.drawSelf(canvas, paint);
    }

    /**
     * %50 speed
     */
    public void doSlowestLogic(){

    }

    /**
     * thread loop
     * @param canvas
     */
    public void doDraw(Canvas canvas){


        enemyIterator = mainSceneMenu.getEnemyList().iterator();
        objectSceneIterator = mainSceneMenu.getObjSenceList().iterator();
/*        remainsIterator = IRemains.remainList.iterator();
        while (remainsIterator.hasNext()){
            remainsIterator.next().onDraw(canvas,paint);
        }*/
        //godraw
        while (enemyIterator.hasNext()){
            enemyIterator.next().onDraw(canvas, paint);
        }

        while (objectSceneIterator.hasNext()){
            objectSceneIterator.next().onDraw(canvas, paint);
        }
    }



    public void doBackgroundDraw(Canvas canvas){
        if(paint != null && canvas!=null){
            canvas.drawBitmap(mainSceneMenu.getBitmapBg(), 0, 0, paint);//? error null pointer
        }else{
            Log.i("unknown error","paint canvas  is null");
        }

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        //


        //this.setBackgroundResource(R.drawable.bg);
        mainSceneMenu.intiImgResource();

        sceneBeanMenu = new SceneBeanMenu(mainSceneMenu,this);
        //mainSceneMenu.sceneBegin();//intiImgResource
        //generate cloud
        objectFactory = ObjectFactory.initObjectFactory(this);
        Random random = new Random();

        for(int a =0; a < 11; a++){
            int xpos = random.nextInt(Constant.SCREEN_WIDTH)-50;
            int ypos = random.nextInt(Constant.SCREEN_HEIGHT)-20;
            objectFactory.createObject(WindCloudP.class, xpos, ypos);
        }

        mainSceneMenu.setNeedActive(true);

        drawThread = new DrawThread(this);
        drawThread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        //mainSceneMenu.getEnemyList().clear();
        mainSceneMenu.restoreScene();
        this.threadDestory();
    }

    public void threadDestory() {
        drawThread.setIsWorking(false);
    }


    @Override
    public IMainScene getMainScene() {
        return this.mainSceneMenu;
    }
}
