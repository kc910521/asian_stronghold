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
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.spirt.MengGo;
import com.ck.ind.finddir.bean.spirt.Messenger;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean07 extends AbsSceneBean {


    public SceneBean07(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill=2;
        this.generateIndexNumber = 0;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc07_title_01);
    }

    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//“¿»ª”–≤®
            if (enemyWavesStill == 2){
                messengerDispear(1);
                this.enemyWavesStill--;
            }else if (enemyWavesStill == 1){
                if (this.mg_isdead){
                    this.enemyWavesStill--;
                }else{
                    this.sendMsg(R.string.sc07_msg_01);
                    super.generateEnemyOnce(MengGo.class, 1, 50);//mengge dispear
                    super.generateEnemyOnce(BannerMan.class, 3, 10);
                    super.generateEnemyOnce(BannerMan.class, 4, 60);
                    super.generateEnemyOnce(Boy.class, 12, 50);
                    super.generateEnemyOnce(Boy.class, 12, 70);
                    super.generateEnemyOnce(HeavyInfantry.class, 9, 50);
                    super.generateEnemyOnce(HeavyInfantry.class, 6, 80);
                    super.generateEnemyOnce(HeavyInfantry.class, 6, 100);
                    super.generateEnemyOnce(Archer.class, 12, 90);
                    super.generateEnemyOnce(Archer.class, 12, 110);
                    super.generateEnemyOnce(Archer.class, 10, 130);
                    super.generateFormOnce(Crasher.class, 4, 0);

                }


            }


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
        return new SceneBean08(this.mainScene, this.surfaceView);
    }

}
