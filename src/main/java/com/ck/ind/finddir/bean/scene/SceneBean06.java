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
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean06 extends AbsSceneBean {


    public SceneBean06(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 4;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc06_title_01);
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 4 ){
                this.generateIndexNumber = 10;
                this.sendMsg(R.string.sc06_msg_01);
                super.generateFormOnce(Crasher.class, 4, 20);
                super.generateFormOnce(Crasher.class, 5, 80);
                super.generateFormOnce(Crasher.class, 4, 140);
            }else if (enemyWavesStill == 3){
                this.sendMsg(R.string.sc06_msg_02);
                super.generateFormOnce(Crasher.class, 4, 20);
                super.generateFormOnce(Crasher.class, 5, 80);
                super.generateFormOnce(Crasher.class, 4, 140);
            }
            else if (enemyWavesStill == 2){
                super.generateEnemyOnce(HeavyInfantry.class, 3, 70);
                super.generateEnemyOnce(BannerMan.class, 2, 50);
                super.generateEnemyOnce(Archer.class, 8, 70);
                super.generateEnemyOnce(Archer.class, 10, 85);
                super.generateEnemyOnce(Boy.class, 12, 100);
                super.generateFormOnce(Crasher.class, 4, 20);
            }else if (enemyWavesStill == 1){
                this.generateIndexNumber = 0;
                this.sendMsg(R.string.sc06_msg_03);
                super.generateEnemyOnce(Archer.class, 7, 75);
                super.generateEnemyOnce(BannerMan.class, 6, 0);
                super.generateEnemyOnce(BannerMan.class, 4, 20);
                super.generateEnemyOnce(HeavyInfantry.class, 3, 40);
                super.generateEnemyOnce(Archer.class, 8, 50);
                super.generateEnemyOnce(Boy.class, 12, 100);
            }


            this.enemyWavesStill--;
        }else{//over
            this.stageOver();
        }


    }

    @Override
    public void initScene() {
        super.villageLiving();
        Itower.effectHpValue(180);
    }

    @Override
    public AbsSceneBean initNextStage() {
        return new SceneBean07(this.mainScene, this.surfaceView);
    }

}
