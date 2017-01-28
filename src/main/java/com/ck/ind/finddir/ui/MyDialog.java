package com.ck.ind.finddir.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.ck.ind.finddir.R;

/**
 * Created by KCSTATION on 2015/10/10.
 */
public class MyDialog extends Dialog {

    private Window window = null;

    public MyDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        //set window attr
        window = this.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);

//        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
//        wl.x = x; //x小于0左移，大于0右移
//        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
//            wl.gravity = Gravity.BOTTOM; //设置重力
//        window.setAttributes(wl);
    }


}
