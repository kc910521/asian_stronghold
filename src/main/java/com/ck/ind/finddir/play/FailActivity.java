package com.ck.ind.finddir.play;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.StartActivity;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.factory.SceneFactory;
import com.ck.ind.finddir.scene.MainScene;

public class FailActivity extends Activity {

    //private GameStore gameStore = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fail);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initEvent();


    }

    private void initEvent(){
/*        gameStore = GameStore.findGameStore(this);
        gameStore.restoreWpDb();*/
        SceneFactory.setNowScene(null);
        Itower.restoreHP();
        //MainScene.findMainScence(null).restoreScene();
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
