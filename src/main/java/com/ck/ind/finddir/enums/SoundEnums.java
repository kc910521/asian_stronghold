package com.ck.ind.finddir.enums;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.R;

/**
 * Created by KCSTATION on 2017/10/29.
 */
public enum SoundEnums {


    FIRE_BALLISTA(R.raw.ballista_fire_03),

    DEAD_MAN(R.raw.soldier_land_grunt_01),

    CAT_CRANKS(R.raw.cat_cranks_02a),

    DARGON_OUT_1(R.raw.fireout2),

    CAT_OUT(R.raw.cat_fire_02b),

    FIRE_OUT_2(R.raw.flamearrow_01),
    FIRE_OUT_3(R.raw.flamearrow_02),
    FIRE_OUT_4(R.raw.flamearrow_03),
    FIRE_OUT_5(R.raw.flamearrow_04),
    FIRE_OUT_6(R.raw.flamearrow_05),
    FIRE_OUT_7(R.raw.flamearrow_06),
    FIREBIRD_OUT(R.raw.fireit),
    BOMBER_1(R.raw.bomb_1),
    BOMBER_2(R.raw.bomb_2),
    BEHIT_1(R.raw.rockmiss_07),
    BEHIT_2(R.raw.rockmiss_08),
    BEHIT_3(R.raw.rockmiss_09),

    BOWMAN_OUT_1(R.raw.cbow_02),
    BOWMAN_OUT_2(R.raw.cbow_03),

    TEST(R.raw.hrs_manytrot_attack_01),
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
