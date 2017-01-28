package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.play.IMainScene;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean11 extends AbsSceneBean {


    public SceneBean11(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 2;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc11_title_01);
        //!
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 2){
                this.generateIndexNumber = 15;
                super.generateEnemyOnce(BannerMan.class, 7, 55);
                super.generateFormOnce(BannerMan.class, 8, 75);
                super.generateFormOnce(BannerMan.class, 9, 95);
                super.generateEnemyOnce(HeavyInfantry.class, 10, 70);
                super.generateEnemyOnce(HeavyInfantry.class, 11, 88);
                super.generateFormOnce(Crasher.class,4,0);
                super.generateFormOnce(Crasher.class,4,0);

            }else if (enemyWavesStill == 1){
                this.generateIndexNumber = 0;
                //this.sendMsg(R.string.sc11_msg_01);
                super.generateEnemyOnce(BannerMan.class, 8, 55);
                super.generateEnemyOnce(Matchlocker.class, 11, 50);
                super.generateEnemyOnce(Matchlocker.class, 11, 75);
                super.generateEnemyOnce(Matchlocker.class, 11, 100);
                super.generateEnemyOnce(Matchlocker.class, 11, 125);
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
        return new SceneBean12(this.mainScene,this.surfaceView);
    }

}
