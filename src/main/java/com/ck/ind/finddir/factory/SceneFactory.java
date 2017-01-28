package com.ck.ind.finddir.factory;

import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.scene.AbsSceneBean;
import com.ck.ind.finddir.bean.scene.SceneBean01;
import com.ck.ind.finddir.bean.scene.SceneBean99;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.sqlite.GameStore;

import java.util.List;
import java.util.Map;

/**
 * 生成场景专用
 * Created by KCSTATION on 2015/9/11.
 */
public class SceneFactory {
    private static AbsSceneBean nowScene = null;
    private static SceneFactory sceneFactory = null;

    private IMainScene mainScene = null;
    private SurfaceView surfaceView = null;
    private GameStore gameStore = null;


    private SceneFactory(IMainScene mainScene, SurfaceView surfaceView){
        this.mainScene = mainScene;
        this.surfaceView = surfaceView;
        gameStore  = GameStore.findGameStore(surfaceView.getContext());
        Log.i("fdebug","SceneFactory init");
        //nowScene = this.getRightScene();
    }


    public static SceneFactory initSceneFactory(IMainScene mainScene, SurfaceView surfaceView){
        if (SceneFactory.sceneFactory == null){
            SceneFactory.sceneFactory = new SceneFactory(mainScene,surfaceView);
        }
        return SceneFactory.sceneFactory;
    }

    public static void clearTemp(){
        //mainScene = null;
        SceneFactory.sceneFactory = null;
        SceneFactory.nowScene = null;
    }
    /**
     * 风险方法
     * 带优化
     */
/*    public static SceneFactory findSceneFactory(){
        if (SceneFactory.sceneFactory == null){
            //
        }

        return SceneFactory.sceneFactory;
    }*/

    /**
     * 得到修正的场景bean
     * 实际应命名为getNextScene，以免重名混乱
     * @return
     */
    public AbsSceneBean getRightScene() {
        if (SceneFactory.nowScene == null){
            //in game init,get stage in db
            List<Map<String, Object>> stList = gameStore.loadStageInf(Constant.PLAYER_NAME);
            Map<String, Object> stgMap = null;
            if (stList != null && !stList.isEmpty()){
                stgMap = stList.get(0);
            }

            nowScene = new SceneBean01(mainScene,surfaceView);
            Itower.I_POWER = 5;
            if (stgMap == null || stgMap.isEmpty() || Integer.valueOf(stgMap.get("stage")+"") == 0 ){
                //hp in test
            }else if(Integer.valueOf(stgMap.get("stage")+"") == 99){
                nowScene = new SceneBean99(this.mainScene,this.surfaceView);
                Itower.setHP(Integer.valueOf(stgMap.get("hp") + ""));
                Constant.ENDLESS_COUNT = Integer.valueOf(stgMap.get("seq")+"");
                Log.i("cx","Constant.ENDLESS_COUNT:"+Constant.ENDLESS_COUNT);
            }
            else{
                //reflect bean
                nowScene = nowScene.takeSceneBeanBy(Integer.valueOf(stgMap.get("stage")+""));

                Itower.setHP(Integer.valueOf(stgMap.get("hp") + ""));
            }

            //nowScene.initScene();
            Log.i("scene","SceneFactory.nowScene == null");
        }else if(SceneFactory.nowScene.initNextStage()==null){
            //last level of scene
            try {
                throw new Exception("SceneFactory.nowScene.getNextScene()==null");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("scene","SceneFactory.nowScene next == null");
        }else{
            nowScene = nowScene.initNextStage();
            //nowScene.initScene();
            Log.i("scene","SceneFactory.nowScene next not null");
        }


        return nowScene;
    }


    public AbsSceneBean getNowScene() {
        return nowScene;
    }

    public static void setNowScene(AbsSceneBean nowScene) {
        SceneFactory.nowScene = nowScene;
    }
}
