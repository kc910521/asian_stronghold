package com.ck.ind.finddir.bean.tower;

/**
 * Created by KCSTATION on 2015/11/26.
 */
public class BulletBean {

    private String wpId ;

    private Long lastShootTS = 0l;

    private Long wpShootInterval ;

    public BulletBean(String _wpid,Long _shootInterval){
        this.wpId = _wpid;
        this.wpShootInterval = _shootInterval;
    }

    public String getWpId() {
        return wpId;
    }

    public void setWpId(String wpId) {
        this.wpId = wpId;
    }

    public Long getLastShootTS() {
        return lastShootTS;
    }

    public void setLastShootTS(Long lastShootTS) {
        this.lastShootTS = lastShootTS;
    }

    public Long getWpShootInterval() {
        return wpShootInterval;
    }

    public void setWpShootInterval(Long wpShootInterval) {
        this.wpShootInterval = wpShootInterval;
    }
}
