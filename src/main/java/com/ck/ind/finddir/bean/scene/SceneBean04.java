package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean04 extends AbsSceneBean {


    public SceneBean04(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill=3;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc04_title_01);
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 2){

                super.generateFormOnce(Crasher.class,2,0);
                super.generateEnemyOnce(Boy.class, 6, 20);
            }else if (enemyWavesStill == 1){
                this.sendMsg(R.string.sc04_msg_01);
                super.generateEnemyOnce(Archer.class, 13, 50);

            }

            super.generateEnemyOnce(Boy.class, 13, 50);
            super.generateFormOnce(Crasher.class, 1, 90);
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
        return new SceneBean05(this.mainScene, this.surfaceView);
    }

}
