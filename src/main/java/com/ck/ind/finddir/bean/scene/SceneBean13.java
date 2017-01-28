package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.Elephant;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.play.IMainScene;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean13 extends AbsSceneBean {


    public SceneBean13(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 2;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc13_title_01);
        //!
        this.isLastStage = true;
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){
            if (enemyWavesStill == 2){
                this.generateIndexNumber = 20;

                super.generateEnemyOnce(HeavyInfantry.class, 10, 155);
                super.generateEnemyOnce(HeavyInfantry.class, 12, 100);
                super.generateFormOnce(HeavyInfantry.class, 9, 120);
                super.generateEnemyOnce(Archer.class, 7, 140);
                super.generateEnemyOnce(Archer.class, 7, 150);
                super.generateEnemyOnce(Archer.class, 10, 140);
                super.generateEnemyOnce(BannerMan.class, 7, 185);
                super.generateFormOnce(Crasher.class, 4, 5);
                super.generateFormOnce(Crasher.class, 5, 65);

            }else if (enemyWavesStill == 1){
                this.generateIndexNumber = 0;
                this.sendMsg(R.string.sc13_msg_01);
                super.generateEnemyOnce(Matchlocker.class, 10, 175);
                super.generateEnemyOnce(Matchlocker.class, 12, 100);
                super.generateEnemyOnce(Matchlocker.class, 10, 120);
                super.generateEnemyOnce(Archer.class, 7, 140);
                super.generateFormOnce(Elephant.class, 1, 190);
                super.generateEnemyOnce(Archer.class, 7, 150);
                super.generateFormOnce(Crasher.class, 4, 105);
                super.generateEnemyOnce(BannerMan.class, 7, 185);
                super.generateEnemyOnce(BannerMan.class, 5, 105);
            }


            this.enemyWavesStill--;
        }else{//over
            this.stageOver();
        }


    }

    @Override
    public void initScene() {
        super.villageLiving();
        Itower.effectHpValue(500);
    }

    @Override
    public AbsSceneBean initNextStage() {
        return null;
    }

}
