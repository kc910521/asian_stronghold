package com.ck.ind.finddir.bean.scene;

import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.spirt.Archer;
import com.ck.ind.finddir.bean.spirt.Bird;
import com.ck.ind.finddir.bean.spirt.Boar;
import com.ck.ind.finddir.bean.spirt.Boy;
import com.ck.ind.finddir.bean.spirt.Crasher;
import com.ck.ind.finddir.bean.spirt.Elephant;
import com.ck.ind.finddir.bean.spirt.HeavyInfantry;
import com.ck.ind.finddir.bean.spirt.JingTower;
import com.ck.ind.finddir.bean.spirt.Matchlocker;
import com.ck.ind.finddir.bean.spirt.MengGo;
import com.ck.ind.finddir.bean.tower.Arrow;
import com.ck.ind.finddir.bean.tower.SkillPojo;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sqlite.GameStore;
import com.ck.ind.finddir.thread.AlertHandler;
import com.ck.ind.finddir.ui.PopWindow;

/**
 * Created by KCSTATION on 2015/9/7.
 */
public class SceneBean01 extends AbsSceneBean {


    public SceneBean01(IMainScene mainScene, SurfaceView surfaceView) {
        super(mainScene, surfaceView);
        this.enemyWavesStill=2;
        this.sceneDescription = surfaceView.getContext().getString(R.string.sc01_title_01)+"\r\n"+surfaceView.getContext().getString(R.string.sc01_msg_01);

    }


    @Override
    public void enemyApproach() {
        super.enemyApproach();
        if (this.enemyWavesStill>=1){//依然有波
            if (this.enemyWavesStill == 2){
                this.sendMsg(R.string.sc01_msg_02);
                super.generateEnemyOnce(HeavyInfantry.class, 1, 100);
            }else if (this.enemyWavesStill == 1){
                this.sendMsg(R.string.sc01_msg_03);
                super.generateEnemyOnce(Boy.class, 7, 100);//7
            }
            this.enemyWavesStill--;
        }else{//over
            GameStore gameStore = GameStore.findGameStore(this.surfaceView.getContext());
            SkillPojo skillPojo = new SkillPojo();
            skillPojo.setIsEnable(1);
            skillPojo.setWpStage(1);
            skillPojo.setWpLevel(1);
            skillPojo.setWid(Constant.CATAPULT_ID);
            skillPojo.setWpName(Constant.CATAPULT_ID);
            gameStore.insertWpWithLevel(skillPojo);
            this.stageOver();
        }
    }


    @Override
     public void initScene() {

        /**
         * warning!
         * 因为在进技能界面之前，此方法已经进行初始化
         */
        GameStore gameStore = GameStore.findGameStore(this.surfaceView.getContext());
        SkillPojo skillPojo = new SkillPojo();
        skillPojo.setIsEnable(1);
        skillPojo.setWpStage(1);
        skillPojo.setWpLevel(1);
        skillPojo.setWid(Constant.ARROWS_ID);
        skillPojo.setWpName(Constant.ARROWS_ID);
        gameStore.insertWpWithLevel(skillPojo);
        //gameStore.updateWpTo(Constant.ARROWS_ID,1);
        super.villageLiving();
    }

    @Override
    public AbsSceneBean initNextStage() {
        return new SceneBean02(this.mainScene, this.surfaceView);
    }
}
