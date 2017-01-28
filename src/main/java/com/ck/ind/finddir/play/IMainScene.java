package com.ck.ind.finddir.play;

import com.ck.ind.finddir.bean.object.IObjectScene;
import com.ck.ind.finddir.bean.scene.AbsSceneBean;
import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.Itower;

import java.util.List;

/**
 * Created by KCSTATION on 2015/12/1.
 */
public interface IMainScene {

    public List<IEnemy> getEnemyList();

    public List<IObjectScene> getObjSenceList();

    public Itower getTower();

    public void fixIsLaunchReady();

    public void restoreScene();

}
