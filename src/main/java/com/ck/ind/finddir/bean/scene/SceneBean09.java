package com.ck.ind.finddir.bean.scene;

import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Bird;
import com.ck.ind.finddir.bean.spirt.Boar;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean09 extends AbsSceneBean {


    public SceneBean09(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill = 4;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc09_title_01);
        //!

    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//依然有波
            if (enemyWavesStill == 4){
                this.sendMsg(R.string.sc09_msg_01);
                super.generateEnemyOnce(Bird.class, 5, 20);
                super.generateEnemyOnce(Bird.class, 7, 40);
                super.generateEnemyOnce(Bird.class, 7, 55);
            }else if (enemyWavesStill == 3){
                this.sendMsg(R.string.sc09_msg_02);
                super.generateEnemyOnce(Bird.class, 3, 55);
                super.generateEnemyOnce(Boar.class, 1, -200);
            }else if (enemyWavesStill == 2){
                super.generateEnemyOnce(Bird.class, 5, 55);
                super.generateEnemyOnce(Boar.class, 3, -100);
            }else if (enemyWavesStill == 1){
                this.sendMsg(R.string.sc09_msg_03);
                //super.generateEnemyOnce(Bird.class, 2, 55);

            }
            //动物

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
        return new SceneBean10(this.mainScene,this.surfaceView);
    }

}
