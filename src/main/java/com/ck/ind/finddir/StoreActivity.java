package com.ck.ind.finddir;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.ck.ind.finddir.bean.tower.SkillPojo;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.factory.SceneFactory;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.sqlite.GameStore;
import com.ck.ind.finddir.ui.PopWindowSel;
import com.ck.ind.finddir.ui.SkillButton;

import java.util.List;
import java.util.Map;


public class StoreActivity extends Activity {

    SkillButton[] btnSkills = null;
    GameStore gameStore = null;
    PopWindowSel popWindowSel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initBtnOfStore();

    }

    public void initBtnOfStore(){
        btnSkills = new SkillButton[]{
                (SkillButton) findViewById(R.id.skbtn_arr),
                (SkillButton) findViewById(R.id.skbtn_flour),
                (SkillButton) findViewById(R.id.skbtn_dragon),
                (SkillButton) findViewById(R.id.skbtn_cat),
                (SkillButton) findViewById(R.id.skbtn_manager),
                (SkillButton) findViewById(R.id.skbtn_oil),
                (SkillButton) findViewById(R.id.skbtn_fcrow),
                (SkillButton) findViewById(R.id.skbtn_emp)
        };
/*        SkillButton dgBtn = (SkillButton) findViewById(R.id.skbtn_dragon);
        dgBtn.initFunc();
        dgBtn.setTextSize(14.0f);*/

        popWindowSel = new PopWindowSel(this);

        //get weapons list
        gameStore = GameStore.findGameStore(this);
        List<SkillPojo> wpList = gameStore.findAvailableWeapon();
        Log.i("cx", "=============sstore activity gameStore.findAvailableWeapon()=========================");
        Log.i("db", "in store:" + wpList.toString());
        //default button view
        for (SkillButton btnThis : btnSkills){
            if (btnThis!=null){
                boolean isInUsed = false;
                for (SkillPojo skillPojo : wpList){
                    //skill not get
                    if (skillPojo.getWid().equals(btnThis.getTag() + "001")){//
                        btnThis.initFunc();
                        btnThis.setClickable(true);
                        btnThis.setImgTransparent(255);
                        btnThis.setLevelNums(skillPojo.getWpLevel()+"");
                        Log.i("db", "find id" + btnThis.getId());
                        btnThis.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popWindowSel.popSkillWindow(getString(R.string.u_have) + v.getContentDescription() + getString(R.string.u_upgrade), v.getTag() + "001").show();
                                //Toast.makeText(getApplicationContext(),"chosed", Toast.LENGTH_LONG).show();
                            }
                        });
                        isInUsed = true;
                        break;
                    }
                }
                if (!isInUsed){
                    btnThis.initFunc();
                    btnThis.setClickable(true);
                    btnThis.setImgTransparent(90);// getBackground().setAlpha(10);
                    btnThis.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popWindowSel.popSkillWindow(getString(R.string.u_have_n)+v.getContentDescription()+ getString(R.string.u_get_it),v.getTag() + "001").show();
                        }
                    });
                    Log.i("db", "not find id" + btnThis.getId());
                }
            }


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //for debug
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) { //¼à¿Ø/À¹½Ø/ÆÁ±Î·µ»Ø¼ü
            Toast.makeText(this,R.string.not_quit,Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }

    }

}
