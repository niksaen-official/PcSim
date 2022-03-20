package com.niksaen.pcsim.games.snake;

import android.view.View;
import android.widget.ImageView;

public class PartOfTail{
    int top;
    int left;
    View view;
    public PartOfTail(int top,int left,View view){
        this.view = view;
        this.left = left;
        this.top = top;
    }
}
