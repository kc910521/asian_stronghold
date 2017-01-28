package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean08 extends AbsSceneBean {


    public SceneBean08(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 3;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc08_title_01);
        //!
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 3){
                this.generateIndexNumber = 10;
                super.generateEnemyOnce(BannerMan.class, 3, 100);
                super.generateEnemyOnce(HeavyInfantry.class, 12, 20);
                super.generateEnemyOnce(HeavyInfantry.class, 10, 40);
                super.generateEnemyOnce(HeavyInfantry.class, 11, 65);
                super.generateEnemyOnce(Archer.class, 10, 80);
                super.generateEnemyOnce(Archer.class, 10, 100);
                super.generateEnemyOnce(Archer.class, 10, 120);
                super.generateEnemyOnce(Archer.class, 10, 140);
                super.generateFormOnce(Crasher.class, 5, 0);
            }else if (enemyWavesStill == 2){
                this.sendMsg(R.string.sc08_msg_01);
                this.generateIndexNumber = 0;
                super.generateEnemyOnce(BannerMan.class, 5, 100);
                super.generateEnemyOnce(HeavyInfantry.class, 5, 20);
                super.generateEnemyOnce(Archer.class, 10, 40);
                super.generateEnemyOnce(Archer.class, 11, 65);
                super.generateEnemyOnce(Archer.class, 10, 80);
                super.generateEnemyOnce(Archer.class, 10, 100);
                super.generateEnemyOnce(Archer.class, 10, 120);
                super.generateEnemyOnce(Archer.class, 10, 140);
                super.generateFormOnce(Crasher.class, 5, 0);
                super.generateFormOnce(Crasher.class, 5, 60);
            }else if (enemyWavesStill == 1){
                this.sendMsg(R.string.sc08_msg_02);


            }
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
        return new SceneBean09(this.mainScene, this.surfaceView);
    }
}
