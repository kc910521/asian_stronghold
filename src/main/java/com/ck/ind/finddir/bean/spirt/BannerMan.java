package com.ck.ind.finddir.bean.spirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.ck.ind.finddir.R;
import com.ck.ind.finddir.bean.tower.AbsBullet;
import com.ck.ind.finddir.bean.tower.Itower;
import com.ck.ind.finddir.scene.MainScene;
import com.ck.ind.finddir.toolkits.ImageTools;

import java.util.List;

/**
 * Created by KCSTATION on 2015/8/13.
 */
public class BannerMan extends AbsEnemyObj implements IEnemy,Cloneable{

    private Itower itower = null;

    public BannerMan(SurfaceView surfaceView){
        super(surfaceView);
        runningBitmap = new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_mov_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_mov_3),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_mov_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_mov_3)
        };
        attackBitmaps= new Bitmap[]{
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_att_1),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_att_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_att_3),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_att_2),
                BitmapFactory.decodeResource(this.surfaceView.getResources(),R.drawable.banner_att_1)
        };
        super.resizeBitMapBach(runningBitmap,35,70);
        super.resizeBitMapBach(attackBitmaps,35,70);

        this.size = runningBitmap[actionIndex].getWidth();
        this.height = runningBitmap[actionIndex].getHeight();

        this.HP = this.maxHp = 5;
        this.attackRange = ImageTools.positionConvert(335);
        this.attackInterval = 8500;
        this.attackPower = 8;
        this.buildingAttPower = 2;
        this.runSpeed = ImageTools.positionConvert(3.0);
        if(MainScene.findMainScence(null) != null){
            itower = MainScene.findMainScence(this.surfaceView).getTower();
        }

    }



    @Override
    protected boolean attackAction() {

        if (this.shotActionIndex >= (attackBitmaps.length-1) ){//射击的最后动作完成
            //Itower.effectHpValue(-this.attackPower);
            //shoot arrows
            List<AbsBullet> dragonRain = null;
            this.itower = MainScene.findMainScence(this.surfaceView).getTower();
            try {
                dragonRain = MainScene.findMainScence(this.surfaceView).getBulletFactory().createDragonRain(8,
                        itower.getX()+ (itower.getWidth()>>1)  ,
                        itower.getY()+ (itower.getHeight()>>1) ,
                        3 ,true);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            MainScene.findMainScence(this.surfaceView).getBulletList().addAll(dragonRain);
            this.lastAttackTime = System.currentTimeMillis();
            this.shotActionIndex = -1;
//            canvas.drawBitmap(attackBitmaps[shotActionIndex], this.x- Constant.MOVE_X_OFFSET, this.y, paint);
            //this.shotActionIndex++;
            return true;
        }else{
            //canvas.drawBitmap(attackBitmaps[shotActionIndex], this.x- Constant.MOVE_X_OFFSET, this.y, paint);
            this.shotActionIndex++;
        }
        return false;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public IEnemy clone() {
        IEnemy iEnemy = null;
        try {
            iEnemy = (IEnemy) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return iEnemy;
    }


}
