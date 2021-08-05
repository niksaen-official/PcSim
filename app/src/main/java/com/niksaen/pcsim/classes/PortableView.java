package com.niksaen.pcsim.classes;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

public class PortableView implements View.OnTouchListener{

    private float _xDelta = 0.0f,_yDelta = 0.0f;
    View layout;

    public PortableView(View v){
        layout = v;
        layout.setOnTouchListener(this);
    }
    public View getLayout(){
        return layout;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    /**
     * В этом методе ничего не менять*/
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                _xDelta = layout.getX() - event.getRawX();
                _yDelta = layout.getY()  - event.getRawY();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    layout.setZ(0);
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE:
                layout.animate().x(event.getRawX()+ _xDelta).y(event.getRawY()+ _yDelta).setDuration(0).start();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    layout.setZ(-2);
                }
                return true;
            default:return false;
        }
    }
}
