package com.ck.ind.finddir.bean.scene;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.MainActivity;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.StoreActivity;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.object.Village1;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.spirt.Messenger;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.EnemyFactory;
import com.ck.ind.finddir.factory.SceneFactory;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.play.VictoryActivity;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.thread.AlertHandler;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by KCSTATION on 2015/9/6.
 */
public abstract class AbsSceneBean {
    //public static int SCENE_LEVEL = 1;


    //敌人生成批次依然存留
    protected int enemyWavesStill = 3;
    //敌人生成批次上限
    protected int enemyWavesThis = 3;

    EnemyFactory enemyFactory = null;

    protected SurfaceView surfaceView = null;
    protected IMainScene mainScene = null;

    //下一章
    //protected AbsSceneBean nextScene = null;
    //初始介绍
    protected String sceneDescription = "FORWARD";

    protected Message message = null;

    protected boolean isLastStage = false;

    /**
     * appear next wave of enemy after last men on the stage
     */
    protected int generateIndexNumber;
    //==================play trigger
    public boolean mg_isdead = false;


    public AbsSceneBean(IMainScene mainScene,SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        this.mainScene = mainScene;
        this.message = new Message();
        this.generateIndexNumber = 0;
        //init creator factory
        enemyFactory = EnemyFactory.findFactory(this.surfaceView);

    }

    /**
     * 村庄发展
     */
    protected void villageLiving(){
/*        if(Village1.WOMEN_QT<=0){
            Village1.WOMEN_QT = 2;
        }
        if ((Village1.MEN_QT+Village1.WOMEN_QT)>20 && (Village1.MEN_QT/2)>Village1.WOMEN_QT){
            Village1.MEN_QT-=7;
        }
        Village1.MEN_QT *= (Village1.WOMEN_QT/100);
        Village1.WOMEN_QT *= (1/5);*/

        this.mainScene.fixIsLaunchReady();
    }


    /**
     * 生成一列敌人
     * @param enemyClazz 敌人类
     * @param numberPerLine 每列数量
     * @param generPosition 屏幕外距离
     */
    protected void generateEnemyOnce(Class<? extends IEnemy> enemyClazz,int numberPerLine,int generPosition){
        IEnemy enemyObj = null;
        for(int i = 0;i<numberPerLine;i++){
            enemyObj = enemyFactory.createObject(enemyClazz, Constant.SCREEN_WIDTH + ImageTools.positionConvert(generPosition ));
            mainScene.getEnemyList().add(enemyObj);
        }
    }

    /**
     * 生成一列敌人，从中部向两边排列
     * @param enemyClazz 敌人类
     * @param numberPerLine 每列数量 max = 9
     * @param generPosition 屏幕外距离
     */
    protected void generateFormOnce(Class<? extends IEnemy> enemyClazz,int numberPerLine,int generPosition){
        IEnemy enemyObj = null;
        //Constant.EXPLOSITION_Y/numberPerLine
        if (numberPerLine > 9){
            numberPerLine = 9;
        }
        int interval = Constant.SCREEN_HEIGHT/(numberPerLine+1);
        int[] posYArrs = new int[numberPerLine];
        for (int a = 0;a < posYArrs.length;a++){
            posYArrs[a] = interval*(a+1);
        }
        int[] resPosY = this.formArraysToAssemble(posYArrs);
        for (int posY : resPosY){
            enemyObj = enemyFactory.createPointObject(enemyClazz, Constant.SCREEN_WIDTH +  ImageTools.positionConvert(generPosition),posY);
            mainScene.getEnemyList().add(enemyObj);
        }
    }

    /**
     *
     * @param enemyClazz
     * @param generPosX adjusted position
     * @param generPosY raw position without adjust for screen size
     */
    protected void generatePoint(Class<? extends IEnemy> enemyClazz,int generPosX,int generPosY){
        IEnemy enemyObj = null;
        enemyObj = enemyFactory.createPointObject(enemyClazz, Constant.SCREEN_WIDTH +  ImageTools.positionConvert(generPosX),generPosY);
        mainScene.getEnemyList().add(enemyObj);
    }

    private  int[] formArraysToAssemble(int[] posYArr){
        int midPos = posYArr.length >> 1;
        int lrFlag = 0;
        int []posRes = new int[posYArr.length];
        for(int a=0; a<posYArr.length; a += 2){
            if(midPos + lrFlag < posYArr.length){
                posRes[a] = posYArr[midPos + lrFlag];
            }
            if(lrFlag != 0){
                if(midPos - lrFlag >= 0 ){
                    posRes[a-1] = posYArr[midPos - lrFlag];
                }
            }
            lrFlag ++;
        }
        if(posYArr.length%2 == 0){
            posRes[posRes.length-1] = posYArr[0];
        }
        return posRes;
    }

    /**
     * 生成敌人混合编队
     */
    public void enemyApproach(){
        //调用 generateEnemyOnce
    }

    /**
     * show alert window with resource content id
     * @param RES_ID
     */
    protected void sendMsg(int RES_ID){
        message.obj = surfaceView.getContext().getString(RES_ID);
        AlertHandler.findAlert(null).sendMessage(message);
        message = AlertHandler.findAlert(null).obtainMessage();
    }
    /**
     * 生成新进的地图项目
     * @param objectScene
     * @param posX
     * @param posY
     */
    protected void generateSceneObject(IObjectScene objectScene,Integer posX,Integer posY){
        IObjectScene objectScene1 = objectScene;
        if (posX!=null && posY!=null){
            objectScene1.setPosition(posX, posY);
        }

        mainScene.getObjSenceList().add(objectScene1);

    }


    public Boolean stageOver(){
        Itower.stopRecovery();
        if (this.initNextStage() != null){
            Log.i("scene","enemyWavesStill:"+enemyWavesStill+",size:"+mainScene.getEnemyList().size());
            mainScene.getTower().initWeaponStatus();
            if(enemyWavesStill <= 0 && mainScene.getEnemyList().size() <= 0){//过关
                Context context = this.surfaceView.getContext();
                //ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                //ComponentName cn =  activityManager.getRunningTasks(1).get(0).topActivity;

                SceneFactory sceneFactory = SceneFactory.initSceneFactory(this.mainScene,this.surfaceView);
                //下一关卡
                MainScene.sceneBean = sceneFactory.getRightScene();
                this.clearSceneReadyStorePage();
                Intent intent = new Intent(context,StoreActivity.class);
                context.startActivity(intent);
                return true;
            }
        }else if (this.isLastStage){
            Context context = this.surfaceView.getContext();
            Intent intent = new Intent(context,VictoryActivity.class);
            context.startActivity(intent);
        }
        return false;
    }

    private void clearSceneReadyStorePage(){
        MainScene.findMainScence(null).getObjSenceList().clear();
        MainScene.findMainScence(null).setTowerNeedDraw(false);
        IRemains.remainList.clear();
    }


    /**
     *
     * @param merNeeded 需要的信使数
     */
    protected void messengerDispear(int merNeeded){
        this.generateIndexNumber = 0;
        Log.i("enemyApproach","Constant.SCREEN_WIDTH + Constant.MOVE_X_OFFSET_MAX_R:"+(Constant.SCREEN_WIDTH + Constant.MOVE_X_OFFSET_MAX_R)+"," +
                "mainScene.getTower().getX() + mainScene.getTower().getWidth():"+(mainScene.getTower().getX() + mainScene.getTower().getWidth()));

        IEnemy enemyObj = null;
        //Constant.EXPLOSITION_Y/numberPerLine
        if (merNeeded > 9){
            merNeeded = 9;
        }
        int interval = Constant.SCREEN_HEIGHT/(merNeeded+1);
        int[] posYArrs = new int[merNeeded];
        for (int a = 0;a < posYArrs.length;a++){
            posYArrs[a] = interval*(a+1);
        }
        int[] resPosY = this.formArraysToAssemble(posYArrs);
        for (int posY : resPosY){
            enemyObj = enemyFactory.createPointObject(Messenger.class,mainScene.getTower().getX() + mainScene.getTower().getWidth()  ,posY);
            mainScene.getEnemyList().add(enemyObj);
        }
    }

    public AbsSceneBean takeSceneBeanBy(int sceneTagNumber){
        Log.i("mtest"," get in takeSceneBeanBy,sceneTagNumber:"+sceneTagNumber);
        Class sbClazz = null;
        try {
            sbClazz = Class.forName(this.execGetSceneBeanClassName(sceneTagNumber));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        AbsSceneBean absSceneBean = null;
        try {
            absSceneBean = (AbsSceneBean) sbClazz.getConstructor(IMainScene.class,SurfaceView.class).newInstance(this.mainScene,this.surfaceView);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return absSceneBean;
    }

    /**
     * 得到目标scenebean的反射需要路径
     * @param sceneTagNumber
     * @return
     */
    private String execGetSceneBeanClassName(int sceneTagNumber){
        return SceneBean01.class.getName().replaceAll(SceneBean01.class.getSimpleName(),"SceneBean"+this.takeRuleNumber(sceneTagNumber));
    }
    /**
     * get nameTagNumber by Scenebean name rule
     * @param sceneTagNumber
     * @return
     */
    private String takeRuleNumber(int sceneTagNumber){
        return sceneTagNumber<10?"0"+sceneTagNumber:sceneTagNumber+"";
    }

    /**
     * init scene params
     */
    public abstract void initScene();

    /**
     * 初始化并返回下一关卡
     * @return
     */
    public abstract AbsSceneBean initNextStage();






    public int getEnemyWavesStill() {
        return enemyWavesStill;
    }


    public String getSceneDescription() {
        return sceneDescription;
    }

    public void setMg_isdead(boolean mg_isdead) {
        this.mg_isdead = mg_isdead;
    }

    public int getGenerateIndexNumber() {
        return generateIndexNumber;
    }
}
