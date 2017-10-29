package com.ck.ind.finddir.enums;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;

/**
 * Created by KCSTATION on 2017/10/29.
 */
public enum SoundEnums {

    FIRE_BALLISTA(R.raw.ballista_fire_03),

    DEAD_MAN(R.raw.soldier_land_grunt_01)

    ;

    private int sourceId;

    SoundEnums(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourceId() {
        return sourceId;
    }

    @Override
    public String toString() {
        return Constant.SOUND_ENUMS_PREFIX + sourceId;
    }
}
