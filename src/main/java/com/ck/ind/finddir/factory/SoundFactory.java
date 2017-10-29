package com.ck.ind.finddir.factory;

import android.content.Context;

import com.ck.ind.finddir.enums.SoundEnums;
import com.ck.ind.finddir.sound.OpenSLSoundPool;
import com.ck.ind.finddir.sound.SoundPoolIf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KCSTATION on 2017/10/29.
 */
public class SoundFactory {

    private static SoundPoolIf currentPool;

    private static SoundFactory soundFactory;

    private static Context context;

    private static Map<String,Integer> sdSourceMap = new HashMap<>();

    static {
        currentPool = new OpenSLSoundPool(OpenSLSoundPool.MAX_STREAMS,
                OpenSLSoundPool.RATE_44_1,
                OpenSLSoundPool.FORMAT_16, 1);
        loadSource();
    }

    private static void loadSource(){
        for (SoundEnums se : SoundEnums.values()){
            sdSourceMap.put(se.toString(), currentPool.load(context, se.getSourceId()));
        }
    }

    public synchronized static SoundFactory init(Context context){
        if (soundFactory == null){
            soundFactory = new SoundFactory(context);
        }

        return soundFactory;
    }

    private SoundFactory(Context context){
        this.context = context;
    }

    public static final void createOneSounds(SoundEnums... ses){
        currentPool.play(sdSourceMap.get(ses[0].toString()), 1);
    }

}
