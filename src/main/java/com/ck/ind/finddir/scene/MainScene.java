package com.ck.ind.finddir.scene;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.MainActivity;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.ai.AutoLaunch;
import com.ck.ind.finddir.bean.object.Countryside;
import com.ck.ind.finddir.bean.object.Forest;
import com.ck.ind.finddir.bean.object.Mountains;
import com.ck.ind.finddir.bean.object.WindCloud;
import com.ck.ind.finddir.bean.object.WindCloudFar;
import com.ck.ind.finddir.bean.scene.AbsSceneBean;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.object.Village1;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.BulletBean;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.BulletFactory;
import com.ck.ind.finddir.factory.EnemyFactory;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.factory.SceneFactory;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.thread.DrawThread;
import com.ck.ind.finddir.toolkits.ImageTools;
import com.ck.ind.finddir.toolkits.ImgResource;
import com.ck.ind.finddir.ui.LaunchButton;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by KCSTATION on 2015/8/6.
 *
 *
 * only one
 */
public class MainScene implements IMainScene {

    protected static SurfaceView surfaceView = null;

    private static MainScene mainScene = null;
    //public static int shootType = 1;//1,boomer;2:arrows

    EnemyFactory enemyFactory = null;
    Itower tower = null;

    Village1 village1 = null;
    Mountains backMountains = null;

    private BulletFactory bulletFactory =  null;

    public static AbsSceneBean sceneBean = null;

    //private Button[] btnSkills = null;
    private MainActivity gMainActivity = null;

    private ObjectFactory objectFactory = null;

    private boolean needActive = true;

    private boolean towerNeedDraw = false;

    private boolean needInitliaze = true;

    private MainScene(SurfaceView surfaceView){
        MainScene.surfaceView = surfaceView;

        SceneFactory sceneFactory = SceneFactory.initSceneFactory(this,MainScene.surfaceView);


        MainScene.sceneBean = sceneFactory.getRightScene();

        this.needInitliaze = true;
        DrawThread.isLogicWorking = true;

        tower = Itower.initUserTower(surfaceView);
        tower.initWeaponStatus();
        bulletFactory = BulletFactory.initFactory(MainScene.surfaceView);
        objectFactory = ObjectFactory.initObjectFactory(surfaceView);

        //sceneBean场景初始化
    }

    public static synchronized MainScene findMainScence(SurfaceView surfaceView){
        if (MainScene.mainScene == null){
            if(surfaceView != null){
                MainScene.mainScene = new MainScene(surfaceView);
            }else{
                return null;
            }
        }
        return MainScene.mainScene;

    }

    Bitmap bgPic = null;

    //IEnemy boy = null;

    private List<AbsBullet> bulletList = new CopyOnWriteArrayList<AbsBullet>();
    private List<IEnemy> enemyList = new CopyOnWriteArrayList<IEnemy>();
    private List<IObjectScene> objSenceList = new CopyOnWriteArrayList<IObjectScene>();



    public void intiImgResource(){
        //fit screen size
        if (ImgResource.BG == null){
            ImgResource.BG = BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.bg);
        }

        //Matrix matrix = new Matrix();
        //matrix.postScale(Constant.EXPLOSITION_X/bgx.getWidth(), Constant.EXPLOSITION_Y/bgx.getHeight());
        bgPic = Bitmap.createScaledBitmap(ImgResource.BG,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,true);


    }
    private float hp_font_size = 0f;
    /**
     * drawing thread loop
     * @param level
     * @param paint
     * @param canvas
     */
    public void initGameScene(int level,Paint paint,Canvas canvas){

        if (this.tower != null && this.towerNeedDraw){
            float xPos = (this.tower.getWidth() / 3) - Constant.MOVE_X_OFFSET;
            float yPos = this.tower.getY()+ImageTools.positionConvert(10);
            tower.onDraw(canvas, paint);
            paint.setTextSize(hp_font_size);
            paint.setColor(Color.argb(255, 150, 17, 17));
            Typeface font = Typeface.create("TrueType", Typeface.BOLD);
            paint.setTypeface(font);
            canvas.drawText("HP:" + Itower.getHP()+"/"+(Itower.BASE_HP+Itower.ADDITION_HP),
                    xPos,
                    yPos, paint);
        }
        //-------men and women qt
        paint.setColor(Color.BLACK);

/*        canvas.drawBitmap(BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.village_men), this.village1.getX() - 10 - Constant.MOVE_X_OFFSET,
                this.village1.getY()+5, paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.village_women) ,this.village1.getX() +70 - Constant.MOVE_X_OFFSET,
                this.village1.getY()+5,paint);*/
/*        canvas.drawText(Village1.MEN_QT + "", this.village1.getX() + 35 - Constant.MOVE_X_OFFSET,
                this.village1.getY() + 30, paint);
        canvas.drawText(Village1.WOMEN_QT + "", this.village1.getX() + 115 - Constant.MOVE_X_OFFSET,
                this.village1.getY() + 30, paint);*/
/*        canvas.drawText(surfaceView.getContext().getString(R.string.enemy_wainf)+sceneBean.getEnemyWavesStill() +
                        surfaceView.getContext().getString(R.string.enemy_waves),
                this.village1.getX() + (this.village1.getWidth()>>1) - Constant.MOVE_X_OFFSET,
                this.village1.getY() - ImageTools.positionConvert(20), paint);*/
        if (Constant.ENDLESS_COUNT != 0){
            paint.setTextSize(12);
            canvas.drawText(surfaceView.getContext().getString(R.string.u_max_endless_nums)+Constant.ENDLESS_COUNT +
                            surfaceView.getContext().getString(R.string.u_max_endless_nums_end),
                    this.village1.getX() + ImageTools.positionConvert(30) - Constant.MOVE_X_OFFSET,
                    this.village1.getY() + ImageTools.positionConvert(10), paint);
        }

    }

    /**
     * first init
     * into battle scene everytimes
     */
    public void sceneBegin(){

        this.sceneClean();
        Log.i("scene1", "sceneBegin()");
        if (tower == null){
            tower = Itower.initUserTower(surfaceView);
            //tower.initWeaponStatus();
        }

        //must before (tower.initWeaponStatus()),for initScene may add weapon to wpList in DB
        MainScene.sceneBean.initScene();
        tower.setPosition(0, ImageTools.positionConvert(100));

        tower.initWeaponStatus();
        //update ui weapon
        this.initSceneWpVisible();

        Mountains mountainBack = objectFactory.createMountain(Mountains.class,0,0);
        List<IObjectScene> mtList = new LinkedList<IObjectScene>();
        //景物放置于城市上方
        for (int headPos = Constant.MOVE_X_OFFSET_MAX_L; headPos < Constant.SCREEN_WIDTH + Math.abs(Constant.MOVE_X_OFFSET_MAX_L) ; headPos += mountainBack.getWidth() ){

            //用的生成血迹方法，可以暂时用一下
            mtList.add(objectFactory.createBloodstain(Forest.class, headPos, tower.getY() - mountainBack.getHeight()));
            //山和森林高度一样
            mtList.add(objectFactory.createMountain(Mountains.class,headPos,tower.getY()-mountainBack.getHeight()-mountainBack.getHeight()));

            objectFactory.createObject(WindCloudFar.class, headPos, tower.getY() - (mountainBack.getHeight() << 1));
        }
        this.objSenceList.addAll(mtList);
        mtList.clear();
        mtList = null;//go gc

        village1 = new Village1(surfaceView);
        village1.setPosition(-(village1.getWidth()) + ImageTools.positionConvert(15),
                ImageTools.positionConvert(130));

        objectFactory.createObject(WindCloud.class, tower.getX() + ImageTools.positionConvert(30), tower.getY());
        objectFactory.createObject(WindCloud.class, tower.getX() + (tower.getWidth() >> 2), tower.getY() + ImageTools.positionConvert(25));
        objectFactory.createObject(WindCloud.class, tower.getX() + (tower.getWidth() >> 1), tower.getY() + ImageTools.positionConvert(60));
        objectFactory.createObject(WindCloud.class, village1.getX() + (village1.getWidth() >> 1), village1.getY() + ImageTools.positionConvert(60));
        objectFactory.createObject(WindCloud.class, village1.getX() + ImageTools.positionConvert(30), village1.getY());
        objectFactory.createObject(WindCloud.class, village1.getX() + (village1.getWidth() >> 2), village1.getY() + ImageTools.positionConvert(25));
        objectFactory.createObject(WindCloud.class, village1.getX() + village1.getWidth() - (village1.getWidth() >> 2), village1.getY() - ImageTools.positionConvert(5));

        Countryside countryside = new Countryside(this.surfaceView);
        countryside.setPosition(tower.getX() - ImageTools.positionConvert(20) ,tower.getY()+tower.getHeight()-ImageTools.positionConvert(20));

        this.hp_font_size = ImageTools.positionConvert(23.0f);

        this.towerNeedDraw = true;
    }

    public synchronized void restoreScene(){
        this.needActive = false;
        bulletList.clear();
        enemyList.clear();
        objSenceList.clear();
        IRemains.remainList.clear();
        //waring
        SceneFactory.clearTemp();
/*        SceneFactory sceneFactory = SceneFactory.initSceneFactory(this,MainScene.surfaceView);
        Log.i("scene", "MainScene.sceneBean = sceneFactory.getRightScene();");
        MainScene.sceneBean = sceneFactory.getRightScene();*/
        bulletFactory.restoreFactory();
        //enemyFactory.restoreFactory();
        Itower.restoreTower();
        this.tower = null;
        MainScene.mainScene = null;
    }

    private void sceneClean(){
        village1 = null;
        objSenceList.clear();
        getEnemyList().clear();
        bulletList.clear();
        IRemains.remainList.clear();
    }
    //enemy generate function
    public void enemyApproach(){
        MainScene.sceneBean.enemyApproach();
    }
    //shoot boomer action
    public void addBullet(int powerKeyDown){
        AbsBullet absBullet = bulletFactory.createStoneBullet(powerKeyDown,this.tower);
        if (absBullet != null){
            this.bulletList.add(absBullet);
        }

    }
    //fire arrows action
    public void fireArrows(int powerKeyDown){
        List<AbsBullet> _listBullets = null;
        try {
            _listBullets  = bulletFactory.createArrows(powerKeyDown, 5, false, null, null);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (!_listBullets.isEmpty()){
            bulletList.addAll(_listBullets);
        }
    }
    //oil attack action
    public void addOilAtt(int powerKeyDown){
        List<AbsBullet> absBullets = null;
        try {
            absBullets = bulletFactory.createOilAttack(powerKeyDown, 7);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (!absBullets.isEmpty()){
            this.bulletList.addAll(absBullets);
        }
    }

    public void launchFireCrow(int powerKeyDown){
        List<AbsBullet> absBullets = null;
        try {
            absBullets = bulletFactory.createFireCrow(powerKeyDown, 1, false, 0, 0);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (!absBullets.isEmpty()){
            this.bulletList.addAll(absBullets);
        }
    }

    public void launchDragons(int powerKeyDown){
        List<AbsBullet> absBullets = null;
        try {
            absBullets = bulletFactory.createDragons(powerKeyDown, (tower.getX() + tower.getWidth()) >> 1, tower.getY(), 1, false);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (!absBullets.isEmpty()){
            this.bulletList.addAll(absBullets);
        }
    }

    /**
     * change skill launch button attributes
     * when could be launch
     */
    public void fixIsLaunchReady(){
        //&&  this.gMainActivity.getSkillBtn() != null
        if (this.gMainActivity != null  && this.getTower() != null){
            Iterator<BulletBean> wpInOrderIter = null;
            Long theTS = System.currentTimeMillis();
            BulletBean bbTmp = null;
            for (LaunchButton btn : this.gMainActivity.getSkillBtn()){
                if (!btn.isInReady()){
                    wpInOrderIter = getTower().getWpInOrder().iterator();
                    while (wpInOrderIter.hasNext()){
                        bbTmp = wpInOrderIter.next();
                        Log.i("handler","bbTmp.getWpId():"+bbTmp.getWpId()+",bbTmp.getLastShootTS():"+bbTmp.getLastShootTS()+",bbTmp.getWpShootInterval:"+bbTmp.getWpShootInterval());
                        if (bbTmp.getWpId().equals(btn.getTag()+"001") && (bbTmp.getLastShootTS()+bbTmp.getWpShootInterval()) <= theTS){
                            Message message = new Message();
                            message.obj = btn;
                            Log.i("sendMessage","sendMessage");
                            this.gMainActivity.getFixBtnHandler().sendMessage(message);
                            Log.i("handler","go send msg to Handler:"+btn.getTag());
                            break;
                        }
                    }
                }else{
                }
            }
            (new AutoLaunch(this.surfaceView)).checkAutoLaunchLoop(Constant.AUTO_DEF_ID);
            //auto launch

        }else{
            //Log.i("unknown error", "this.btnSkills is null");
        }
    }

    /**
     * 初始化武器按钮，装配的可见
     * dai youhuia
     * 之前必须接initWeaponStatus方法得到最新武器列表
     */
    public void initSceneWpVisible(){
        if (this.gMainActivity != null &&  this.gMainActivity.getSkillBtn() != null && this.getTower() != null){

            Iterator<BulletBean> wpInOrderIter = null;
            //Long theTS = System.currentTimeMillis();
            BulletBean bbTmp = null;
            Log.i("sendMessage","dd:"+getTower().getWpInOrder().size());
            //getTower().
            for (Button btn : this.gMainActivity.getSkillBtn()){
                //btn.setVisibility(View.INVISIBLE);
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
        }
    }

    public void sendLastLaunchTS(String wpid,Long lastLhTS){
        Iterator<BulletBean> wpInOrderIter = null;
        if (getTower() != null){
            wpInOrderIter = getTower().getWpInOrder().iterator();
            BulletBean bbTmp = null;
            while (wpInOrderIter.hasNext()){
                bbTmp = wpInOrderIter.next();
                if(bbTmp.getWpId().equals(wpid)){
                    bbTmp.setLastShootTS(lastLhTS);
                }
            }
        }

    }



    public void updateWavesInf(){
        Message message = new Message();
        message.arg1 = MainScene.sceneBean.getEnemyWavesStill();
        this.gMainActivity.getFixWavesInf().sendMessage(message);
    }

    /* getter setter*/



    public Itower getTower() {
        return tower;
    }
    public List<AbsBullet> getBulletList() {
        return bulletList;
    }

    public Bitmap getBgPic() {
        return bgPic;
    }


    public List<IEnemy> getEnemyList() {
        return enemyList;
    }


    public List<IObjectScene> getObjSenceList() {
        return objSenceList;
    }

    public BulletFactory getBulletFactory() {
        return bulletFactory;
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

    public boolean isTowerNeedDraw() {
        return towerNeedDraw;
    }

    public void setTowerNeedDraw(boolean towerNeedDraw) {
        this.towerNeedDraw = towerNeedDraw;
    }

    public boolean isNeedInitliaze() {
        return needInitliaze;
    }

    public void setNeedInitliaze(boolean needInitliaze) {
        this.needInitliaze = needInitliaze;
    }
}
