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

        // �������ظ�����
        addShortcutIntent.putExtra("duplicate", false);// �����Բ��Ǹ��ݿ�ݷ�ʽ�������ж��ظ���
        // Ӧ���Ǹ��ݿ�����Intent���ж��Ƿ��ظ���,��Intent.EXTRA_SHORTCUT_INTENT�ֶε�value
        // �������Ʋ�ͬʱ����Ȼ�е��ֻ�ϵͳ����ʾToast��ʾ�ظ�����Ȼ�Ὠ������
        // ��Ļ��û�пռ�ʱ����ʾ
        // ע�⣺�ظ���������ΪMIUI�������ֻ��ϲ�̫һ����С�����ƺ������ظ�������ݷ�ʽ

        // ����
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // ͼ��
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(LogoActivity.this,
                        R.mipmap.my_icon));
        // ���ù�������
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.setClass(LogoActivity.this, LogoActivity.class);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        addShortcutIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);
        // ���͹㲥
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
