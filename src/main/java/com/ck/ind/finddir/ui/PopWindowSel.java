package com.ck.ind.finddir.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.MainActivity;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.scene.SceneBean99;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.tower.SkillPojo;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sharep.PropertiesService;
import com.ck.ind.finddir.sqlite.GameStore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by KCSTATION on 2015/8/11.
 * alert in store activity
 */
public class PopWindowSel {

    private Dialog selectDialog;
    Activity activityTarget = null;
    TextView popInf = null;
    Button negaButton = null;//cancel
    Button posButton = null;//ok

    GameStore gameStore = null;

    public PopWindowSel(Activity activityTarget) {
        this.activityTarget = activityTarget;
        /* 初始化普通对话框。并设置样式 */
        selectDialog = new MyDialog(activityTarget, R.style.sp_dialog);
        selectDialog.setContentView(R.layout.pop_win_sel);
        popInf = (TextView) selectDialog
                .findViewById(R.id.Sel_TextView01);
        posButton = (Button) selectDialog.findViewById(R.id.ok_btn);
        negaButton = (Button) selectDialog.findViewById(R.id.cancel_btn);
        selectDialog.setCanceledOnTouchOutside(false);
        gameStore = GameStore.findGameStore(this.activityTarget);
    }
    /*
    choose skill popWindow
     */
    public PopWindowSel popSkillWindow(String winContent,final String skillId){
        popInf.setText(winContent);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("db", "1 from update:" + skillId);
                int nowLevel = 1;
                //还未开始的那关的数
                int nowStageLevel = Integer.parseInt(MainScene.sceneBean.getClass().getName().toLowerCase().split(".scenebean")[1]);
                boolean isExistInUse = false;

                List<SkillPojo> skillPojoList =  gameStore.findAvailableWeapon();

                Iterator<SkillPojo> resList =  skillPojoList.iterator();

                SkillPojo tempIfInExist = null;
                SkillPojo skMap = null;
                while (resList.hasNext()){
                    skMap = resList.next();
                    Log.i("db", "skMap:" + skMap+",skillId:"+skillId+";");
                    if (skMap.getWid().equals(skillId)){
                        nowLevel = skMap.getWpLevel();
                        isExistInUse = true;
                        tempIfInExist = new SkillPojo();
                        tempIfInExist.setWpInterval(skMap.getWpInterval());
                        tempIfInExist.setIsEnable(1);
                        tempIfInExist.setWpName(skillId);
                        tempIfInExist.setWid(skillId);
                        tempIfInExist.setWpLevel(skMap.getWpLevel() + 1);
                        tempIfInExist.setWpStage(nowStageLevel);

                        break;
                    }
                }

                Log.i("db", "isExistInUse:" + isExistInUse +",nowLevel:"+(nowLevel+1));
                if (isExistInUse && skMap != null && tempIfInExist != null){
                    skillPojoList.remove(skMap);
                    skillPojoList.add(tempIfInExist);
                    gameStore.resembleWeaponToDb(skillPojoList, nowStageLevel);
                    //gameStore.updateWpLevelTo(skillId,++nowLevel);
                }else{
                    //gameStore.updateWpTo(skillId, 1);
                    SkillPojo skillPojo = new SkillPojo();
                    skillPojo.setIsEnable(1);
                    skillPojo.setWpStage(nowStageLevel);
                    skillPojo.setWpLevel(1);
                    skillPojo.setWid(skillId);
                    skillPojo.setWpName(skillId);
                    gameStore.insertWpWithLevel(skillPojo);
                    gameStore.resembleWeaponToDb(skillPojoList,nowStageLevel);
                }




                //save skill over
                //save stage to database


                Log.i("mtest", MainScene.sceneBean.getClass() + ",and:" + nowStageLevel);



                //判断是否为挑战模式
                if (SceneBean99.class.equals(MainScene.sceneBean.getClass()) ){
                    gameStore.updateChallengeStage(Constant.PLAYER_NAME, Itower.getHP(), ++Constant.ENDLESS_COUNT);
                    (new PropertiesService(activityTarget)).saveBy(Constant.GOS_IN_TOUR, (99 + Constant.ENDLESS_COUNT )+"");
                }else{
                    int aacc = gameStore.insertStage(Constant.PLAYER_NAME,nowStageLevel,Itower.getHP());
                    Log.i("insertStage", "aacc:" + aacc);
                    (new PropertiesService(activityTarget)).saveBy(Constant.GOS_IN_TOUR, nowStageLevel + "");
                }


                selectDialog.dismiss();


                /*add HP*/
                if(Constant.MANAGER_ID.equals(skillId)){
                    Itower.setHP(Itower.getHP() + 50);
                }

                goToMainScene();
            }
        });
        negaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectDialog.dismiss();
            }
        });

        return this;
    }


    public void show(){
        if (selectDialog.isShowing()){
            selectDialog.cancel();
        }
        selectDialog.show();
    }

    /**
     * to game scene
     */
    private void goToMainScene(){
        Intent intent =  new Intent(activityTarget.getApplicationContext(),MainActivity.class);

        intent.putExtra(Constant.PLAYER_NAME, UUID.randomUUID().toString());
        activityTarget.startActivity(intent);
        activityTarget.finish();
        MainScene.findMainScence(null).setNeedInitliaze(true);

    }
}
