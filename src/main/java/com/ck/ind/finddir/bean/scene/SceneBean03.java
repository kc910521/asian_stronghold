package com.ck.ind.finddir.bean.scene;

import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.Messenger;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean03 extends AbsSceneBean {


    public SceneBean03(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill=4;
        this.generateIndexNumber = 0;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc03_title_01);
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 4){
                messengerDispear(1);
            }else if (enemyWavesStill == 3){
                super.generateFormOnce(Crasher.class, 3, 0);
                super.generateEnemyOnce(Boy.class, 9, 20);
                this.generateIndexNumber = 6;
            }else if (enemyWavesStill == 2){
                this.sendMsg(R.string.sc03_msg_01);
                super.generateFormOnce(HeavyInfantry.class, 2, 40);
                super.generateFormOnce(Crasher.class, 2, 150);
                this.generateIndexNumber = 0;
            }else if (enemyWavesStill == 1){
                this.sendMsg(R.string.sc03_msg_02);
                super.generateEnemyOnce(Boy.class, 13, 90);
                super.generateFormOnce(Crasher.class, 3, 150);
            }
            if (enemyWavesStill != 4){
                super.generateEnemyOnce(Archer.class, 13, 50);
                super.generateEnemyOnce(Boy.class, 13, 50);
                super.generateEnemyOnce(Boy.class, 7, 65);
                super.generateFormOnce(Crasher.class, 4, 90);
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
        return new SceneBean04(this.mainScene, this.surfaceView);
    }

}
