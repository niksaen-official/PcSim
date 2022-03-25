package com.niksaen.pcsim.games.snake;

import android.view.View;
import android.widget.ImageView;

public class PartOfTail{
    Coordinate coordinate;
    View view;
    public PartOfTail(Coordinate coordinate,View view){
        this.view = view;
        this.coordinate = coordinate;
    }
}
