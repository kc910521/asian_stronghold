package com.ck.ind.finddir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.ind.finddir.play.IMainScene;
import com.ck.ind.finddir.play.MySurfacePlayView;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sharep.PropertiesService;
import com.ck.ind.finddir.sound.JavaSoundPool;
import com.ck.ind.finddir.sound.OpenSLSoundPool;
import com.ck.ind.finddir.sound.SoundPoolIf;
import com.ck.ind.finddir.sqlite.GameStore;
import com.ck.ind.finddir.thread.DrawThread;
import com.ck.ind.finddir.toolkits.ImageTools;
import com.ck.ind.finddir.ui.PopStageStore;
import com.ck.ind.finddir.ui.PopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class StartActivity extends Activity {

    private MySurfacePlayView menuView = null;
    private Button btnStart = null;
    private Button btnAbout = null;
    private Button btnQuit = null;
    private Button btnNewGm = null;
    private Button btnChallenge = null;
    private TextView tvStageNum = null;
    private PropertiesService pps = null;
    private List<Map<String,Object>> resListDt = null;


    private boolean isChallengeConfirm = false;

    private IMainScene mainSceneMenu = null;

    //private View mainView = null;

    //sound
    private static String TAG = "OpenSLSoundPool";

    private SoundPoolIf currentPool;

    private int[] currentSounds;

    private boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //hide system ui
/*        this.windowThis = getWindow();
        WindowManager.LayoutParams params = windowThis.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        this.windowThis.setAttributes(params);*/
/*        this.mainView = getLayoutInflater().from(this).inflate(R.layout.activity_start,
                null);*/
        //this.mainView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        setContentView(R.layout.activity_start);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager wm = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

/*        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        this.getWindow().setAttributes(params);*/

        Constant.SCREEN_WIDTH = wm.getDefaultDisplay().getWidth() + ImageTools.getNavigationBarHeight(this);
        Constant.SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight() ;


        Constant.MIN_ACTIVE_UPON = Constant.SCREEN_HEIGHT/6;
        Constant.G = ImageTools.formulateThrowLineG(0.7f);
        Constant.SCREEN_RATION = Constant.SCREEN_HEIGHT_SP/Constant.SCREEN_HEIGHT;

        Log.i("msg","screen ImageTools.getNavigationBarHeight(this):"+ImageTools.getNavigationBarHeight(this));

        this.initAccessory();

        //

        //initialize soundpool
        // use Android SoundPool (via a wrapper) by default
        currentPool = new OpenSLSoundPool(OpenSLSoundPool.MAX_STREAMS, OpenSLSoundPool.RATE_44_1, OpenSLSoundPool.FORMAT_16, 1);
//        currentPool = new JavaSoundPool(OpenSLSoundPool.MAX_STREAMS);

        currentSounds = loadSounds(currentPool);
    }

    private int[] loadSounds(SoundPoolIf sp){
        int ids[] = new int[6];
		ids[0] = sp.load(this, R.raw.footstep);
//		ids[1] = sp.load(this, R.raw.die2);
//		ids[2] = sp.load(this, R.raw.die3);
//		ids[3] = sp.load(this, R.raw.die_soft);
//		ids[4] = sp.load(this, R.raw.die_soft1);
//		ids[5] = sp.load(this, R.raw.die_soft2);

        return ids;
    }

    public void playRandomSound(){
        currentPool.play(currentSounds[0], 1);
    }


 /*   private void createPlayerThread(){
        Runnable player = new Runnable() {

            @Override
            public void run() {
                boolean running = true;
                while(running){
                    if (playing){
                        synchronized(StartActivity.this){
                            playRandomSound();
                        }
                    }
                    // Sleep between 0 and MAX_PLAYER_GAP ms between
                    // playing samples
                    try {
                        Thread.sleep((int)(Math.random() * MAX_PLAYER_GAP));
                    } catch (InterruptedException e) {
                        running = false;
                    }
                }
            }
        };

        Thread t = new Thread(player);
        t.start();
    }*/


    /**
     * 是否已经通关
     * @return
     */
    private boolean isVictoryGame(){
        String vc = pps.getInfBy(Constant.IS_VICTORY_MODE);
        if ("true".equals(vc) ){
            return true;
        }
        return false;
    }

    private String getStageNumber(){
        return pps.getInfBy(Constant.GOS_IN_TOUR);
    }

    @Override
    protected void onResume() {
        this.resListDt = null;
        if (MainScene.findMainScence(null) != null){
            MainScene.findMainScence(null).restoreScene();
        }
        isChallengeConfirm = false;
        DrawThread.isLogicWorking = true;
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DrawThread.isLogicWorking = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DrawThread.isLogicWorking = true;
    }

    private void initAccessory(){

        pps = new PropertiesService(this);
        menuView = (MySurfacePlayView) findViewById(R.id.mysurface_view_start);
        //PlayScene.findMainScence(menuView);
        mainSceneMenu = menuView.getMainScene();
        btnStart = (Button) findViewById(R.id.btn_continuegame);
        btnAbout = (Button) findViewById(R.id.btn_about);
        btnQuit = (Button) findViewById(R.id.btn_quitgame);
        btnNewGm = (Button) findViewById(R.id.btn_newgame);
        btnChallenge = (Button) findViewById(R.id.btn_challenge);
        tvStageNum = (TextView)findViewById(R.id.gos_number);


        //////-------------


        btnNewGm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGameStart();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToMain();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRandomSound();
                popWin("If you have any question or suggestion \r\n" +
                        "just send all to：\r\n Qmine@outlook.com\r\n" +
                        "KnightNine91@gmail.com\r\n" +
                        "NO SOUND ? next version.");
            }
        });

        btnChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChallengeStart();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        Constant.ENDLESS_COUNT = 0;
        //完成的关卡数
        String gosNb = this.getStageNumber();
        if (!"null".equals(gosNb)){
            tvStageNum.setText(getString(R.string.u_max_tour)+gosNb+"/13");
            tvStageNum.setVisibility(View.VISIBLE);
        }
        if (!this.isVictoryGame()){
            btnChallenge.setVisibility(View.GONE);
        }
    }

    private void newGameStart(){
        GameStore gameStore = null;
        gameStore = GameStore.findGameStore(this);
        gameStore.restoreWpDb();
        gameStore.clearDeprecatedStage(0);
        gameStore.resetStage(Constant.PLAYER_NAME);
        this.getInStage();
    }

    private void newChallengeStart(){
        if(this.isChallengeConfirm){
            GameStore gameStore = null;
            Constant.ENDLESS_COUNT = 0;
            gameStore = GameStore.findGameStore(this);
            gameStore.restoreWpDb();
            gameStore.clearDeprecatedStage(0);
            gameStore.insertStageToChallengeModel(Constant.PLAYER_NAME);


            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            this.startActivity(intent);
            this.finish();
        }else{
            Toast.makeText(this,R.string.new_game_warning,Toast.LENGTH_LONG).show();
            this.isChallengeConfirm = true;
        }

    }

    /**
     * goto main game view
     */
    private void jumpToMain(){

        popAwindow(btnStart);
  /*      mainSceneMenu.restoreScene();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        this.startActivity(intent);
        this.finish();*/
    }

    private void getInStage(){
        mainSceneMenu.restoreScene();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }

    private void popWin(String content){
        PopWindow popWindow = new PopWindow(this);
        popWindow.popWindow(content);
    }

    //pop window

    private PopStageStore window = null;
    private Button goSave,cancelSave = null;
    private ListView saveList = null;
    private SimpleAdapter adapter = null;

    private void popAwindow(View parent) {

        if (window == null) {
            LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = lay.inflate(R.layout.stage_pop_layout, null);

            //v.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_corners_view));
            //初始化按钮
            goSave = (Button) v.findViewById(R.id.btn_go_save);
            //goSave.setOnClickListener(submitListener);
            cancelSave = (Button) v.findViewById(R.id.btn_cancel_save);
            cancelSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (window != null) {
                        window.dismiss();
                    }
                }
            });
            //初始化listview，加载数据。
            saveList = (ListView) v.findViewById(R.id.save_list_view);
            adapter = new SimpleAdapter(StartActivity.this,
                    this.findListData(),
                    R.layout.item_stagepop_layout,
                    new String[]{"stage","hp","tm"},
                    new int[]{R.id.pop_item_stage_number,R.id.pop_item_stage_hp,R.id.pop_item_stage_tm}
                    );

            saveList.setAdapter(adapter);
            saveList.setItemsCanFocus(false);
            saveList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            saveList.setOnItemClickListener(listClickListener);
            window = new PopStageStore(v, ImageTools.positionConvert(390) ,ImageTools.positionConvert(300));
        }
        window.setFocusable(true);
        window.update();
        window.showAtLocation(parent, Gravity.LEFT|Gravity.BOTTOM, ImageTools.positionConvert(10), -ImageTools.positionConvert(20));
    }



    private List<Map<String,Object>> findListData(){

        if (this.resListDt == null || this.resListDt.isEmpty()){
            GameStore gameStore = GameStore.findGameStore(this);
            this.resListDt = gameStore.loadStageInf(Constant.PLAYER_NAME);

        }
        return this.resListDt;

        //List<Map<String, Object>> stageList = gameStore.loadStageInf(Constant.PLAYER_NAME);
       // stageMap.put("tm","--");
        //Map<String,String> strMap = new HashMap<>();

/*        strMap.put("stage",stageMap.get("")+"stage");
        strMap.put("hp","2");
        strMap.put("tm","110010");*/
       // rList.add(stageMap);
    }


    AdapterView.OnItemClickListener listClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Map<String, Object> dtMap = (Map<String, Object>) adapter.getItem(position);
            GameStore gameStore = GameStore.findGameStore(null);
            if (gameStore != null){
                int tStage = Integer.valueOf(dtMap.get("stage") + "");
                gameStore.clearDeprecatedStage(tStage);
                gameStore.clearDeprecatedSkill(tStage);
            }else{
                Toast.makeText(getApplicationContext(),getString(R.string.sys_warn) ,Toast.LENGTH_SHORT).show();
            }
            getInStage();
        }
    };


}
