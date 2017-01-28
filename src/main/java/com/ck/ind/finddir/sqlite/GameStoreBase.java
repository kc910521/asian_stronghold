package com.ck.ind.finddir.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ck.ind.finddir.bean.tower.Itower;

/**
 * Created by KCSTATION on 2015/8/24.
 */
public class GameStoreBase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "gmdb";
    enum TABLE_NAME{
        db_tower_wp,
        db_record
    }


    public GameStoreBase(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, GameStoreBase.DATABASE_NAME, factory, 1);
    }

    public void insertValues(String tableName,Object ...args){

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建武器表
//技能id和关卡联合主键
        db.execSQL("CREATE TABLE " +
                " IF NOT EXISTS "+TABLE_NAME.db_tower_wp+" " +
                " (" +
                "  " +
                " wid VARCHAR, " +
                " wp_name VARCHAR, " +
                " is_enable SMALLINT DEFAULT 0," +
                " wp_level INTEGER DEFAULT 1," +
                " wp_interval INTEGER DEFAULT 10," +
                " wp_stage INTEGER DEFAULT 0," +
                " PRIMARY KEY (wid,wp_stage) "+
                ")");

        //seq_stage: 仅挑战模式需要
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME.db_record+" ( " +
                " " +//oid INTEGER PRIMARY KEY AUTOINCREMENT,
                " rid INTEGER PRIMARY KEY AUTOINCREMENT ," +
                " player_name VARCHAR," +
                " _stage INTEGER DEFAULT 0, " +
                " tower_hp INTEGER DEFAULT "+ Itower.BASE_HP+"," +
                " seq_stage INTEGER DEFAULT 0," +
                " _tm VARCHAR " +
                " )");
        //scoreTable
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
