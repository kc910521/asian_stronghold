package com.ck.ind.finddir.play;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.StartActivity;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.factory.SceneFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sharep.PropertiesService;
import com.ck.ind.finddir.sqlite.GameStore;

public class VictoryActivity extends Activity {

    private GameStore gameStore = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_victory);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initEvent();


    }

    private void initEvent(){
        //gameStore = GameStore.findGameStore(this);
        /*gameStore.restoreWpDb();
        gameStore.resetStage(Constant.PLAYER_NAME);*/

        //open victory mode
        PropertiesService propertiesService = new PropertiesService(this);
        propertiesService.saveBy(Constant.IS_VICTORY_MODE, "true");


        SceneFactory.setNowScene(null);
        Itower.restoreHP();
        if (MainScene.findMainScence(null) != null){
            MainScene.findMainScence(null).restoreScene();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //¼à¿Ø/À¹½Ø/ÆÁ±Î·µ»Ø¼ü
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
