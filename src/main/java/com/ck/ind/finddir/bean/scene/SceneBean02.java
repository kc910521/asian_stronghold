package com.ck.ind.finddir.bean.scene;

import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.JingTower;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.tower.SkillPojo;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sharep.PropertiesService;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean02 extends AbsSceneBean {



    public SceneBean02(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.generateIndexNumber = 0;//--------------------------------------5
        this.enemyWavesStill = 2;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc02_title_01);
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            //super.generateEnemyOnce(Boy.class, 2, 10);
            if (this.enemyWavesStill == 1){
                this.generateIndexNumber = 0;
                this.sendMsg(R.string.sc02_msg_01);
                super.generateEnemyOnce(Archer.class, 8, 60);
                super.generateEnemyOnce(Boy.class, 8, 100);
                super.generateEnemyOnce(Boy.class, 12, 90);
            }else {
                super.generateEnemyOnce(Boy.class,12,70);
                super.generateEnemyOnce(Crasher.class, 1, 10);//90

            }
            this.enemyWavesStill--;
        }else{//over
            this.stageOver();
            //this.enemyWavesStill=1;
        }


    }

    @Override
    public void initScene() {

        super.villageLiving();

        //gameStore.updateWpTo(Constant.CATAPULT_ID, 1);
    }

    @Override
    public AbsSceneBean initNextStage() {
        return new SceneBean03(this.mainScene, this.surfaceView);
    }
}
