package com.ck.ind.finddir.bean.object;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by KCSTATION on 2015/8/17.
 */
public interface IObjectScene extends Cloneable {

    public void onDraw(Canvas canvas,Paint paint);

    public void onLogic();

    public void setPosition(float x, float y);

    public float getX();

    public float getY();

    public IObjectScene clone() throws CloneNotSupportedException;
}
