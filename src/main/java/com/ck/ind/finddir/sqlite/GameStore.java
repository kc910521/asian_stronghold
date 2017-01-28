package com.ck.ind.finddir.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.tower.SkillPojo;
import com.ck.ind.finddir.factory.ObjectFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * this is a db interface used by outer
 * Created by KCSTATION on 2015/9/13.
 */
public class GameStore extends ABSGameStore{



    private GameStore(Context context){
        gameStoreBase = new GameStoreBase(context,null);
        this.vertifyInitialize();
    }

    public static GameStore findGameStore(Context context){
        if (GameStore.gameStore == null && context != null){
            GameStore.gameStore = new GameStore(context);
        }
        return GameStore.gameStore;
    }


    //-------------------------------agent logic-------
    //--------------1.tower----------------
    //

    /**
     * find all available weapon
     *
     * @return
     */
    private Cursor findWpCursor(){
        return super.simpleFindBySql(GameStoreBase.TABLE_NAME.db_tower_wp + "", null,
                "is_enable=? AND wp_stage IN (SELECT MAX(tb2.wp_stage) FROM "+GameStoreBase.TABLE_NAME.db_tower_wp+" as tb2 ) "
                , new String[]{"1"},null);
    }

    /**
     * set wid=tovalue in db wid=wpid
     * @param wpid
     * @param toValue
     */
    @Deprecated
    public void updateWpTo(String wpid,int toValue){
        ContentValues contentValues = new ContentValues();
        contentValues.put("is_enable", toValue);
        if(wpid == null){
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_tower_wp+"",contentValues,null,null);
        }else{
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_tower_wp+"",contentValues,"wid=?",new String[]{wpid});
        }

    }

    /**
     * set weapon level to db
     * @param wpid
     * @param level
     */
    @Deprecated
    public void updateWpLevelTo(String wpid,final int level){
        ContentValues contentValues = new ContentValues();
        contentValues.put("wp_level", level);
        if(wpid == null){
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_tower_wp+"",contentValues,null,null);
        }else{
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_tower_wp+"",contentValues,"wid=?",new String[]{wpid});
        }
    }

    public List<SkillPojo> findAvailableWeapon(){
        Cursor cursor = this.findWpCursor();
        List<SkillPojo> wpList = new LinkedList<>();
        SkillPojo skillPojo = null;
        while (cursor.moveToNext()){
            skillPojo = new SkillPojo();
            skillPojo.setIsEnable(1);
            skillPojo.setWid(cursor.getString(cursor.getColumnIndex("wid")));
            skillPojo.setWpInterval(cursor.getInt(cursor.getColumnIndex("wp_interval")));
            skillPojo.setWpLevel(cursor.getInt(cursor.getColumnIndex("wp_level")));
            skillPojo.setWpStage(cursor.getInt(cursor.getColumnIndex("wp_stage")));
/*            wpInfo = new LinkedHashMap<>();
            wpInfo.put("id", cursor.getString(cursor.getColumnIndex("wid")));
            wpInfo.put("level", cursor.getString(cursor.getColumnIndex("wp_level")));
            wpInfo.put("interval", cursor.getString(cursor.getColumnIndex("wp_interval")));*/
            wpList.add(skillPojo);
        }
        return wpList;
    }

    /**
     * single insert
     */
    public void insertWpWithLevel(SkillPojo skillPojo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("wid", skillPojo.getWid());
        contentValues.put("wp_level", skillPojo.getWpLevel());
        if (skillPojo.getWpInterval() <100){//default val is 10
            contentValues.put("wp_interval",this.getWpIntervalByModel(skillPojo.getWid()));
        }else{
            contentValues.put("wp_interval",skillPojo.getWpInterval());
        }


        contentValues.put("is_enable",1);
        contentValues.put("wp_stage", skillPojo.getWpStage());



        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", contentValues);
/*        if(wpid == null){
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_tower_wp+"",contentValues,null,null);
        }else{
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_tower_wp+"",contentValues,"wid=?",new String[]{wpid});
        }*/
    }

    private int getWpIntervalByModel(String wpid){
        Cursor cursor = super.simpleFindBySql(GameStoreBase.TABLE_NAME.db_tower_wp + "", null,
                "wp_stage = 0 AND wid = ? "
                , new String[]{wpid}, null);
        int skInterval = 10;
        while (cursor.moveToNext()){
            skInterval = cursor.getInt(cursor.getColumnIndex("wp_interval"));
            return skInterval;
        }
        return skInterval;
    }
    /**
     * multi insert
     * 将读取的技能list重新组装入数据库
     * @param wpList standard loaded by findAvailableWeapon()
     * @param stageLevel now_stage+1
     */
    public void resembleWeaponToDb(List<SkillPojo> wpList,int stageLevel){
        Iterator<SkillPojo> iterator = wpList.iterator();
        ContentValues contentValues = new ContentValues();
        while (iterator.hasNext()){
            SkillPojo thisSkill = iterator.next();
            contentValues.put("wid",thisSkill.getWid());
            contentValues.put("wp_level",thisSkill.getWpLevel());
            contentValues.put("wp_interval",thisSkill.getWpInterval());
            contentValues.put("is_enable",1);
            contentValues.put("wp_stage", stageLevel);
            this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", contentValues);

            /*
            *
            *             contentValues.put("wid",wpMap.get("id")+"");
            contentValues.put("wp_level",Integer.valueOf(wpMap.get("level")+""));
            contentValues.put("wp_interval",Integer.valueOf(wpMap.get("interval")+""));
            contentValues.put("is_enable",1);
            contentValues.put("wp_stage", (stageLevel) + "");
            this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", contentValues);
            * */
            //contentValues.clear();
        }
    }

    public void clearDeprecatedSkill(int nowStage){
        super.simpleDelete(GameStoreBase.TABLE_NAME.db_tower_wp + "", "wp_stage>?", new String[]{nowStage + ""});
    }

    public void restoreWpDb(){
/*        this.updateWpLevelTo(null, 1);//all wp level to 1
        this.updateWpTo(null, 0);//all wp not in use*/
        this.clearDeprecatedSkill(0);
    }


    //================stage save/load=====

    public Integer insertStage(String playerName ,int stageNumber, int Thp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");


        ContentValues contentValues = new ContentValues();
        contentValues.put("_stage", stageNumber);
        contentValues.put("tower_hp", Thp);
        contentValues.put("player_name", playerName);
        contentValues.put("_tm", simpleDateFormat.format(new Date()));
        return super.simpleInsert(GameStoreBase.TABLE_NAME.db_record + "", contentValues);
        //this.clearDeprecatedStage(stageNumber);
    }

    public void clearDeprecatedStage(int nowStage){
        super.simpleDelete(GameStoreBase.TABLE_NAME.db_record + "", "_stage>?", new String[]{nowStage + ""});
    }

    @Deprecated
    public void updateStage(String playerName ,int stageNumber, int Thp){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_stage", stageNumber);
        contentValues.put("tower_hp", Thp);
        if(playerName == null || "".equals(playerName)){
            contentValues.put("player_name",playerName);
            super.simpleInsert(GameStoreBase.TABLE_NAME.db_record + "", contentValues);
        }else{
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_record+"",contentValues,"player_name=?",new String[]{playerName});
        }
    }

    /**
     *
     * @param playerName
     * @param Thp
     * @param overStage 已经完成的挑战模式
     */
    public void updateChallengeStage(String playerName , int Thp,int overStage){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_stage", 99);
        contentValues.put("tower_hp", Thp);
        contentValues.put("seq_stage", overStage);
        if(playerName == null || "".equals(playerName)){
            contentValues.put("player_name",playerName);
            super.simpleInsert(GameStoreBase.TABLE_NAME.db_record + "", contentValues);
        }else{
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_record+"",contentValues,"player_name=?",new String[]{playerName});
        }
    }

    /**
     *  if stage return 99 ,mean endless model
     *  and seq_stage begins work
     * @param playerName
     * @return
     */
    public List<Map<String,Object>> loadStageInf(String playerName){
        Cursor cursor = super.simpleFindBySql(GameStoreBase.TABLE_NAME.db_record + "", null, "player_name=? AND _stage>0", new String[]{playerName},"_stage desc");

        Map<String,Object> resMap = null;
        List<Map<String,Object>> stageList = new LinkedList<Map<String,Object>>();
        while (cursor.moveToNext()){
            resMap = new HashMap<>();
            resMap.put("name",cursor.getInt(cursor.getColumnIndex("player_name")));
            int stageNumber = cursor.getInt(cursor.getColumnIndex("_stage"));
            if (stageNumber >= 99){
                resMap.put("stage","CHALLENGE!");
            }else{
                resMap.put("stage",stageNumber);
            }

            resMap.put("hp",cursor.getInt(cursor.getColumnIndex("tower_hp")));
            resMap.put("seq",cursor.getInt(cursor.getColumnIndex("seq_stage")));
            resMap.put("tm",cursor.getString(cursor.getColumnIndex("_tm")));
            stageList.add(resMap);
        }
        return stageList;
    }

    public void resetStage(String playerName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_stage", 0);
        contentValues.put("tower_hp", Itower.BASE_HP);
        if (playerName != null){
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_record+"",contentValues,"player_name=?",new String[]{playerName});
        }else{
            //reset all user
            super.simpleUpdate(GameStoreBase.TABLE_NAME.db_record+"",contentValues,null,null);
        }
    }

    public void insertStageToChallengeModel(String playerName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_stage", 99);
        contentValues.put("tower_hp", Itower.BASE_HP);
        contentValues.put("seq_stage",0);
        if (playerName != null){
            contentValues.put("player_name",playerName);
        }else{
            //reset all user
            contentValues.put("player_name", Constant.PLAYER_NAME);
        }
        contentValues.put("_tm","BEF");

        super.simpleInsert(GameStoreBase.TABLE_NAME.db_record+"",contentValues);
    }



}
