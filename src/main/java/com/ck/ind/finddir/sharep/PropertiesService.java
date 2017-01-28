package com.ck.ind.finddir.sharep;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KCSTATION on 2016/1/18.
 */
public class PropertiesService {

    private Context context = null;

    public PropertiesService(Context _context){
        this.context = _context;
    }


    private SharedPreferences initSharePreferences(){
        return this.context.getSharedPreferences("prop", Activity.MODE_PRIVATE);
    }

    public void saveBy(String keyVal,String value){
        SharedPreferences.Editor editorSp =  initSharePreferences().edit();
        editorSp.putString(keyVal,value);
        editorSp.commit();
    }

    /**
     * not exist return "null"
     * @param key
     * @return
     */
    public String getInfBy(String key){
        return initSharePreferences().getString(key,"null");
    }



}
