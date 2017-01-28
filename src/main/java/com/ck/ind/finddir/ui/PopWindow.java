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
                /* ��ʼ����ͨ�Ի��򡣲�������ʽ */
        selectDialog = new Dialog(activityTarget, R.style.trans_dialog);
        selectDialog.setCancelable(false);
/* ������ͨ�Ի���Ĳ��� */
        selectDialog.setContentView(R.layout.pop_win);
        selectDialog.setCanceledOnTouchOutside(false);
/* +2+ȡ�ò����е��ı��ؼ�������ֵ��Ҫ��ʾ������+2+ */
        TextView textView01 = (TextView) selectDialog
                .findViewById(R.id.TextView01);
        textView01.setText(winContent);

        btnItem1 = (Button) selectDialog.findViewById(R.id.ly1btn1);
        btnItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawThread.isLogicWorking = true;
                selectDialog.dismiss();//���ضԻ���


            }
        });
        Log.i("inf","====winContent:"+winContent+",selectDialog.isShowing()"+selectDialog.isShowing());
        if (selectDialog.isShowing()){
            selectDialog.cancel();
            //selectDialog.
        }
        Log.i("msg","===before show:"+winContent);
        selectDialog.show();//��ʾ�Ի���

    }

}
