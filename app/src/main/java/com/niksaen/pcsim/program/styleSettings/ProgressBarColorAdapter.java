package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.save.StyleSave;

public class ProgressBarColorAdapter extends RecyclerView.Adapter<ProgressBarColorAdapter.ViewHolder>{

    private int[] resourceDrawable = {
            R.drawable.progress_bar_circle_color1,
            R.drawable.progress_bar_circle_color2,
            R.drawable.progress_bar_circle_color3,
            R.drawable.progress_bar_circle_color4,
            R.drawable.progress_bar_circle_color5,
            R.drawable.progress_bar_circle_color6,
            R.drawable.progress_bar_circle_color7,
            R.drawable.progress_bar_circle_color8,
            R.drawable.progress_bar_circle_color9,
            R.drawable.progress_bar_circle_color10,
            R.drawable.progress_bar_circle_color11,
            R.drawable.progress_bar_circle_color12,
            R.drawable.progress_bar_circle_color13,
            R.drawable.progress_bar_circle_color14,
            R.drawable.progress_bar_circle_color15,
            R.drawable.progress_bar_circle_color16,
            R.drawable.progress_bar_circle_color17,
            R.drawable.progress_bar_circle_color18,
    };
    private String[] colorId = ColorList.ThemeColorList2;
    private ProgressBar test;
    private LayoutInflater layoutInflater;
    private Context context;
    private StyleSave styleSave;
    private int type=0;

    public ProgressBarColorAdapter(Context context, ProgressBar test, StyleSave styleSave){
        layoutInflater = LayoutInflater.from(context);
        this.test = test;
        this.context = context;
        currentDrawableResource = styleSave.ProgressBarResource;
        this.styleSave = styleSave;
    }
    public ProgressBarColorAdapter(Context context, ProgressBar test, StyleSave styleSave,int type){
        layoutInflater = LayoutInflater.from(context);
        this.test = test;
        this.context = context;
        currentDrawableResource = styleSave.ProgressBarResource;
        this.styleSave = styleSave;
        this.type = type;
    }

    @NonNull
    @Override
    public ProgressBarColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_color_2,viewGroup,false);
        return new ViewHolder(view);
    }

    public int currentDrawableResource;
    public int currentBgColor;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.colorView.setBackgroundColor(Color.parseColor(colorId[i]));
        viewHolder.colorView.setOnClickListener(v -> {
            currentDrawableResource = resourceDrawable[viewHolder.getAdapterPosition()];
            if(type == 0) {
                test.setProgressDrawable(context.getDrawable(resourceDrawable[viewHolder.getAdapterPosition()]));
            }else{
                currentBgColor = Color.parseColor(colorId[i]);
                LayerDrawable progressBarBackground = (LayerDrawable) test.getProgressDrawable();
                progressBarBackground.getDrawable(0).setColorFilter(Color.parseColor(colorId[i]), PorterDuff.Mode.SRC_IN);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorId.length;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        Button colorView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.current_color);
        }
    }
}

