package com.ck.ind.finddir.factory;

import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.bean.spirt.IEnemy;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.tower.Arrow;
import com.ck.ind.finddir.bean.tower.AutoDefence;
import com.ck.ind.finddir.bean.tower.Bullet;
import com.ck.ind.finddir.bean.tower.DragonRain;
import com.ck.ind.finddir.bean.tower.DragonRoc;
import com.ck.ind.finddir.bean.tower.FireCrow;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.tower.Oil;
import com.ck.ind.finddir.bean.tower.Rocket;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/23.
 */
public class BulletFactory {
    private static BulletFactory bulletFactory = null;
    private SurfaceView surfaceView = null;
    //private MainScene mainScene = null;

    //for clone
    private AbsBullet arrow = null;
    private AbsBullet rocket = null;
    private AbsBullet fireCrow = null;
    private AbsBullet dragons = null;
    private AbsBullet oilBomb = null;
    private AbsBullet dragonRain = null;
    private AbsBullet autoDef = null;

    private Random randomSeed = null;

    private Itower itower = null;

    private BulletFactory(SurfaceView gamView){
        this.surfaceView = gamView;
        //this.mainScene = MainScene.findMainScence(gamView);
        itower = Itower.initUserTower(gamView);
        this.randomSeed = new Random();
    }


    public static BulletFactory initFactory(SurfaceView gameView){
        if (bulletFactory == null){
            bulletFactory = new BulletFactory(gameView);
        }
        return bulletFactory;
    }

    public synchronized void restoreFactory(){
        bulletFactory = null;
    }

    /**
     * this function to create stone attack
     * is different with others factory function
     * @param shootPower
     * @param itower
     * @return
     */
    public AbsBullet createStoneBullet(int shootPower,Itower itower){
        AbsBullet bullet = new Bullet(surfaceView,
                itower.getCataX()+10,
                itower.getCataY()+10,
                0.5f,
                -5.0f,
                shootPower);
        if ( itower.wpIsReady(bullet.getOid(),true) && itower.getWpMap().containsKey(bullet.getOid())){
            bullet.setWpLevel(Integer.valueOf(itower.getWpMap().get(bullet.getOid()+"")) );
            itower.setShootAfter(true);
        }else{
            bullet = null;
        }

        return bullet;
    }
    //arrows attack

    /**
     *
     * @param shootPower power
     * @param arrowNeeded arrows number needed
     * @param fromEnemy isFrom Enemy ,if not,ex,ey can be null
     * @param ex enemy x
     * @param ey enemy y
     * @return
     * @throws CloneNotSupportedException
     */
    public List<AbsBullet> createArrows(int shootPower,int arrowNeeded,Boolean fromEnemy,Integer ex,Integer ey) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();
        if (arrow == null){
            arrow = new Arrow(surfaceView, 0, 0, 0, 0, shootPower);
        }
        //AbsBullet absBullet = new Arrow(surfaceView, 0, 0, 0, 0, shootPower);
        //dame,code need refine

        if (fromEnemy){
            for (int i = 0; i < arrowNeeded; i++) {
                AbsBullet abc = arrow.clone();
                abc.setPositionAndSpeed(ex,
                        ey+( i * ImageTools.positionConvert(35)) ,
                        ImageTools.positionConvert(14),
                        ImageTools.positionConvert((shootPower * 2)-10));
                abc.setPowerKeyDown(shootPower);
                abc.setFromEnemy(true);
                absBulletList.add(abc);
            }
        }else{
            //判断是否可以射击(超出射击间隔)
            Log.i("db", "find itower.getWpMap():" + itower.getWpMap());
            //:{DRAGON001=1, CAT001=1, OIL001=1, FLOUR001=1, ARR001=1}
            if (itower.getWpMap().containsKey(arrow.getOid())) {
                int nowLevel = Integer.valueOf(itower.getWpMap().get(arrow.getOid()));
                arrow.setWpLevel(nowLevel);
                Log.i("dmg","arrows level is "+nowLevel);
                for (int i = 0; i < (arrowNeeded+nowLevel); i++) {
                    AbsBullet abc = arrow.clone();
                    abc.setWpLevel(nowLevel);
                    abc.setPowerKeyDown(shootPower);
                    abc.setPositionAndSpeed(itower.getX()+(itower.getWidth()/3) + this.randomPositionByRule(60),
                            itower.getY()+(itower.getHeight()>>1)  + this.randomPositionByRule(40+arrowNeeded+nowLevel),
                            ImageTools.positionConvert(14),
                            ImageTools.positionConvert((shootPower * 2)-10+this.randomPositionReg(4)-2 ));
                    absBulletList.add(abc);
                }
            }
        }
        return absBulletList;
    }

    //oil attack
    public List<AbsBullet> createOilAttack(int shootPower,int oilNumNeeded) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();
/*        Random randomX = new Random();
        Random randomY = new Random();*/
        if(oilBomb == null){
            oilBomb = new Oil(surfaceView,
                    itower.getX(),
                    itower.getY(),
                    ImageTools.positionConvert(6),
                    -5.0f,
                    shootPower);//shootPower
        }
        //判断是否可以射击(超出射击间隔),武器不可用
        if (itower.getWpMap().containsKey(oilBomb.getOid())) {
            oilBomb.setWpLevel(Integer.valueOf(itower.getWpMap().get(oilBomb.getOid())));
            AbsBullet abc = null;
            for (int i = 0; i < oilNumNeeded+(oilBomb.getWpLevel()<<1); i++) {
                abc = oilBomb.clone();
                //abc.setPowerKeyDown(shootPower);
                abc.setPositionAndSpeed(
                        itower.getX()+ (itower.getWidth()/5) +  this.randomPositionReg(itower.getWidth() * 4 / 5),
                        itower.getY()+(itower.getHeight()>>2) + this.randomPositionReg(itower.getHeight()/4),
                        ImageTools.positionConvert(6),
                        -5.0f);
                absBulletList.add(abc);
            }
        }
        return absBulletList;
    }

    /**
     * for firecrow submissle
     * @param shootPower
     * @param px
     * @param py
     * @param arrowNeeded
     * @param fromEnemy
     * @return
     * @throws CloneNotSupportedException
     */
    public List<AbsBullet> createRockets(int shootPower,Integer px,Integer py,int arrowNeeded,Boolean fromEnemy) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();

        if (rocket == null){
            rocket =  new Rocket(surfaceView,
                    px,
                    py,
                    ImageTools.positionConvert(16),
                    0,
                    shootPower);
        }

        //AbsBullet absBullet = new Rocket(surfaceView,px, py, 0, 0, shootPower);
        for (int i = 0; i < arrowNeeded; i++) {
            AbsBullet abc = rocket.clone();
            abc.setPowerKeyDown(shootPower);

            abc.setPositionAndSpeed(px + this.randomPositionByRule(24+arrowNeeded),
                    py + this.randomPositionByRule(20+arrowNeeded),
                    ImageTools.positionConvert(16),
                    //限定powerkeydown 模拟火箭随机性
                    -ImageTools.positionConvert((shootPower+(this.randomSeed.nextInt(3)-1)) * 2 - 9)
            );
            absBulletList.add(abc);
        }
        MainScene.findMainScence(this.surfaceView).getBulletList().addAll(absBulletList);
        return absBulletList;
    }


    /**
     * fire crows
     * @param shootPower
     * @param ex
     * @param ey
     * @param arrowNeeded
     * @param fromEnemy
     * @return
     * @throws CloneNotSupportedException
     */
    public List<AbsBullet> createFireCrow(int shootPower,int arrowNeeded,Boolean fromEnemy,Integer ex,Integer ey) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();
        Log.i("firecrow","fromEnemy:"+fromEnemy);
        if (fireCrow == null){
            fireCrow = new FireCrow(surfaceView, 0, 0, 0, 0, shootPower);
        }
        //dame,code need refine
        if (fromEnemy){
            for (int i = 0; i < arrowNeeded; i++) {
                AbsBullet abc = fireCrow.clone();
                abc.setPowerKeyDown(shootPower);
                abc.setPositionAndSpeed(ex,
                        ey+(i*ImageTools.positionConvert(25)),
                        ImageTools.positionConvert(12),
                        ImageTools.positionConvert((shootPower*2)-10));

                abc.setFromEnemy(true);
                absBulletList.add(abc);
            }
        }else{
            //判断是否可以射击(超出射击间隔)
            if (itower.getWpMap().containsKey(fireCrow.getOid())) {
                fireCrow.setWpLevel(Integer.valueOf(itower.getWpMap().get(fireCrow.getOid())));
                int numberFx = fireCrow.getWpLevel()/4;//=============================level
                for (int i = 0; i < arrowNeeded+numberFx; i++) {
                    AbsBullet abc = fireCrow.clone();
                    abc.setPowerKeyDown(shootPower);
                    abc.setPositionAndSpeed(
                            itower.getX()+(itower.getWidth()>>1) + this.randomPositionByRule(45),
                            (itower.getHeight()>>1) + itower.getY() + this.randomPositionByRule(20),
                                    ImageTools.positionConvert(12),
                                    ImageTools.positionConvert((shootPower * 2) - 10));
                    Log.i("firecrow", "abc.getOid():" + abc.getOid());
                    absBulletList.add(abc);
                }
            }
        }
        return absBulletList;
    }


    /**
     * dragon
     * @param shootPower
     * @param px
     * @param py
     * @param arrowNeeded
     * @param fromEnemy
     * @return
     * @throws CloneNotSupportedException
     */
    public List<AbsBullet> createDragons(int shootPower,Integer px,Integer py,int arrowNeeded,Boolean fromEnemy) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();

        if (dragons == null){
            dragons =  new DragonRoc(surfaceView,px, py, 0, 0, shootPower);
        }

        if (itower.getWpMap().containsKey(dragons.getOid())){
            //AbsBullet absBullet = new Rocket(surfaceView,px, py, 0, 0, shootPower);
            dragons.setWpLevel(Integer.valueOf(itower.getWpMap().get(dragons.getOid())));
            for (int i = 0; i < arrowNeeded+dragons.getWpLevel(); i++) {
                AbsBullet abc = dragons.clone();
                abc.setPowerKeyDown(shootPower);
                abc.setPositionAndSpeed(px + this.randomPositionByRule(35),
                        py + this.randomPositionByRule(75),
                        ImageTools.positionConvert(11),
                        -5.0f);
                absBulletList.add(abc);
            }
        }

        MainScene.findMainScence(this.surfaceView).getBulletList().addAll(absBulletList);
        return absBulletList;
    }


    /**
     * dragon rain from banner leader
     * @param shootPower
     * @param targetX
     * @param targetY
     * @param arrowNeeded
     * @param fromEnemy
     * @return
     * @throws CloneNotSupportedException
     */
    public List<AbsBullet> createDragonRain(int shootPower,Integer targetX,Integer targetY,int arrowNeeded,Boolean fromEnemy) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();
        //Random randomPosition = new Random();

        if (dragonRain == null){
            dragonRain =  new DragonRain(surfaceView,targetX, targetY, 0, 0, shootPower);
        }

            //AbsBullet absBullet = new Rocket(surfaceView,px, py, 0, 0, shootPower);
            //dragonRain.setWpLevel(Integer.valueOf(itower.getWpMap().get(dragonRain.getOid())));
        targetX = targetX + ImageTools.positionConvert(this.randomPositionReg(80)-40);
        for (int i = 0; i < arrowNeeded; i++) {
            AbsBullet abc = dragonRain.clone();
            abc.setPowerKeyDown(shootPower);
            abc.setPositionAndSpeed(
                    targetX + ( targetY*4/5 + this.randomPositionByRule(arrowNeeded * 15)) ,
                    - this.randomPositionByRule(90),
                    ImageTools.positionConvert(8),
                    ImageTools.positionConvert(10));
            absBulletList.add(abc);
        }

        MainScene.findMainScence(this.surfaceView).getBulletList().addAll(absBulletList);
        return absBulletList;
    }

    /**
     *
     * @param shootPower
     * @param isMaxAttack 发生最大攻击的标志位,为0发动
     * @return
     * @throws CloneNotSupportedException
     */
    public List<AbsBullet> createAutoDef(int shootPower,int isMaxAttack) throws CloneNotSupportedException {
        List<AbsBullet> absBulletList = new LinkedList<AbsBullet>();

        if (this.autoDef == null){
            this.autoDef = new AutoDefence(surfaceView, 0, 0, 0, 0, shootPower);
        }
        //AbsBullet absBullet = new Arrow(surfaceView, 0, 0, 0, 0, shootPower);
        //dame,code need refine
        //判断是否可以射击(超出射击间隔)
        Log.i("db","find itower.getWpMap():"+itower.getWpMap());
        //:{DRAGON001=1, CAT001=1, OIL001=1, FLOUR001=1, ARR001=1}
        if (itower.getWpMap().containsKey(this.autoDef.getOid())) {
            int nowLevel = Integer.valueOf(itower.getWpMap().get(this.autoDef.getOid()));
            this.autoDef.setWpLevel(nowLevel);
            if(isMaxAttack == 0){
                isMaxAttack = nowLevel+1;
            }
            Log.i("dmg","level is "+nowLevel);
            for (int i = 0; i < isMaxAttack; i++) {
                AbsBullet abc = this.autoDef.clone();
                abc.setWpLevel(nowLevel);
                abc.setPowerKeyDown(shootPower);
                int posXX = itower.getX()+(itower.getWidth()>>1) + this.randomPositionReg(itower.getWidth() >> 2);
                int posYY = itower.getY()+ (itower.getHeight()>>5)  + this.randomPositionReg(itower.getHeight()>>1);
                int xSpeed = ImageTools.positionConvert(7) ;

                IEnemy firstEnemy = this.findEnemyFirst();
                if (firstEnemy != null){
                    int thisYSpeed = this.calculateForYSpeed(posXX,posYY,
                            firstEnemy.getX(),
                            firstEnemy.getY()+(firstEnemy.getHeight() >> 1),
                            xSpeed);
                    //Log.i("auto","thisYSpeed:"+thisYSpeed+",firstEnemy.getX():"+firstEnemy.getX()+",firstEnemy.getY():"+firstEnemy.getY());
                    abc.setPositionAndSpeed(
                            posXX,
                            posYY,
                            (firstEnemy.getX() ==  posXX?0:xSpeed) * (firstEnemy.getX()<posXX ? -1 : 1),
                            thisYSpeed );
                    absBulletList.add(abc);
                }
            }
        }
        return absBulletList;
    }

    /**
     *
     * @param launchX 发动x位置
     * @param launchY 发动y位置
     * @param speedX
     * @return
     */
    private int calculateForYSpeed(int launchX,int launchY,int tarX,int tarY,int speedX){
    /*        int deviationX = ;
        int deviationY = ;*/
        //目标在下面，那么yspeed也会得到负数
        float motherInt = Math.abs((tarX - launchX)==0?1:(tarX - launchX)) ;

        Log.i("auto","launchX:"+launchX+",launchY:"+launchY+",tarX:"+tarX+",tarY"+tarY+",=====result:"+((int)((tarY - launchY)/motherInt) * speedX));
        //launchX:241,launchY:174,tarX:641,tarY285
        return Math.round (((tarY - launchY)/motherInt) * speedX);
    }

    private synchronized IEnemy findEnemyFirst(){
        List<IEnemy> enemyList = MainScene.findMainScence(this.surfaceView).getEnemyList();
        if (enemyList.size()>0){
            return enemyList.get(0);
        }else{
            return null;
        }
    }


    /**
     * get random int with screen ration
     * @param originalPoint
     * @return
     */
    private int randomPositionByRule(int originalPoint){
        originalPoint = ImageTools.positionConvert(originalPoint);
        return this.randomSeed.nextInt(originalPoint);
    }

    /**
     * without ration of screen height
     * @param originalPoint
     * @return
     */
    private final int randomPositionReg(int originalPoint){
        return this.randomSeed.nextInt(originalPoint);
    }

}
