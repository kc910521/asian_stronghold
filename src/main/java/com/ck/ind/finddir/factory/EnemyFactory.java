package com.ck.ind.finddir.factory;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/10.
 *
 *
 */
public class EnemyFactory<T extends IEnemy> {
    private SurfaceView surfaceView = null;
    //Random random = new Random();
    IEnemy iEnemy = null;


    private static EnemyFactory enemyFactory = null;

    /**
     * list for flyweight
     */
    private Map<Class<T>,T> flyweightList = null;

    private EnemyFactory(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        this.troopInterval =  ImageTools.positionConvert(25);
        this.flyweightList = new LinkedHashMap<Class<T>,T>();
    }

    public static synchronized EnemyFactory findFactory(SurfaceView surfaceView){
        if (enemyFactory == null){
            enemyFactory = new EnemyFactory(surfaceView);
        }
        return enemyFactory;
    }

/*    public synchronized void restoreFactory(){
        //enemyFactory = null;
    }*/

    public synchronized T createObject(Class clazz ,int xPosition){
        T enemyClone = null;

        if (flyweightList.get(clazz) != null){//非第一次创造本类型部队
            try {
                enemyClone = (T) flyweightList.get(clazz).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }else{
            Constructor constructor = null;
            try {
                constructor = clazz.getDeclaredConstructor(new Class[]{SurfaceView.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            constructor.setAccessible(true);
            try {
                enemyClone = (T) constructor.newInstance(new Object[]{surfaceView});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            flyweightList.put(clazz,enemyClone);

        }
        //this.lastAllocatY = Constant.MIN_ACTIVE_UPON;
        enemyClone.fullHp();
        enemyClone.setCurPostion(this.randomXPosition(xPosition),this.collectAllYPosition());//this.collectAllYPosition()
        return enemyClone;


/*        if (iEnemy != null && iEnemy.getClass().equals(clazz)){//非第一次创造本类型部队
            Log.i("createObj","clone");
            IEnemy enemyClone = null;
            try {
                enemyClone = iEnemy.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            enemyClone.fullHp();
            enemyClone.setCurPostion(this.randomXPosition(xPosition),this.collectAllYPosition());
            return (T)enemyClone;
        }else{
            this.lastAllocatY = Constant.MIN_ACTIVE_UPON;
            Constructor constructor = null;
            try {
                constructor = clazz.getDeclaredConstructor(new Class[]{SurfaceView.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            constructor.setAccessible(true);
            try {
                iEnemy = (IEnemy) constructor.newInstance(new Object[]{surfaceView});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //iEnemy = new Boy(surfaceView);
            iEnemy.fullHp();
            iEnemy.setCurPostion(xPosition,this.collectAllYPosition());
            return  (T)iEnemy;

        }*/

    }

    /**
     * can be used to create aline of  arrayed enemy
     * @param clazz
     * @param xPosition
     * @param yPosition
     * @return
     */
    public synchronized T createPointObject(Class clazz ,int xPosition,int yPosition){

        if (iEnemy != null && iEnemy.getClass().equals(clazz)){//非第一次创造本类型部队
            Log.i("createObj","clone");
            IEnemy enemyClone = null;
            try {
                enemyClone = iEnemy.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            enemyClone.fullHp();
            enemyClone.setCurPostion(this.randomXPosition(xPosition),yPosition);
            return (T)enemyClone;
        }else{
            this.lastAllocatY = Constant.MIN_ACTIVE_UPON;
            Constructor constructor = null;
            try {
                constructor = clazz.getDeclaredConstructor(new Class[]{SurfaceView.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            constructor.setAccessible(true);
            try {
                iEnemy = (IEnemy) constructor.newInstance(new Object[]{surfaceView});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            iEnemy.fullHp();
            iEnemy.setCurPostion(this.randomXPosition(xPosition),yPosition);
            return  (T)iEnemy;

        }

    }

    /**
     * 得到从中向两边排列的队伍
     * @param clazz
     * @param xPosition
     * @return
     */
    public synchronized T createFormObject(Class clazz ,int xPosition){

        if (iEnemy != null && iEnemy.getClass().equals(clazz)){//非第一次创造本类型部队
            Log.i("createObj","clone");
            IEnemy enemyClone = null;
            try {
                enemyClone = iEnemy.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            enemyClone.fullHp();
            enemyClone.setCurPostion(this.randomXPosition(xPosition),this.collectAllYPosition());
            return (T)enemyClone;
        }else{
            this.lastAllocatY = Constant.MIN_ACTIVE_UPON;
            Constructor constructor = null;
            try {
                constructor = clazz.getDeclaredConstructor(new Class[]{SurfaceView.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            constructor.setAccessible(true);
            try {
                iEnemy = (IEnemy) constructor.newInstance(new Object[]{surfaceView});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //iEnemy = new Boy(surfaceView);
            iEnemy.fullHp();
            iEnemy.setCurPostion(xPosition,this.collectAllYPosition());
            return  (T)iEnemy;

        }

    }

    private Random random_xp_y = new Random();
    private int troopInterval = 0;

    //最后一次指派的y坐标，待合法查验
    private int lastAllocatY = 0;

    private int collectAllYPosition(){
        //Log.i("msg","lastAllocatY:"+lastAllocatY+",Constant.SCREEN_HEIGHT:"+Constant.SCREEN_HEIGHT+",troopInterval:"+troopInterval);
        if ((random_xp_y.nextInt(5))<=3){//可用
            if (lastAllocatY >= (Constant.SCREEN_HEIGHT - (Constant.SCREEN_HEIGHT/5))){//超出y范围
                lastAllocatY = Constant.MIN_ACTIVE_UPON+(troopInterval/2);

            }else {
                lastAllocatY +=  troopInterval;
            }
        }else{
            collectAllYPosition();
            lastAllocatY += troopInterval;
        }
        Log.i("lastAllocatY",lastAllocatY+",troopInterval:"+troopInterval+",Constant.MIN_ACTIVE_UPON:"+Constant.MIN_ACTIVE_UPON);
        return lastAllocatY;
    }

    /**
     * /2为数轴原点
     * @param posX
     * @return
     */
    private int randomXPosition(int posX){
        int rdy = random_xp_y.nextInt(36);
        return posX+ ImageTools.positionConvert(rdy - (40 / 2));
    }
    /**
     * get randon Y position
     * @return
     */
/*    private int randomPosition(){
        Random random1 = new Random();
        int posY = random1.nextInt(Constant.SCREEN_HEIGHT-(Constant.SCREEN_HEIGHT/5));
        Log.i("posi",posY+"");
        if (posY<Constant.MIN_ACTIVE_UPON || posY >(Constant.SCREEN_HEIGHT-120)){
            return this.randomPosition();
        }
        return posY;
    }*/
}
