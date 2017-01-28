package com.ck.ind.finddir.factory;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.bean.object.Explosion;
import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.object.Mountains;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * Created by KCSTATION on 2015/9/23.
 */
public class ObjectFactory <OB extends IObjectScene> {

    private static ObjectFactory objectFactory = null;
    private SurfaceView surfaceView = null;
    private OB cloudyObj = null;
    private Explosion boomFog = null;
    private OB littleFog = null;
    private OB shootFog = null;
    private OB bloodstain = null;
    private OB enemyShootFog = null;
    private Mountains backMountain = null;

    private Random randomPos = null;

    private ObjectFactory(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        randomPos = new Random();
    }
    public static synchronized ObjectFactory initObjectFactory(SurfaceView surfaceView){
        if (ObjectFactory.objectFactory == null){
            ObjectFactory.objectFactory = new ObjectFactory(surfaceView);
        }
        return ObjectFactory.objectFactory;
    }


    /**
     * windcloud only now
     *
     * object 可以在setPosition 时进行插入ObjectLisSt
     * then return object is useless further more never insert returnvalue to list again
     *
     *
     * @param objClass
     * @param posX
     * @param posY
     * @return
     */
    public OB createObjectFog(Class<OB> objClass,float posX,float posY){
        //temp object is null or conflict in type
        Log.i("cloud",objClass+"will be created.");
        if (this.littleFog == null || !littleFog.getClass().equals(objClass)){
            Log.i("cloud",objClass+" new");
            Constructor<OB> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                littleFog = constructor.newInstance(this.surfaceView);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            littleFog.setPosition(this.randomPoint(posX,20),this.randomPoint(posY,20));
            return littleFog;

        }else{
            IObjectScene objt = null;
            try {
                objt  = this.littleFog.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(this.randomPoint(posX,50),this.randomPoint(posY,50));
            return (OB) objt;
        }
    }


    /**
     *shoot fog create
     *
     * @param objClass
     * @param posX
     * @param posY
     * @return
     */
    public OB createShootFog(Class<OB> objClass,float posX,float posY){
        //temp object is null or conflict in type
        Log.i("cloud",objClass+"will be created.");
        if (this.shootFog == null || !shootFog.getClass().equals(objClass)){
            Constructor<OB> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                this.shootFog = constructor.newInstance(this.surfaceView);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.shootFog.setPosition(posX,posY);
            return this.shootFog;

        }else{
            IObjectScene objt = null;
            try {
                objt  = this.shootFog.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(posX,posY);
            return (OB) objt;
        }
    }

    /**
     *shoot fog from enemy
     *
     * @param objClass
     * @param posX
     * @param posY
     * @return
     */
    public OB createEnemyShootFog(Class<OB> objClass,float posX,float posY){
        //temp object is null or conflict in type
        Log.i("cloud",objClass+"will be created.");
        if (this.enemyShootFog == null || !enemyShootFog.getClass().equals(objClass)){
            Constructor<OB> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                this.enemyShootFog = constructor.newInstance(this.surfaceView);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.enemyShootFog.setPosition(posX,posY);
            return this.enemyShootFog;

        }else{
            IObjectScene objt = null;
            try {
                objt  = this.enemyShootFog.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(posX,posY);
            return (OB) objt;
        }
    }

    /**
     *
     * @param objClass
     * @param posX
     * @param posY
     * @return
     */
    public OB createBloodstain(Class<OB> objClass,float posX,float posY){
        //temp object is null or conflict in type
        Log.i("blood","posX:"+posX+",posY:"+posY);
        if (this.bloodstain == null || !bloodstain.getClass().equals(objClass)){
            Constructor<OB> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                this.bloodstain = constructor.newInstance(this.surfaceView);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            this.bloodstain.setPosition(posX,posY);
            return this.bloodstain;

        }else{
            IObjectScene objt = null;
            try {
                objt  = this.bloodstain.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(posX,posY);
            return (OB) objt;
        }
    }


    public Mountains createMountain(Class<OB> objClass,float posX,float posY){
        //temp object is null or conflict in type
        if (this.backMountain == null || !backMountain.getClass().equals(objClass)){
            Constructor<OB> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            this.backMountain = new Mountains(this.surfaceView);
            this.backMountain.setPosition(posX,posY);
            return this.backMountain;

        }else{
            Mountains objt = null;
            try {
                objt  = (Mountains)this.backMountain.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(posX,posY);
            return objt;
        }
    }

    /**
     * 模仿上个方法，性能
     * then return object is useless further more never insert return value to list again
     *
     *
     * @param objClass
     * @param posX
     * @param posY
     * @return
     */
    public OB createObject(Class<OB> objClass,float posX,float posY){
        //temp object is null or conflict in type
        Log.i("cloud",objClass+"will be created.");
        if (this.cloudyObj == null || !cloudyObj.getClass().equals(objClass)){
            Log.i("cloud",objClass+" new");
            Constructor<OB> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                cloudyObj = constructor.newInstance(this.surfaceView);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            cloudyObj.setPosition(this.randomPoint(posX,50),this.randomPoint(posY,50));
            return cloudyObj;

        }else{
            Log.i("cloud",objClass+" clone");
            IObjectScene objt = null;
            try {
                objt  = this.cloudyObj.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(this.randomPoint(posX,50),this.randomPoint(posY,50));
            return (OB) objt;
        }
    }


    /**
     * just for all of explosion effect
     * @param objClass
     * @param expBitmaps for individual explosion effect bitmaps
     * @param posX
     * @param posY
     * @return
     */
    public Explosion createBoomObject(Class<Explosion> objClass,float posX,float posY ,Bitmap[] expBitmaps){
        //temp object is null or conflict in type
        if (this.boomFog == null || !boomFog.getClass().equals(objClass)){
            Constructor<Explosion> constructor = null;
            try {
                constructor = objClass.getConstructor(new Class[]{SurfaceView.class});
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                this.boomFog = constructor.newInstance(this.surfaceView);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            boomFog.setPosition(posX,posY);
            if (expBitmaps != null){
                boomFog.setBitmaps(expBitmaps);
            }
            return boomFog;

        }else{
            Log.i("cloud",objClass+" clone");
            Explosion objt = null;
            try {
                objt  = (Explosion) this.boomFog.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            objt.setPosition(posX,posY);
            if (expBitmaps != null){
                objt.setBitmaps(expBitmaps);
            }
            return objt;
        }
    }

    /**
     *
     * @param xoy
     * @param sumFixPoint sumpoint will be randomlize,half of that is 0 point
     * @return
     */
    private float randomPoint(float xoy,float sumFixPoint){
        sumFixPoint = ImageTools.positionConvert(sumFixPoint);
        return  xoy+randomPos.nextInt((int)sumFixPoint)-((int)sumFixPoint>>1);
    }

}
