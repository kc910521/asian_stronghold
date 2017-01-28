package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.SurfaceView;

import com.ck.ind.finddir.Constant;
import com.ck.ind.finddir.bean.object.Bloodstain;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.bean.wreck.IRemains;
import com.ck.ind.finddir.factory.EnemyFactory;
import com.ck.ind.finddir.factory.ObjectFactory;
import com.ck.ind.finddir.factory.WreckFactory;
import com.ck.ind.finddir.play.PlayScene;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.Random;

/**
 * Created by KCSTATION on 2015/8/13.
 */
public abstract class AbsEnemyObj{
    protected int x;
    protected int y;
    protected SurfaceView surfaceView;
    protected int actionIndex = 0;
    protected int height;
    protected int size;//getWidth
    protected Bitmap[] runningBitmap = null;
    //默认数组第一元素为攻击等待
    protected Bitmap[] attackBitmaps = null;
    protected int attackRange = 1;
    protected int attackPower = 1;//ontheway
    protected int buildingAttPower = 0;
    protected double runSpeed = 1;
    protected int HP = 1;
    protected int attackInterval;
    protected Boolean isAlive = true;

    protected boolean inRange = false;
    protected boolean readyShot = true;
    protected int shotActionIndex = -1;
    protected int lastAttIndex = -1;
    protected Long lastAttackTime = null;

    protected int maxHp = 0;
    //rotate ration max:360
    protected int  rotateRation = 0;

    //isdead and need fly
    protected boolean isdeadAndFly  = false;
    protected boolean inMelee = false;
    //if >=0 attackBitmaps should not null
    protected int meLeeRange = -99;
    //need show blood when hurt
    protected boolean bloodstainInHurt = true;

    protected int DeadposY ;

    //如果为true，直接越过城市后销毁
    protected boolean isFlyUnit = false;

    protected float tmInterval = 0;

    private float t_interval;

    private float deadToFlyXSpeed = 0;

    public AbsEnemyObj(SurfaceView surfaceView){

        this.surfaceView = surfaceView;
        this.tmInterval = ImageTools.positionConvert(0.5f);
        this.deadToFlyXSpeed = ImageTools.positionConvert(1.5f);
    }





    public void setCurPostion(int x, int y) {
        //随机化初始动作
        this.actionIndex = (new Random()).nextInt(runningBitmap.length);
        this.x = x;
        this.y = y;
    }

    public void attackBuilding(){
        Itower.effectHpValue(-buildingAttPower);
    };

    public void getDamange(int damagePoint){
        this.HP -= damagePoint;
        if (this.HP <= 0){
            this.DeadposY = this.y;
            isdeadAndFly = true;
        }else if(this.bloodstainInHurt){
            Log.i("fdebug","ey:"+this.y+",e height:"+this.height+",blood y"+(this.y + this.height * 9 / 10));
            MainScene.findMainScence(this.surfaceView).getObjSenceList().add(
                    ObjectFactory.initObjectFactory(this.surfaceView).createBloodstain(Bloodstain.class, this.x, this.y + this.height * 9 / 10)
            );
        }
        afterUnderAttack();
    };

    /**
     * 被攻击后
     */
    protected void afterUnderAttack(){

    }

    protected void damageToFlyDraw(){
        Log.i("dmg","damageToFlyDraw HP:"+this.HP);
        if (this.y <= this.DeadposY && this.y > -20 && this.x < Constant.SCREEN_WIDTH  && this.HP < -(this.maxHp*3)){//
            rotateRation+=23;
            this.x += this.deadToFlyXSpeed *this.t_interval;
            this.y += Constant.G *this.t_interval *this.t_interval+(-5) *this.t_interval+0.5f;
            this.t_interval+=this.tmInterval;
        }else{
            Log.i("dead","remian over");
            IRemains pikemenRemains = this.generateRemain();
            IRemains.remainList.add(pikemenRemains);
            this.destory();
        }

    }


    public void onLogic(){
        if(isdeadAndFly){
            damageToFlyDraw();
        }else{
            //未被炸飞
            if ((!inRange || !readyShot) && (attackRange>0)) {
                //is need shoot cal
                this.calculateShouldAttack();
            }
            //----------------|| no melee and
            if (this.x < 0 || (MainScene.findMainScence(null) != null &&
                    this.meLeeRange<0 &&
                    MainScene.findMainScence(null).getTower() != null &&
                    this.x < (MainScene.findMainScence(null).getTower().getX()+MainScene.findMainScence(null).getTower().getWidth()) &&
                    this.y > MainScene.findMainScence(null).getTower().getY() &&
                    this.y < (MainScene.findMainScence(null).getTower().getY()+MainScene.findMainScence(null).getTower().getHeight()

                    )
            )) {
                if (!isFlyUnit){
                    this.attackBuilding();
                    this.destory();
                }else{
                    //bird go over city
                    this.inNormalMove();
                }
                if (this.x < -30){
                    this.destory();
                }


            } else {
                //to attack
                if (this.inRange && this.readyShot){
                    //after shoot,statement changes
                    if (this.meLeeRange >= 0 && this.inMelee){
                        if(this.meleeAttackAction()){
                            //自动防御预防死角
                            //this.getDamange(1);
                            this.readyShot = false;
                        };
                    }else{
                        if(attackAction()){
                            this.readyShot = false;
                        };
                    }

                }else{
                    //in normal,go forward
                    this.inNormalMove();
                }
            }
        }
    }

    private void inNormalMove(){
        if ((runningBitmap.length-1) <= actionIndex  ){//|| runningBitmap[actionIndex] == null
            actionIndex = 0;
        }else{
            actionIndex ++;
        }
        this.x -= this.runSpeed;
        this.goSpecLogic();
    }

    protected void goSpecLogic(){
        //write special logic in normal runnng
    }

    public void onDraw(Canvas canvas,Paint paint) {
        if (isdeadAndFly && ((runningBitmap.length - 1) >= actionIndex)) {
            canvas.drawBitmap(ImageTools.adjustPhotoRotation(runningBitmap[actionIndex], this.rotateRation), this.x - Constant.MOVE_X_OFFSET, this.y, paint);
        }else if((!inRange || !readyShot)  && !inMelee){//&& (attackRange>0)
            //continue go forward
            canvas.drawBitmap(runningBitmap[actionIndex], this.x - Constant.MOVE_X_OFFSET, this.y, paint);
            //Log.i("newmod", "!inRange || !readyShot x:" + this.x);
        }else if(this.inRange && this.readyShot && attackBitmaps != null  && ((attackBitmaps.length-1) >= shotActionIndex)){
            if (attackBitmaps[shotActionIndex] == null){
                canvas.drawBitmap(attackBitmaps[lastAttIndex], this.x - Constant.MOVE_X_OFFSET, this.y, paint);
            }else{
                canvas.drawBitmap(attackBitmaps[shotActionIndex], this.x - Constant.MOVE_X_OFFSET, this.y, paint);
                this.lastAttIndex = shotActionIndex;
            }
        }else if (inMelee && attackBitmaps != null ){
            //attack interval idle
            canvas.drawBitmap(attackBitmaps[0], this.x - Constant.MOVE_X_OFFSET, this.y, paint);
        }
        else{
            Log.i("newmod", "go error aindex:1,actionIndex:" + actionIndex + ";shotActionIndex" + shotActionIndex + ";inRange:" + inRange + ";readyShot:"+readyShot);

        }
    }

    /**
     *
     * @return attaction finish
     */
    protected boolean attackAction(){
        //just type in one loop code because of could be call for loop
        return false;
    }

    protected boolean meleeAttackAction(){
        if (this.attackBitmaps != null){
            if (this.shotActionIndex+1 >= attackBitmaps.length ){
                //射击的最后动作完成
                Itower.effectHpValue(-this.buildingAttPower);
                this.lastAttackTime = System.currentTimeMillis();
                this.shotActionIndex = -1;
                return true;
            }
            shotActionIndex ++;
        }
        return false;
    }

    public int destory() {
        this.isAlive = false;
        if (MainScene.findMainScence(null) == null){
            PlayScene.findMainScence(this.surfaceView).getEnemyList().remove(this);
        }else{
            MainScene.findMainScence(this.surfaceView).getEnemyList().remove(this);
        }
        return 0;
    }

    /**
     * 是否进入射程
     */
    private void calculateShouldAttack(){
        //在playscene 不会初始化mainscene
        if (MainScene.findMainScence(null) != null && MainScene.findMainScence(null).getTower() != null){
            Itower itower = MainScene.findMainScence(this.surfaceView).getTower();
            //if (itower != null){
                if (this.attackRange>this.getAbsoluteDistance(this.x,this.y,itower.getX(),itower.getY(),itower.getWidth())){
                    inRange = true;
                }else{
                    inRange = false;
                }
                if (this.meLeeRange >= 0 &&
                        this.meLeeRange > this.getAbsoluteDistance(this.x,this.y,itower.getX(),itower.getY(),itower.getWidth()) &&
                        this.y <= (itower.getY()+ itower.getHeight())
                        ){
                    inMelee = true;
                }else{
                    inMelee = false;
                }
                if (this.lastAttackTime == null|| (System.currentTimeMillis())-this.lastAttackTime>attackInterval){
                    this.readyShot = true;
                }else{
                    this.readyShot = false;
                }
           // }
        }

    }
    /**
     * 求绝对距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param targetWidth
     * @return
     */
    private final int getAbsoluteDistance(int x1,int y1,int x2,int y2,int targetWidth){
        int rAngleLenght1 = Math.abs(x1 - x2);
        int rAngleLenght2 = Math.abs(y1 - y2);
        Double tempDb = Math.sqrt(Math.pow(rAngleLenght1, 2) + Math.pow(rAngleLenght2, 2))-targetWidth;
        return tempDb.intValue();
    }

    protected IRemains generateRemain(){
        //生成残骸
        return WreckFactory.initWFactory(this.surfaceView).producePikeWreck(this.x, this.y + (this.height * 9/ 10));
    }

    /**
     * resize bitmap size
     * @param width
     * @param height
     * @return
     */
    protected Bitmap[] resizeBitMapBach(Bitmap[] bitmaps,int width,int height){
        Log.i("msg","resize width&height to-->width："+width/Constant.SCREEN_HEIGHT_SP);
        return ImageTools.resizeBitMapBachBeRule(bitmaps,
                width,
                height);
    }


    public void fullHp() {
        this.HP = this.maxHp;
        this.isAlive = true;
        isdeadAndFly =false;
    }
}
