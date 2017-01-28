package com.ck.ind.finddir.bean.scene;

import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.BannerMan;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.play.PlayScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sqlite.GameStore;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBeanMenu extends AbsSceneBean {


    public SceneBeanMenu(PlayScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill=1;
        this.sceneDescription = "WELCOME!";
    }


    @Override
    public void enemyApproach() {
        super.generateEnemyOnce(Boy.class, 10, 50);
        super.generateEnemyOnce(BannerMan.class, 6, 0);
        super.generateFormOnce(Crasher.class, 3, 10);
        super.generateEnemyOnce(Matchlocker.class, 9, 1);
        super.generateEnemyOnce(Archer.class,12,65);
        super.generateEnemyOnce(Boy.class,11,30);
        //super.generateEnemyOnce(Boy.class,2,10);


    }


    @Override
     public void initScene() {
/*        GameStore gameStore = GameStore.findGameStore(this.surfaceView.getContext());
        gameStore.updateWpTo(Constant.ARROWS_ID,1);
        super.villageLiving();
        Log.i("sqlite","arrows to 1");*/
    }

    @Override
    public Boolean stageOver() {
        return false;
    }

    @Override
    public AbsSceneBean initNextStage() {
        return null;
    }
}
