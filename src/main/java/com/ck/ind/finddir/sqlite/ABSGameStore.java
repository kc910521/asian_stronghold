package com.ck.ind.finddir.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ck.ind.finddir.Constant;

import java.util.List;
import java.util.Map;

/**
 * Created by KCSTATION on 2015/9/14.
 */
public abstract class ABSGameStore {

    protected static GameStore gameStore = null;
    protected  GameStoreBase gameStoreBase = null;



    protected void vertifyInitialize(){
        //判断是否第一次进入
        Cursor cursor = this.simpleFindBySql(GameStoreBase.TABLE_NAME.db_tower_wp + "", new String[]{"wid"}, null, null,null);
        //之前未插入过武器表内容
        if(cursor.getCount()<=1){
            this.firstInGame();
        }
        cursor.close();
    }

    protected void firstInGame(){
        ContentValues cv = new ContentValues();
        cv.put("wid", Constant.ARROWS_ID);
        cv.put("wp_name","arr001");
        cv.put("wp_interval", 1000);
        //cv.put("is_enable", "1");要在第一关，设为1
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.CATAPULT_ID);
        cv.put("wp_name", "cat001");//wp_interval
        cv.put("wp_interval", 1500);
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.OIL_ID);
        cv.put("wp_name", "oil001");
        cv.put("wp_interval", 5500);
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.FLOURISH_ID);
        cv.put("wp_name", "flo001");
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.FIRE_CROW_ID);
        cv.put("wp_name", "fcrow001");
        cv.put("wp_interval", 3500);
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.ROCKET_ID);
        cv.put("wp_name", "roc001");
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.MANAGER_ID);
        cv.put("wp_name", "manager001");
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.AUTO_DEF_ID);
        cv.put("wp_name", "adef001");
        cv.put("wp_interval", 800);
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        cv.put("wid", Constant.DRAGON_ID);
        cv.put("wp_name", "dragon001");
        cv.put("wp_interval", 1800);
        this.simpleInsert(GameStoreBase.TABLE_NAME.db_tower_wp + "", cv);
        //---------stage save initial
        //cv.put("player_name", Constant.PLAYER_NAME);
        //cv.put("_stage", 0);
        //this.simpleInsert(GameStoreBase.TABLE_NAME.db_record + "", cv);
    }

    /**
     *
     * @param cursor
     * @return
     */
    protected List<Map<String,Object>> convertToMap(Cursor cursor){

        return null;
    }


    //-------------------------------------------CRUD------------------------------------------------

    /**
     * load data to cursor
     * @param tableName tablename
     * @param columnsQuery columns u needed if null then load all columns
     * @param condition sql where
     * @param values where value
     * @return
     */
    protected Cursor simpleFindBySql(String tableName,String[] columnsQuery,String condition,String[] values,String orderby){
        SQLiteDatabase sdb = gameStoreBase.getReadableDatabase();
        Cursor c = null;
        c = sdb.query(tableName,columnsQuery,condition,values,null,null,orderby);

        //sdb.close();warn
        return c;
    }

    protected Integer simpleInsert(String tableName,ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = gameStoreBase.getWritableDatabase();
        Long retVal = sqLiteDatabase.insert(tableName, null, contentValues);
        //sqLiteDatabase.close();
        contentValues.clear();
        return retVal.intValue();
    }

    protected Integer simpleDelete(String tableName,String ParamsClu,String[] valueArrs){
        SQLiteDatabase sqLiteDatabase = gameStoreBase.getWritableDatabase();
        int retVal = sqLiteDatabase.delete(tableName, ParamsClu, valueArrs);
        return retVal;
    }

    protected Integer simpleUpdate(String tableName,ContentValues contentValues,String condition,String[] values){
        SQLiteDatabase sqLiteDatabase = gameStoreBase.getWritableDatabase();
        Integer retVal = sqLiteDatabase.update(tableName,contentValues,condition,values);
        Log.i("db","update return:"+retVal);
        return retVal;
    }
}
