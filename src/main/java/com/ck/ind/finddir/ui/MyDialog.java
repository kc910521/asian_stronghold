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
        //����x��y�������ô�����Ҫ��ʾ��λ��
//        wl.x = x; //xС��0���ƣ�����0����
//        wl.y = y; //yС��0���ƣ�����0����
//            wl.alpha = 0.6f; //����͸����
//            wl.gravity = Gravity.BOTTOM; //��������
//        window.setAttributes(wl);
    }


}
