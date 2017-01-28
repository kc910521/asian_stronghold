package com.ck.ind.finddir.play;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.ck.ind.finddir.scene.MainScene;

/**
 * Created by KCSTATION on 2015/11/25.
 */
public interface ISurfaceView {

    public void doFastLogic();

    public void doNormalLogic();

    public void doSlowestLogic();

    public void doBackgroundDraw(Canvas canvas);

    public void doDraw(Canvas canvas);

    public SurfaceHolder getHolder();

    public IMainScene getMainScene();
}
