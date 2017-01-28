package com.ck.ind.finddir.ai;

import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.scene.SceneBean02;
import com.ck.ind.finddir.bean.scene.SceneBean09;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.factory.BulletFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;
import com.ck.ind.finddir.ui.LaunchButton;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by KCSTATION on 2016/1/4.
 */
public class AutoLaunch {

    private SurfaceView gameView = null;

    private Random random = null;

    public AutoLaunch(SurfaceView gameView){
        this.gameView = gameView;
        this.random = new Random();
    }

    public void launchByTag(LaunchButton skBtn){
        //in simple
        //Log.i("sendMessage","OIL");
        if ("OIL".equals(skBtn.getTag()) && skBtn.isInReady() && MainScene.findMainScence(this.gameView).getEnemyList().size() > 0 ){
            Iterator<IEnemy> eListIterator = MainScene.findMainScence(this.gameView).getEnemyList().iterator();
            while (eListIterator.hasNext()){
                if (eListIterator.next().getX() <
                        (Itower.initUserTower(this.gameView).getX() + Itower.initUserTower(this.gameView).getWidth() + ImageTools.positionConvert(100))){
                    MainScene.findMainScence(this.gameView).addOilAtt(Itower.I_POWER);
                    skBtn.setInReady(false);
                    MainScene.findMainScence(this.gameView).sendLastLaunchTS(skBtn.getTag() + "001", System.currentTimeMillis());

                    break;
                }
            }

        }
    }

    public void checkAutoLaunchLoop(String launchId){
        List<IEnemy> eneList = MainScene.findMainScence(this.gameView).getEnemyList();
        Itower itower = Itower.initUserTower(this.gameView);

        if (eneList.size() >0 &&
                //itower.getWpMap().containsKey(Constant.AUTO_DEF_ID) && //ø…“‘»•µÙ
                //sb9 no need auto launch
                itower.wpIsReady(Constant.AUTO_DEF_ID,true) && !SceneBean09.class.equals(MainScene.sceneBean.getClass())
                ){
            //launch ok
            int pointFix = 1;
            if (this.random.nextInt(4) == 1){
                //∆Î…‰…‰ª˜
                pointFix = 0;
            }
            try {
                MainScene.findMainScence(this.gameView).getBulletList().addAll(
                    BulletFactory.initFactory(this.gameView).createAutoDef(
                        1,
                        pointFix));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

        }
        eneList = null;
        itower = null;

    }
}
