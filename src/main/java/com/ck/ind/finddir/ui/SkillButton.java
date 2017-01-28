package com.ck.ind.finddir.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ck.ind.finddir.R;

/**
 * Created by KCSTATION on 2015/11/23.
 * 技能选择界面
 */
public class SkillButton extends LinearLayout {


    private ImageView mImgView = null;
    private TextView mTextView = null;
    private TextView mLevelText = null;
    private Context mContext = null;

    public SkillButton(Context context) {
        super(context);
    }

    public SkillButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.isk_btn_layout, this, true);
        this.mImgView = (ImageView) findViewById(R.id.ibtn_img);
        this.mTextView = (TextView) findViewById(R.id.ibtn_text);
        this.mLevelText = (TextView) findViewById(R.id.ibtn_level);
    }

    private void setImgResource() {
        this.mImgView.setImageDrawable(this.getBackground());
        this.setBackgroundResource(0);
    }

    private void setmTextContent() {
        String valueStr = (this.getContentDescription().toString()).split(":")[0];
        mTextView.setText(valueStr);
    }

    public void initFunc(){
        this.setImgResource();
        this.setmTextContent();
    }

    public void setTextSize(float sizef) {
        this.mTextView.setTextSize(sizef);
    }

    public void setLevelNums(String levelNumsStr){
        this.mLevelText.setText("LV."+levelNumsStr);
    }

    public void setImgTransparent(int tsParent){
        if (this.mImgView.getDrawable() != null){
            this.mImgView.getDrawable().setAlpha(tsParent);
            this.mLevelText.setTextColor(Color.argb(tsParent,51,51,51));
        }

    }
}
