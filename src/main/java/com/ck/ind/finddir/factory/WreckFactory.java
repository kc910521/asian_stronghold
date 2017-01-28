package com.ck.ind.finddir.factory;

import android.view.SurfaceView;

import com.ck.ind.finddir.bean.wreck.Crasher_remains;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.bean.wreck.Pikemen_remains;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/12.
 */
public class WreckFactory {

    private SurfaceView surfaceView = null;

    private IRemains iRemains = null;
    private IRemains crasherRemains = null;

    static WreckFactory wreckFactory = null;

    private WreckFactory(SurfaceView surfaceView){
        this.surfaceView = surfaceView;
        randomIndex = new Random();
    }

    private Random randomIndex = null;

    public static WreckFactory initWFactory(SurfaceView surfaceView){
        if (wreckFactory == null){
            wreckFactory = new WreckFactory(surfaceView);

        }
        return wreckFactory;
    }


    public IRemains producePikeWreck(int x,int y){
        if (this.iRemains == null){
            this.iRemains =new Pikemen_remains(surfaceView);
            this.iRemains.setPosition(x,y,
                    0,0,
                    randomIndex.nextInt(this.iRemains.getBitmapsLength()));
            return this.iRemains;
        }else {
            IRemains iRemains1 = null;
            try {
                iRemains1 = this.iRemains.clone();
                iRemains1.setPosition(x,y,
                        0,0,
                        randomIndex.nextInt(iRemains1.getBitmapsLength()));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return  iRemains1;
        }
    }

    public IRemains produceCrasherWreck(int x,int y, int width, int height, int typeIndex){
        if (this.crasherRemains == null){
            this.crasherRemains =new Crasher_remains(surfaceView);
            this.crasherRemains.setPosition(x,y,width,height,typeIndex);
            return this.crasherRemains;
        }else {
            IRemains crasherRemains = null;
            try {
                crasherRemains = this.crasherRemains.clone();
                crasherRemains.setPosition(x,y,width,height,typeIndex);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return  crasherRemains;
        }
    }
}
