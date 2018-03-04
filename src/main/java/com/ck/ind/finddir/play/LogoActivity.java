package com.ck.ind.finddir.play;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;
import com.ck.ind.finddir.StartActivity;
import com.ck.ind.finddir.sharep.PropertiesService;
import com.ck.ind.finddir.sound.SoundFile;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends Activity {

    private final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SoundFile.init(this);

        if (!this.appIconExist(getApplicationContext())){
            this.addShortcut(getString(R.string.app_name));
        }

        this.initTimerComp();
    }


    private void initTimerComp(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    /**
     * from
     * http://www.cnblogs.com/mengdd/p/3837592.html
     * @param name
     */
    private void addShortcut(String name) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);

        // 不允许重复创建
        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

        // 名字
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(LogoActivity.this,
                        R.mipmap.my_icon));
        // 设置关联程序
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.setClass(LogoActivity.this, LogoActivity.class);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        addShortcutIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        // 发送广播
        sendBroadcast(addShortcutIntent);
    }

    private boolean appIconExist(Context cx){
        PropertiesService pps = new PropertiesService(this);

        if (!"null".equals(pps.getInfBy(Constant.GOS_IN_TOUR))){
            return true;
        }
        return false;
/*        final String uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = cx.getContentResolver().query(CONTENT_URI, null,"title=?", new String[] { cx.getString(R.string.app_name) }, null);
        if (c != null && c.getCount() > 0) {
            return true;
        }
        return false;*/
    }
}
