package com.ck.ind.finddir.bean.tower;

/**
 * Created by KCSTATION on 2016/2/6.
 *
 * base on TABLE_NAME.db_tower_wp
 */
public class SkillPojo {

    private String wid;

    private String wpName;

    private int isEnable = 0;

    private int wpLevel = 1;

    private int wpInterval = 10;

    private int wpStage = 0;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWpName() {
        return wpName;
    }

    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public int getWpLevel() {
        return wpLevel;
    }

    public void setWpLevel(int wpLevel) {
        this.wpLevel = wpLevel;
    }

    public int getWpInterval() {
        return wpInterval;
    }

    public void setWpInterval(int wpInterval) {
        this.wpInterval = wpInterval;
    }

    public int getWpStage() {
        return wpStage;
    }

    public void setWpStage(int wpStage) {
        this.wpStage = wpStage;
    }
}
