package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.JingTower;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean05 extends AbsSceneBean {


    public SceneBean05(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill=3;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc05_title_01);
        this.generateIndexNumber = 15;
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//ÒÀÈ»ÓÐ²¨

            if (enemyWavesStill == 2){
                this.generateIndexNumber = 30;
                super.generateFormOnce(Crasher.class, 2, 160);
                super.generateEnemyOnce(Boy.class, 9, 20);
                super.generateEnemyOnce(Archer.class, 13, 80);
                super.generateEnemyOnce(Archer.class, 10, 60);
                super.generateEnemyOnce(Archer.class, 5, 50);
                super.generateEnemyOnce(Boy.class, 13, 60);
                super.generateEnemyOnce(Boy.class, 12, 100);
                this.sendMsg(R.string.sc05_msg_02);
                super.generatePoint(JingTower.class, 10, Constant.SCREEN_HEIGHT>>2);
                super.generateEnemyOnce(HeavyInfantry.class, 5, 20);

            }else if (enemyWavesStill == 1){
                this.generateIndexNumber = 0;
                super.generateFormOnce(Crasher.class, 3, 50);
                super.generateEnemyOnce(Archer.class, 12, 130);
                super.generateEnemyOnce(Boy.class, 12, 105);

                super.generateEnemyOnce(HeavyInfantry.class, 5, 40);
            }else if (enemyWavesStill == 3){
                this.sendMsg(R.string.sc05_msg_01);
                super.generateEnemyOnce(Boy.class, 7, 85);
                super.generateEnemyOnce(Archer.class, 13, 80);
                super.generateEnemyOnce(Archer.class, 5, 50);


            }
            super.generateEnemyOnce(Boy.class, 12, 65);
            super.generateEnemyOnce(Boy.class, 12, 85);


            this.enemyWavesStill--;
        }else{//over
            this.stageOver();
        }


    }

    @Override
    public void initScene() {
        super.villageLiving();
    }

    @Override
    public AbsSceneBean initNextStage() {
        return new SceneBean06(this.mainScene, this.surfaceView);
    }

}
