package com.ck.ind.finddir.ui;

import android.graphics.Paint;

/**
 * Created by KCSTATION on 2015/10/16.
 * paint for clone
 * crash
 * fatal error in process
 */
public class MyPaint extends Paint implements Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
