package com.ck.ind.finddir.play;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.MainActivity;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.object.Mountains;
import com.ck.ind.finddir.bean.object.Village1;
import com.ck.ind.finddir.bean.object.WindCloud;
import com.ck.ind.finddir.bean.scene.AbsSceneBean;
import com.ck.ind.finddir.bean.scene.SceneBeanMenu;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.tower.BulletBean;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.factory.BulletFactory;
import com.ck.ind.finddir.factory.EnemyFactory;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.thread.DrawThread;
import com.ck.ind.finddir.toolkits.ImgResource;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by KCSTATION on 2015/8/6.
 *
 *
 * only one
 */
public class PlayScene implements IMainScene{

    protected static SurfaceView surfaceView = null;
    private static PlayScene mainScene = null;
    //public static int shootType = 1;//1,boomer;2:arrows

    EnemyFactory enemyFactory = null;
    //Itower tower = null;

    Village1 village1 = null;
    Mountains backMountains = null;

    //private BulletFactory bulletFactory =  null;
    public static AbsSceneBean sceneBean = null;

    //private Button[] btnSkills = null;
    private MainActivity gMainActivity = null;

    private ObjectFactory objectFactory = null;

    private boolean needActive = true;

    //private Bitmap bgx = null;

    private PlayScene(SurfaceView surfaceView){
        PlayScene.surfaceView = surfaceView;
        //bulletFactory = BulletFactory.initFactory(PlayScene.surfaceView,this);
        //SceneFactory sceneFactory = SceneFactory.initSceneFactory(this, PlayScene.surfaceView);
        PlayScene.sceneBean = new SceneBeanMenu(this,this.surfaceView);

        this.objectFactory = ObjectFactory.initObjectFactory(surfaceView);
        DrawThread.isLogicWorking = true;

        //sceneBean场景初始化
    }
    public static synchronized PlayScene findMainScence(SurfaceView surfaceView){
        if (PlayScene.mainScene == null){
            PlayScene.mainScene = new PlayScene(surfaceView);
        }else{
        }
        return PlayScene.mainScene;

    }

    //Bitmap bgPic = null;

    //IEnemy boy = null;

    private List<IEnemy> enemyList = new CopyOnWriteArrayList<IEnemy>();
    private List<IObjectScene> objSenceList = new CopyOnWriteArrayList<IObjectScene>();

    private Bitmap bitmapBg = null;

    public void intiImgResource(){
        //fit screen size
        if (ImgResource.BG == null){

            if (System.currentTimeMillis()%3 == 0){
                ImgResource.BG = BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.bg_stmenu);
            }else{
                ImgResource.BG = BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.bg);
            }


        }
        bitmapBg = Bitmap.createScaledBitmap(ImgResource.BG, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, true);
        //bgx =
        //Matrix matrix = new Matrix();
        //matrix.postScale(Constant.EXPLOSITION_X/bgx.getWidth(), Constant.EXPLOSITION_Y/bgx.getHeight());

        //bgPic = Bitmap.createScaledBitmap(bgx,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,true);


    }

    /**
     * drawing thread loop
     * @param level
     * @param paint
     * @param canvas
     */
    public void initGameScene(int level,Paint paint,Canvas canvas){

/*        tower.onDraw(canvas, paint);

        canvas.drawText("HP:" + Itower.getHP(), (this.tower.getWidth() / 3) - Constant.MOVE_X_OFFSET,
                Constant.EXPLOSITION_Y - this.tower.getHeight() - 10, paint);*/
        //-------men and women qt
        paint.setColor(Color.BLACK);


/*        canvas.drawText(Village1.MEN_QT + "", this.village1.getX() + 35 - Constant.MOVE_X_OFFSET,
                this.village1.getY() + 30, paint);
        canvas.drawText(Village1.WOMEN_QT + "", this.village1.getX() + 115 - Constant.MOVE_X_OFFSET,
                this.village1.getY() + 30, paint);*/

    }

    /**
     * first init
     */
    public void sceneBegin(){

        this.sceneClean();
/*        //enemyFactory = EnemyFactory.findFactory(surfaceView);

//        palmTree = new PalmTree(surfaceView);
//        palmTree.setPosition(-80, 150);

        village1 = new Village1(surfaceView);
        village1.setPosition(-(village1.getWidth()+20),Constant.EXPLOSITION_Y-village1.getHeight()-20);

        backMountains = new Mountains(surfaceView);
        backMountains.setPosition(-10, 0);




        tower = Itower.initUserTower(surfaceView);
        tower.setPosition(0, 100);
        tower.initWeaponStatus();

        objectFactory.createObject(WindCloud.class, tower.getX() + 30, tower.getY());
        objectFactory.createObject(WindCloud.class, tower.getX() + (tower.getWidth() >> 2), tower.getY() + 25);
        objectFactory.createObject(WindCloud.class, tower.getX() + (tower.getWidth() >> 1), tower.getY() + 60);*/
    }

    private boolean needMoreObject = true;

    public void restoreScene(){
        //bulletList.clear();
        needMoreObject = false;
        this.needActive = false;
        enemyList.clear();
        objSenceList.clear();

        //PlayScene.mainScene = null;

    }
    private void sceneClean(){
        village1 = null;
        objSenceList.clear();
        getEnemyList().clear();
    }
    //enemy generate function
    public void enemyApproach(){
        if (this.needMoreObject){
            PlayScene.sceneBean.enemyApproach();
        }
    }
    //shoot boomer action

    /**
     * change skill launch button attributes
     * when could be launch
     */
    public void fixIsLaunchReady(){
    }

    /**
     * 初始化武器按钮，装配的可见
     */
    public void initSceneWpVisible(){
/*        if (this.gMainActivity != null &&  this.gMainActivity.getSkillBtn() != null && this.getTower() != null){
            Log.i("","initSceneWpVisible");
            Iterator<BulletBean> wpInOrderIter = null;
            Long theTS = System.currentTimeMillis();
            BulletBean bbTmp = null;
            for (Button btn : this.gMainActivity.getSkillBtn()){
                btn.setVisibility(View.INVISIBLE);
                wpInOrderIter = getTower().getWpInOrder().iterator();
                while (wpInOrderIter.hasNext()){
                    bbTmp = wpInOrderIter.next();
                    if (bbTmp.getWpId().equals(btn.getTag()+"001")){
                        Message message = new Message();
                        message.obj = btn;
                        this.gMainActivity.getFixBtnHandler().sendMessage(message);
                        break;
                    }
                }
            }
        }*/
    }

    /* getter setter*/
    public Itower getTower() {
        return null;
    }

    //public Bitmap getBgPic() {
      //  return bgPic;
   // }


    public List<IEnemy> getEnemyList() {
        return enemyList;
    }


    public List<IObjectScene> getObjSenceList() {
        return objSenceList;
    }

    public Bitmap getBitmapBg() {
        return bitmapBg;
    }

    public void setgMainActivity(MainActivity gMainActivity) {
        this.gMainActivity = gMainActivity;
    }

    public MainActivity getgMainActivity() {
        return gMainActivity;
    }

    public boolean isNeedActive() {
        return needActive;
    }

    public void setNeedActive(boolean needActive) {
        this.needActive = needActive;
    }
}
