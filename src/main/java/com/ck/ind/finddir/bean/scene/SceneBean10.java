package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.spirt.Messenger;
import com.ck.ind.finddir.play.IMainScene;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean10 extends AbsSceneBean {


    public SceneBean10(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 5;
        this.generateIndexNumber = 0;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc10_title_01);
        //!
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();

        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 5){
                messengerDispear(1);
            }else if (enemyWavesStill == 4){
                this.sendMsg(R.string.sc10_msg_01);
                this.generateIndexNumber = 20;
                super.generateEnemyOnce(Matchlocker.class, 8, 155);
                super.generateEnemyOnce(Matchlocker.class, 9, 120);
                super.generateEnemyOnce(BannerMan.class, 5, 105);
                super.generateEnemyOnce(HeavyInfantry.class, 12, 90);
                super.generateEnemyOnce(HeavyInfantry.class, 10, 60);
            }else if (enemyWavesStill == 3){
                this.sendMsg(R.string.sc10_msg_02);
                this.generateIndexNumber = 0;
                super.generateFormOnce(Crasher.class,5,0);
                super.generateFormOnce(Crasher.class,3,85);
                super.generateEnemyOnce(Matchlocker.class, 7, 155);
                super.generateEnemyOnce(Matchlocker.class, 9, 120);
                super.generateEnemyOnce(BannerMan.class, 5, 105);
                super.generateEnemyOnce(HeavyInfantry.class, 12, 90);
                super.generateEnemyOnce(HeavyInfantry.class, 10, 60);

            }else if (enemyWavesStill == 2){
                this.generateIndexNumber = 20;
                super.generateFormOnce(Crasher.class,4,0);
                super.generateFormOnce(Crasher.class,4,85);
                super.generateEnemyOnce(BannerMan.class, 5, 105);
                super.generateEnemyOnce(HeavyInfantry.class, 12, 90);
            }else if(enemyWavesStill == 1){
                this.sendMsg(R.string.sc10_msg_03);
                this.generateIndexNumber = 0;
                super.generateEnemyOnce(Matchlocker.class, 10, 175);
                super.generateEnemyOnce(Matchlocker.class, 12, 100);
                super.generateEnemyOnce(Matchlocker.class, 10, 120);
                super.generateEnemyOnce(BannerMan.class, 7, 185);
                super.generateEnemyOnce(Archer.class, 7, 140);
                super.generateEnemyOnce(Archer.class, 7, 150);
                super.generateFormOnce(Crasher.class, 4, 105);
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
    }

    @Override
    public AbsSceneBean initNextStage() {
        return new SceneBean11(this.mainScene,this.surfaceView);
    }

}
