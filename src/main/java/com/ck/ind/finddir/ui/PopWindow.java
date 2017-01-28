package com.ck.ind.finddir.ui;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.thread.DrawThread;

/**
 * Created by KCSTATION on 2015/8/11.
 */
public class PopWindow {


    Button btnItem1;
    Dialog selectDialog;
    Activity activityTarget = null;

    public PopWindow(Activity activityTarget) {
        this.activityTarget = activityTarget;

    }

    public void popWindow(String winContent){
                /* 初始化普通对话框。并设置样式 */
        selectDialog = new Dialog(activityTarget, R.style.trans_dialog);
        selectDialog.setCancelable(false);
/* 设置普通对话框的布局 */
        selectDialog.setContentView(R.layout.pop_win);
        selectDialog.setCanceledOnTouchOutside(false);
/* +2+取得布局中的文本控件，并赋值需要显示的内容+2+ */
        TextView textView01 = (TextView) selectDialog
                .findViewById(R.id.TextView01);
        textView01.setText(winContent);

        btnItem1 = (Button) selectDialog.findViewById(R.id.ly1btn1);
        btnItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawThread.isLogicWorking = true;
                selectDialog.dismiss();//隐藏对话框


            }
        });
        Log.i("inf","====winContent:"+winContent+",selectDialog.isShowing()"+selectDialog.isShowing());
        if (selectDialog.isShowing()){
            selectDialog.cancel();
            //selectDialog.
        }
        Log.i("msg","===before show:"+winContent);
        selectDialog.show();//显示对话框

    }

}
