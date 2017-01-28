package com.ck.ind.finddir;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.ind.finddir.ai.AutoLaunch;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.thread.AlertHandler;
import com.ck.ind.finddir.thread.DrawThread;
import com.ck.ind.finddir.thread.ScreenListener;
import com.ck.ind.finddir.toolkits.ImageTools;
import com.ck.ind.finddir.ui.LaunchButton;
import com.ck.ind.finddir.ui.PopWindow;

import java.io.IOException;


public class MainActivity extends Activity {

    MySurfaceView gameView;
    SeekBar seekBar = null;
    TextView powerShow = null;
    private LaunchButton[] skillBtn = null;
    private TextView enemyWav = null;
    private ImageButton ibPause,ibPicShot = null;

    AutoLaunch autoLaunch = null;
    //Handler seekBarhandler = null;
    //signal this is need or not refresh needed activity
    private String uuidSig = "";

    private boolean needQuit = false;

    //设置按钮可用
    private Handler fixBtnHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //set target button bgalpha to 255
            //and to visible
            LaunchButton btn = (LaunchButton)msg.obj;
            btn.setInReady(true);

            autoLaunch.launchByTag(btn);
        }
    };
    //设置波数更新
    private Handler fixWavesInf = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0){
                enemyWav.setText(getString(R.string.last_wave));
            }else{
                enemyWav.setText(getString(R.string.enemy_wainf)+msg.arg1+getString(R.string.enemy_waves));
            }

        }
    };



    private Handler msgHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //需要首先拿到this activity

        WindowManager wm = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.initScreenScale(wm);

        msgHandler = AlertHandler.findAlert(this);

        this.initAccessory();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);




    }

    private void initScreenScale(WindowManager  wm){
        Constant.SCREEN_WIDTH = wm.getDefaultDisplay().getWidth() + ImageTools.getNavigationBarHeight(this);
        Constant.SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight() ;
        Constant.MIN_ACTIVE_UPON = Constant.SCREEN_HEIGHT/5;
        Constant.G = ImageTools.formulateThrowLineG(0.7f);
        Constant.SCREEN_RATION = Constant.SCREEN_HEIGHT_SP/Constant.SCREEN_HEIGHT;
        Constant.MOVE_X_OFFSET_MAX_L = -(int)(300 * Constant.SCREEN_HEIGHT / Constant.SCREEN_HEIGHT_SP);
        Constant.MOVE_X_OFFSET_MAX_R = (int)(50 * Constant.SCREEN_HEIGHT / Constant.SCREEN_HEIGHT_SP);
    }

    private void initAccessory(){
        seekBar = (SeekBar) findViewById(R.id.seekBar_power);
        powerShow = (TextView) findViewById(R.id.power_indicate);
        enemyWav = (TextView) findViewById(R.id.tv_enemy_still);

        this.ibPause = (ImageButton) findViewById(R.id.imageButton_pause);
        this.ibPicShot = (ImageButton) findViewById(R.id.imageButton_screenshot);

        skillBtn = new LaunchButton[]{
                (LaunchButton) findViewById(R.id.btn_skill1),
                (LaunchButton) findViewById(R.id.btn_skill2),
                (LaunchButton) findViewById(R.id.btn_skill3),
                (LaunchButton) findViewById(R.id.btn_skill4)
        };

        gameView = (MySurfaceView) findViewById(R.id.mysurface_view);

        //init launch ai
        autoLaunch = new AutoLaunch(gameView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress / 10 < 1) {
                    Itower.I_POWER = 1;
                } else {
                    Itower.I_POWER = progress / 10;
                }

                powerShow.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(50);

        skillBtn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillBtn[0].isClickable()) {
                    gameView.mainScene.fireArrows(Itower.I_POWER);
                    skillBtn[0].setInReady(false);
                    gameView.mainScene.sendLastLaunchTS(skillBtn[0].getTag() + "001", System.currentTimeMillis());
                }
            }
        });
        skillBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillBtn[1].isClickable()) {
                    gameView.mainScene.addOilAtt(Itower.I_POWER);
                    skillBtn[1].setInReady(false);
                    gameView.mainScene.sendLastLaunchTS(skillBtn[1].getTag() + "001", System.currentTimeMillis());
                }
            }
        });

        skillBtn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillBtn[2].isClickable()) {
                    gameView.mainScene.launchFireCrow(Itower.I_POWER);
                    skillBtn[2].setInReady(false);
                    gameView.mainScene.sendLastLaunchTS(skillBtn[2].getTag() + "001", System.currentTimeMillis());
                }
            }
        });

        skillBtn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillBtn[3].isClickable()) {
                    gameView.mainScene.launchDragons(Itower.I_POWER);
                    skillBtn[3].setInReady(false);
                    gameView.mainScene.sendLastLaunchTS(skillBtn[3].getTag() + "001", System.currentTimeMillis());
                }
            }
        });

        gameView.getMainScene().setgMainActivity(this);


        ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DrawThread.isLogicWorking) {
                    DrawThread.isLogicWorking = false;
                    ibPause.setImageResource(R.drawable.icon_pause_2);
                    ibPicShot.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),R.string.in_pause,Toast.LENGTH_SHORT).show();
                } else {
                    DrawThread.isLogicWorking = true;
                    ibPause.setImageResource(R.drawable.icon_pause_1);
                    ibPicShot.setVisibility(View.GONE);
                }
                //in cheat
                Constant.CHEAT_TRIGGER ++;
                if (Constant.CHEAT_TRIGGER >= 10){
                    Constant.CHEAT_TRIGGER = 0;
                    Itower.effectHpValue(200);
                }
            }
        });


        this.ibPicShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String scsUrl = gameView.mkScreenshot();
                    if (scsUrl != null && !"".equals(scsUrl)){
                        Toast.makeText(getApplicationContext(),R.string.screenshot_ok,Toast.LENGTH_SHORT).show();
                        //update gallery
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + scsUrl)));
                    }else{
                        Toast.makeText(getApplicationContext(),R.string.screenshot_fail,Toast.LENGTH_SHORT).show();
                    }
                    ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //push button[] and this activity to mainscene
        //gameView.getMainScene().setSkillBtns(skillBtn);
    }


    @Override
    protected void onResume() {
        super.onResume();
        gameView.getMainScene().initSceneWpVisible();

        DrawThread.isLogicWorking = false;
        this.needQuit = false;
        //后面的显示对话框，肯定要暂停
        //还需要这样？
        if (ibPause != null){
            ibPause.setImageResource(R.drawable.icon_pause_1);
            ibPicShot.setVisibility(View.GONE);
        }

        PopWindow popWindow = new PopWindow(this);
        popWindow.popWindow(MainScene.sceneBean.getSceneDescription());
        AlertHandler.findAlertStatic(this);
        Constant.CHEAT_TRIGGER = 0;
        //ibPause.setImageResource(R.drawable.icon_tphoto);
    }

    @Override
    protected void onStop() {
        Log.i("life", "act stop");
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onRestart() {
        Log.i("life", "act restart");



        if (MainScene.findMainScence(null) != null){


            Log.i("life", "in restart,uuid:" + this.getIntent().getStringExtra(Constant.PLAYER_NAME));


            if (getIntent().getStringExtra(Constant.PLAYER_NAME) != null){
                String plUUID = getIntent().getStringExtra(Constant.PLAYER_NAME);
                Log.i("life", "in restart,uuid:" + plUUID);
                if (this.uuidSig.equals(plUUID) ){
                    //传来的uuid和本activity存好的uuid相等，不是第一次进入主游戏
                    MainScene.findMainScence(null).setNeedInitliaze(false);
                }else{
                    this.uuidSig = plUUID;
                    MainScene.findMainScence(null).setNeedInitliaze(true);
                }

            }else{//没有得到技能页穿的uuid，所以是切换出去了，不要更新
                MainScene.findMainScence(null).setNeedInitliaze(false);
            }

        }

        DrawThread.isLogicWorking = true;
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i("life","act pause");
        super.onPause();
        DrawThread.isLogicWorking = false;
    }



    //for debug
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //System.exit(0);
            if (needQuit){
                MainScene.findMainScence(gameView).restoreScene();
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
                finish();
            }else{
                needQuit = true;
                Toast.makeText(getApplicationContext(),R.string.for_quit,Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Handler getFixBtnHandler() {
        return fixBtnHandler;
    }

    public Handler getFixWavesInf() {
        return fixWavesInf;
    }

    public LaunchButton[] getSkillBtn() {
        return skillBtn;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("life",this.gameView+"-----------onConfigurationChanged------------:"+newConfig.hardKeyboardHidden);
/*        if (this.gameView != null){
            this.gameView.surfaceDestroyed(null);
        }*/
        super.onConfigurationChanged(newConfig);
    }
}


/*
*
*
*
*
*
*
lis = new ScreenListener(this);
        lis.begin(new ScreenListener.ScreenStateListener() {

@Override
public void onUserPresent() {
        //Log.e("onUserPresent", "onUserPresent");
        }

@Override
public void onScreenOn() {
        // Log.e("onScreenOn", "onScreenOn");
        }

@Override
public void onScreenOff() {
        Log.e("onScreenOff", "onScreenOff");
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);

        //Log.e("onScreenOff", "onScreenOff");

                int currentVersion = android.os.Build.VERSION.SDK_INT;
                if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    lis = null;
                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    am.killBackgroundProcesses(getPackageName());
                    finish();
                    System.exit(0);
                } else {// android2.1
                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                    am.killBackgroundProcesses(getPackageName());
                }
        }
        });
*
*
*
*
*
* */