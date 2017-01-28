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
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.play.IMainScene;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean12 extends AbsSceneBean {


    public SceneBean12(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 4;
        this.generateIndexNumber = 2;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc12_title_01);
        //!
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if(enemyWavesStill == 4){
                messengerDispear(1);
            }else if (enemyWavesStill == 3){
                super.generateEnemyOnce(HeavyInfantry.class, 11, 0);
                super.generateEnemyOnce(HeavyInfantry.class, 11, 50);

                super.generateFormOnce(Crasher.class, 4, 0);

                super.generateEnemyOnce(Archer.class, 9, 50);
                super.generateEnemyOnce(Archer.class, 8, 70);
                super.generateEnemyOnce(Archer.class, 9, 100);
                super.generateEnemyOnce(Archer.class, 10, 120);

                super.generateEnemyOnce(Matchlocker.class, 11, 110);
                super.generateEnemyOnce(Matchlocker.class, 11, 130);
                super.generateEnemyOnce(Matchlocker.class, 11, 150);

            }else if (enemyWavesStill == 2){
                this.sendMsg(R.string.sc12_msg_01);
                super.generateEnemyOnce(BannerMan.class, 7, 55);
                super.generateFormOnce(BannerMan.class, 8, 75);
                super.generateFormOnce(BannerMan.class, 9, 95);
                super.generateFormOnce(BannerMan.class, 10, 155);
                super.generateFormOnce(BannerMan.class, 11, 175);
                this.generateIndexNumber = 0;
            }else if (enemyWavesStill == 1){
                this.sendMsg(R.string.sc12_msg_02);

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
        return new SceneBean13(this.mainScene,this.surfaceView);
    }

}
