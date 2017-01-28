package com.ck.ind.finddir.bean.tower;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;
import com.ck.ind.finddir.thread.DrawThread;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KCSTATION on 2015/8/12.
 */
public class Itower {

    private SurfaceView surfaceView = null;
    private static Itower itower = null;
    private static int HP = 500;
    private int x;
    private int y;
    private int width;
    private int height;
    private Bitmap[] bitmapsWeapon;
    private Bitmap[] bitmapBases;
    private boolean shootAfter = false;
    //private Long firstWpAttackInterval;
    private int animateIndex = 0;
    private List<BulletBean> wpInOrder = null;

    public static int BASE_HP = 500;
    public static int ADDITION_HP = 0;
    //min:1;max:10;
    public static int I_POWER;

    private int recoverySkillLevel = 0;
    private int managerSkillLevel = 0;


    //auto recover
    private static Timer timer = null;
    private static boolean isInRecovery = false;
    //装备情况MAP
    //Key of map always be UPERCASE
    Map<String,String> wpMap = null;
/*    private Boolean arrowsEqiuped = false;
    private Boolean catEqiuped = false;
    private Boolean oilEqiuped = false;*/


    //weapon initial
/*    protected Boolean CAT001Equiped = false;
    protected Boolean ARR001Equiped = false;
    protected Boolean OIL001Equiped = false;
    protected Boolean FCROW001Equiped = false;*/

    private int cataWidth = 0;
    private int cataHeight = 0;
    private int cataX = 0;
    private int cataY = 0;

    private Itower(SurfaceView surfaceView){
        this.surfaceView = surfaceView;

        this.bitmapsWeapon = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(), R.drawable.weapon_cata_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.weapon_cata_att_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.weapon_cata_att_3),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.weapon_cata_att_4)
        };
        bitmapBases = ImageTools.resizeBitMapBachBeRule(new Bitmap[]{BitmapFactory.decodeResource(
                this.surfaceView.getResources(), R.drawable.itower_ec1)}, 350, 260);

        //initialize weapon :Catapult with real width
        this.cataWidth = ImageTools.positionConvert(90);
        this.cataHeight = ImageTools.positionConvert(190);
        ImageTools.resizeBitMapBach(this.bitmapsWeapon, cataWidth,cataHeight);

        this.width = bitmapBases[0].getWidth();
        this.height = bitmapBases[0].getHeight();
        //HP = Itower.BASE_HP;
        //firstWpAttackInterval = 3000l;

        //load sqlite get realweapon attribute
        wpMap = new HashMap<>();
        wpInOrder = new ArrayList<>();
    }

    public synchronized static Itower initUserTower(SurfaceView surfaceView){
        if (itower == null && surfaceView != null){
            itower = new Itower(surfaceView);
        }
        return itower;
    }

    public synchronized static void  restoreTower(){
        Itower.stopRecovery();
        itower = null;
        HP = 500;
        BASE_HP = 500;
        ADDITION_HP = 0;
    }

    public static void stopRecovery(){
        if (Itower.timer != null ){
            Itower.timer.cancel();
            Itower.isInRecovery = false;
        }
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        this.cataX = this.x+this.width-this.cataWidth-this.width/9;
        this.cataY = this.y+(this.height/5);
    }

    //draw catapult shot action
    public void onDraw(Canvas canvas,Paint paint){
        canvas.drawBitmap(bitmapBases[0], this.x - Constant.MOVE_X_OFFSET, this.y, paint);
        if (this.shootAfter){
            canvas.drawBitmap(bitmapsWeapon[animateIndex],
                    this.cataX -Constant.MOVE_X_OFFSET,
                    this.cataY,
                    paint);
            if (animateIndex>=bitmapsWeapon.length-1){
                animateIndex = 0;
                this.shootAfter = false;
            }else{
                animateIndex ++;
            }
        }else{
            canvas.drawBitmap(bitmapsWeapon[0],
                    this.cataX-Constant.MOVE_X_OFFSET,
                    this.cataY,
                    paint);
        }
        this.onHpLogic();
    }

    /**
     * hp recover logic
     */
    public void onHpLogic(){
        //装备技能繁荣或忠诚，且不在恢复则激活rocover
        if (!this.isInRecovery && (this.recoverySkillLevel != 0 || this.managerSkillLevel >=2)){
            //put condition get in for effect
            if (Itower.HP < Itower.BASE_HP + Itower.ADDITION_HP ){
                this.isInRecovery = true;
                this.beginRecover(this.recoverySkillLevel + (this.managerSkillLevel/2) ,Itower.BASE_HP + Itower.ADDITION_HP);
            }
        }
    }

    /**
     *
     * @param recoverPer recoverPoint per s
     * @param maxTowerHp
     */
    private void beginRecover(final int recoverPer,final int maxTowerHp){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Itower.getHP() >= maxTowerHp) {
                    isInRecovery = false;
                    timer.cancel();
                }else if (!DrawThread.isLogicWorking){

                }else
                {
                    Itower.HP += recoverPer;
                }
            }
        }, 1500, 1500);

    }



    /**
     * initialize weapon availabel list in sqlite
     *
     * this function must after scene;s init
     */
    public void initWeaponStatus(){
        GameStore gameStore = GameStore.findGameStore(this.surfaceView.getContext());
        List<SkillPojo> wpList = gameStore.findAvailableWeapon();
        Log.i("db", "ssss have wpList:" + wpList);
        this.wpMap.clear();
        this.wpInOrder.clear();
        //make a reflection to push val of true to weapon attributes
        //Field[] fields = this.getClass().getDeclaredFields();
        for (SkillPojo askill : wpList){

            this.wpMap.put(askill.getWid().toUpperCase(),askill.getWpLevel()+"");
            this.wpInOrder.add(new BulletBean((askill.getWid()).toUpperCase(),Long.parseLong(askill.getWpInterval()+"")));
        }
        Log.i("db","++wpMap have:"+wpMap.toString());
        Log.i("db", "++wpInOrder have:" + this.wpInOrder.toString());
        //hp recovery
        //this.onHpLogic();
        //have recovery skill
        if (this.getWpMap().containsKey(Constant.FLOURISH_ID)){
            this.recoverySkillLevel = Integer.valueOf(this.getWpMap().get(Constant.FLOURISH_ID)+"");
        }else{
            this.recoverySkillLevel = 0;
        }


        this.initTowerAdditionMap();
    }

    /**
     * fix addition hp
     */
    private void initTowerAdditionMap(){
        Itower.ADDITION_HP = 0;
        if (this.getWpMap().containsKey(Constant.MANAGER_ID)){
            this.managerSkillLevel = Integer.valueOf(this.getWpMap().get(Constant.MANAGER_ID)+"");
            Itower.ADDITION_HP = 50 * managerSkillLevel;
        }else{
            this.managerSkillLevel = 0;
        }
    }

    //muse run after initWeaponStatus had launched
    public void initHpBySkill(){

    }


    public synchronized static int effectHpValue(int changePoint){
        Itower.HP += changePoint;
        return Itower.HP;
    }


    /**
     *
     * @param wpId
     * @param needUpdateLastShootTS
     * if could be launch ,is need update shoot timestamp
     * @return
     */
    public boolean wpIsReady(String wpId,boolean needUpdateLastShootTS){
        Iterator<BulletBean> bbList = this.getWpInOrder().iterator();
        BulletBean bbTmp = null;

        while (bbList.hasNext()){
            bbTmp = bbList.next();
            if (bbTmp.getWpId().equals(wpId)){
                if ((bbTmp.getWpShootInterval()+bbTmp.getLastShootTS()) <= System.currentTimeMillis()){
                    if (needUpdateLastShootTS){
                        bbTmp.setLastShootTS(System.currentTimeMillis());
                    }
                    return true;
                }else{
                    break;
                }
            }
        }
        return false;
    }

    public static void restoreHP() {
        Itower.HP = Itower.BASE_HP;
    }

    //---------------------------gs method-------------------------------------------------
    public void setShootAfter(boolean shootAfter) {
        this.shootAfter = shootAfter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public static int getHP() {
        return HP;
    }

    public static void setHP(int HP) {
        Itower.HP = HP;
    }

    /*    public Long getFirstWpAttackInterval() {
        return firstWpAttackInterval;
    }*/

    public Map<String, String> getWpMap() {
        return wpMap;
    }

    public List<BulletBean> getWpInOrder() {
        return wpInOrder;
    }

    public void setWpInOrder(List<BulletBean> wpInOrder) {
        this.wpInOrder = wpInOrder;
    }

    public int getCataX() {
        return cataX;
    }

    public int getCataY() {
        return cataY;
    }
}
