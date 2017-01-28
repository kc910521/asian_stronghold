package com.ck.ind.finddir.thread;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.ck.ind.finddir.ui.PopWindow;

/**
 * Created by KCSTATION on 2015/11/30.
 */
public class AlertHandler extends Handler {

    private Activity activity = null;

    private static AlertHandler alertHandler = null;

    private PopWindow popWindow = null;

    private AlertHandler(Activity _activity){
        if (_activity == null){
            try {
                throw new Exception("CK ALERTHANDLER.CLASS : _ACTIVITY IS NULL");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.activity = _activity;
        this.popWindow = new PopWindow(_activity);
    }

    public static AlertHandler findAlert(Activity nowActivity){
        if (AlertHandler.alertHandler == null){
            AlertHandler.alertHandler = new AlertHandler(nowActivity);
        }
        return AlertHandler.alertHandler;
    }

    public static AlertHandler findAlertStatic(Activity nowActivity){
        if (nowActivity == null){
            throw new NullPointerException("nowActivity sent is null");
        }else{
            AlertHandler.alertHandler = new AlertHandler(nowActivity);
        }
        return AlertHandler.alertHandler;
    }


    @Override
    public void handleMessage(Message msg) {

        String astr = msg.obj.toString();
        this.popWindow.popWindow(astr);
        super.handleMessage(msg);
    }


}
