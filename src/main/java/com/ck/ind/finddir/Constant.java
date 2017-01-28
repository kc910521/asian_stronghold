package com.ck.ind.finddir;

/**
 * Created by KCSTATION on 2015/8/3.
 */
public class Constant {

    public static String  SCREENSHOT_DIR = "as_screenshots";
    //this device's absolution
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;

    //debug device htc 528
    public static float SCREEN_WIDTH_SP = 800;
    public static float SCREEN_HEIGHT_SP = 480;


    public static float G = 0.8f;

    //调试屏高占当前屏高百分比
    public static float SCREEN_RATION = 0f;//1.0 default

    public static int MOVE_X_OFFSET = 0;
    public static int MOVE_X_OFFSET_MAX_L = -300;
    public static int MOVE_X_OFFSET_MAX_R = 50;

    //active screen padding-up
    public static int MIN_ACTIVE_UPON = 70;
    //active screen padding-down
    public static int MIN_ACTIVE_DOWN = 70;
    //public static int MOVE_Y_OFFSET = 0;
    public static int ENDLESS_COUNT = 0;

    public static String IS_VICTORY_MODE = "victoryMode";
    //胜利的巡游模式数
    public static String GOS_IN_TOUR = "gosInTour";

    //========================weapon skill id in db================
    public static String ARROWS_ID = "ARR001";
    public static String CATAPULT_ID = "CAT001";
    public static String OIL_ID = "OIL001";//boomber
    public static String ROCKET_ID = "ROC001";
    public static String FIRE_CROW_ID = "FCROW001";

    //---skill without weapon
    public static String AUTO_DEF_ID = "ADEF001";
    public static String MANAGER_ID = "MANAGER001";
    public static String FLOURISH_ID = "FLOUR001";
    public static String DRAGON_ID = "DRAGON001";
    //useless enemy skill
    public static String DRAGON_RAIN_ID = "DRAIN001";

    //============================ player inf
    public static String PLAYER_NAME = "player";

    public static int CHEAT_TRIGGER = 0;
}
