package com.ck.ind.finddir;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.play.FailActivity;
import com.ck.ind.finddir.play.ISurfaceView;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.thread.DrawThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public final class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,ISurfaceView {


    private Paint paint = null;
    //Timer timer = null;
    //TimerTask timerTask = null;

    MainScene mainScene = null;
    private DrawThread drawThread = null;
    private Iterator<IEnemy> enemyIterator = null;
    private Iterator<IObjectScene> objectSceneIterator = null;
    private Iterator<IRemains> remainsIterator = null;
    private Iterator<AbsBullet> bulletIterator = null;
    //Context context = null;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //this.context = context;
        this.requestFocus();
        this.setFocusableInTouchMode(true);
        getHolder().addCallback(this);
        mainScene = MainScene.findMainScence(this);
        Log.i("life", "------------surface construct,mainscene dizhi:"+mainScene);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.doDraw(canvas);
    }

    /**
     * loop on thread
     * %2 speed logic
     */
    public void doNormalLogic(){
        //go login
        objectSceneIterator = mainScene.getObjSenceList().iterator();
        while (objectSceneIterator.hasNext()){
            objectSceneIterator.next().onLogic();
        }
        enemyIterator = mainScene.getEnemyList().iterator();
        while (enemyIterator.hasNext()){
            enemyIterator.next().onLogic();
        }

        //更新按键是否可用

    }
    /**
     * loop on thread
     * %1 speed logic
     */
    public void doFastLogic(){
        //go login
        bulletIterator = mainScene.getBulletList().iterator();
        while (bulletIterator.hasNext()){
            bulletIterator.next().go();
        }
        //bullet.drawSelf(canvas, paint);
    }

    /**
     * %50 speed
     */
    public void doSlowestLogic(){
        if (mainScene.isNeedActive()){
            if (mainScene.getEnemyList().size() <= MainScene.sceneBean.getGenerateIndexNumber()){
                mainScene.enemyApproach();
                mainScene.updateWavesInf();
            }
            if (mainScene.getTower() != null && mainScene.getTower().getHP()<0){
                this.threadDestory();

                Intent intent = new Intent(mainScene.getgMainActivity(), FailActivity.class);
                mainScene.getgMainActivity().startActivity(intent);
                mainScene.getgMainActivity().finish();

            }
        }

    }

    /**
     * thread loop
     * @param canvas
     */
    public void doDraw(Canvas canvas){

        mainScene.initGameScene(0, paint, canvas);
        enemyIterator = mainScene.getEnemyList().iterator();
        objectSceneIterator = mainScene.getObjSenceList().iterator();
        remainsIterator = IRemains.remainList.iterator();

        while (remainsIterator.hasNext()){
            remainsIterator.next().onDraw(canvas,paint);
        }
        //godraw
        while (enemyIterator.hasNext()){
            enemyIterator.next().onDraw(canvas, paint);
        }

        while (objectSceneIterator.hasNext()){
            objectSceneIterator.next().onDraw(canvas, paint);
        }

        bulletIterator = mainScene.getBulletList().iterator();
        while (bulletIterator.hasNext()){
            bulletIterator.next().drawSelf(canvas, paint);
        }
    }

    public void doBackgroundDraw(Canvas canvas){
        if(paint != null && canvas!=null){
            canvas.drawBitmap(mainScene.getBgPic(), 0, 0, paint);//? error null pointer
        }else{
            Log.i("unknown error","paint canvas  is null");
        }

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("life", "surfaceCreated ,drawThread:" + drawThread+",mainScene:"+mainScene+",isNeedInitliaze"+mainScene.isNeedInitliaze());

        if (mainScene != null && mainScene.isNeedInitliaze()){
            mainScene.setNeedInitliaze(true);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setTextSize(25);
            //
            //this.setBackgroundResource(R.drawable.bg);
            mainScene.intiImgResource();
            mainScene.sceneBegin();//intiImgResource

            mainScene.setNeedActive(true);
        }



        drawThread = null;
        drawThread = new DrawThread(this);
        drawThread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("life","surface destory");
        this.threadDestory();
        //mainScene.restoreScene();
    }

    public void threadDestory(){
        drawThread.setIsWorking(false);
    }


    private float nowX = 0;
    private float nowY = 0;

    private int xOffsetBefore = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (Itower.initUserTower(null) != null){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    nowX = event.getX();
                    nowY = event.getY();
                    xOffsetBefore = Constant.MOVE_X_OFFSET;
                    break;
                case MotionEvent.ACTION_UP:
                    if (nowX == event.getX()) {
                        mainScene.addBullet(Itower.I_POWER);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (nowX == event.getX() || nowY > (Constant.SCREEN_HEIGHT * 6/7) || nowY < (Constant.SCREEN_HEIGHT/7)){
                        //
                    }else{
                        //移动差值（偏移量）
                        Float tmpf = nowX - event.getX();

                        //向左||向右
                        if((tmpf<0 && (xOffsetBefore+tmpf.intValue()) <= Constant.MOVE_X_OFFSET_MAX_L) ||
                                (tmpf >= 0 && (xOffsetBefore+tmpf.intValue()) >= Constant.MOVE_X_OFFSET_MAX_R)){
                            //什么都不做
                        }else{
                            Constant.MOVE_X_OFFSET = xOffsetBefore+tmpf.intValue();
                        }
                    }
                    break;
                default:break;
            }
        }

        return true;
    }

    public MainScene getMainScene() {
        return mainScene;
    }


    /**
     *
     * @return img full url,if '',mean failed
     * @throws IOException
     */
    public String mkScreenshot() throws IOException {
        String mDir = Environment.getExternalStorageDirectory()
                + "/"+Constant.SCREENSHOT_DIR;
        Log.i("sc2","dir :"+mDir);
        if (this.fileExist(mDir)){
            Log.i("sc2","dir ok");
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bitCanvas = new Canvas(bitmap);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.doBackgroundDraw(bitCanvas);
                this.doDraw(bitCanvas);//在自己创建的canvas上画
                this.doDraw(bitCanvas);//在自己创建的canvas上画
            }catch (Exception e){

            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mDir = mDir+"/" + System.currentTimeMillis() + ".jpg";
            File file = new File(mDir);
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return mDir;
        }else{
            Log.i("sc2","no dir");
        }

        return "";
    }

    private boolean fileExist(String filesPath){
        File file = new File(filesPath);
        if (file.exists()){
            return true;
        }else{
            if (file.mkdirs()){
                return true;
            }else{
                Log.i("sc2","no dir");
            }
        }
        return false;
    }

}
