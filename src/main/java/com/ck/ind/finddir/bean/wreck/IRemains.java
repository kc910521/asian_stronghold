package com.ck.ind.finddir.bean.wreck;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by KCSTATION on 2015/8/12.
 */
public interface IRemains {
    public static List<IRemains> remainList = new CopyOnWriteArrayList<IRemains>();

    public void onDraw(Canvas canvas,Paint paint);

    public void setPosition(int x, int y, int width, int height, int reType);

    public IRemains clone() throws CloneNotSupportedException;

    public int getBitmapsLength();

}
