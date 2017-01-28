package com.ck.ind.finddir.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Console;

/**
 * Created by KCSTATION on 2016/1/4.
 */
public class LaunchButton extends Button {

    private boolean inReady = true;

    public LaunchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSoundEffectsEnabled(false);
    }

    public LaunchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSoundEffectsEnabled(false);
    }

    public LaunchButton(Context context) {
        super(context);
        setSoundEffectsEnabled(false);
    }

    public boolean isInReady() {
        return inReady;
    }

    public void setInReady(boolean inReady) {
        this.inReady = inReady;
        Log.i("sendMessage","iget"+this.getTag());
        if (inReady){
            this.getBackground().setAlpha(255);
            if (this.getVisibility() != View.VISIBLE){
                this.setVisibility(View.VISIBLE);
            }
            this.setClickable(true);

        }else{
            this.getBackground().setAlpha(85);
            this.setClickable(false);
        }
    }


}
