package com.ck.ind.finddir.bean.spirt;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;


/**
 * Created by KCSTATION on 2015/8/5.
 */
public interface IEnemy extends IDestoriable {




    //public void go();

    public void onDraw(Canvas canvas,Paint paint);

    public void onLogic();


    public int getX();
    public int getY();
    public int getSize();// ==width
    public int getHeight();

    public IEnemy clone() throws CloneNotSupportedException;

    public void setCurPostion(int x,int y);

    public void attackBuilding();

    public void getDamange(int damagePoint);

    public void fullHp();
}
