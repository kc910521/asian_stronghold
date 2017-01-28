package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.JingTower;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.play.IMainScene;

import java.util.Random;

/***
 * endless model
 */
public class SceneBean99 extends AbsSceneBean {

    private Random randomSeed = null;

    //第一个archer回同时生成搭配的攻城车
    private Class[] infantryArrays = new Class[]{Archer.class,Archer.class,BannerMan.class, Boy.class, Matchlocker.class, HeavyInfantry.class};
    //,

    public SceneBean99(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 3;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sced_title_01)+(Constant.ENDLESS_COUNT+1)+surfaceView.getContext().getString(R.string.sced_title_tm);
        //!
        this.isLastStage = false;
        randomSeed = new Random();
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//依然有波
            if (enemyWavesStill == 1){
                this.showImportantMsg();
                this.randomGenerateEnemy();
            }else if (enemyWavesStill == 2 && Constant.ENDLESS_COUNT>5){
                this.randomGenerateEnemy();
            }else if (enemyWavesStill == 3 && Constant.ENDLESS_COUNT>8){
                this.randomGenerateEnemy();
            }


            this.enemyWavesStill--;
        }else{//over
            this.stageOver();
        }
    }

    /**
     * (minInt,maxInt]
     * @param minInt
     * @param maxInt
     * @return
     */
    private int randomIntByRange(int minInt,int maxInt){
        return randomSeed.nextInt(maxInt-minInt)+minInt;
    }

    //随机生成攻城部队,一波
    private void randomGenerateEnemy(){
        if (Constant.ENDLESS_COUNT == 0){
            super.generateEnemyOnce(Archer.class, 7, 0);
        }else{
            int troopLines = this.randomIntByRange((Constant.ENDLESS_COUNT + 1) >> 1, (Constant.ENDLESS_COUNT + 1));
            int extraDistance = 0;
            for (int tl = 0; tl < troopLines; tl++){
                int troopType = this.randomIntByRange(0,infantryArrays.length-1);
                if (troopType == 0){//生成冲车
                    this.generateFormOnce(Crasher.class,this.randomIntByRange(1, 5),tl * 35);
                    extraDistance = 50;
                }
                this.generateEnemyOnce(this.infantryArrays[troopType], this.randomIntByRange(5, 13), tl * 35 + extraDistance);
                extraDistance = 0;
            }
        }
    }

    private void showImportantMsg(){
        switch (Constant.ENDLESS_COUNT){
            case 0:
                this.sendMsg(R.string.endless_mod_00);
                break;
            case 1:
                this.sendMsg(R.string.endless_mod_01);
                break;
            case 4:
                this.sendMsg(R.string.endless_mod_04);
                super.generatePoint(JingTower.class, 10, Constant.SCREEN_HEIGHT >> 2);
                break;
            case 9:
                this.sendMsg(R.string.endless_mod_09);
                break;
            case 13:
                this.sendMsg(R.string.endless_mod_13);
                super.generatePoint(JingTower.class, 10, Constant.SCREEN_HEIGHT >> 2);
                break;
            case 15:
                this.sendMsg(R.string.endless_mod_15);
                this.generateFormOnce(JingTower.class, 3, 0);
                break;
            case 20:break;
            case 30:break;
            case 40:break;
            case 50:break;
        }
    }



    @Override
    public void initScene() {
        super.villageLiving();
    }

    @Override
    public AbsSceneBean initNextStage() {
        return new SceneBean99(this.mainScene,this.surfaceView);
    }

}
