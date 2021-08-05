package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.save.StyleSave;

public class SeekBarColorAdapter extends RecyclerView.Adapter<SeekBarColorAdapter.ViewHolder>{

    private int[] resourceDrawable = {
            R.drawable.seek_progress_color1,
            R.drawable.seek_progress_color2,
            R.drawable.seek_progress_color3,
            R.drawable.seek_progress_color4,
            R.drawable.seek_progress_color5,
            R.drawable.seek_progress_color6,
            R.drawable.seek_progress_color7,
            R.drawable.seek_progress_color8,
            R.drawable.seek_progress_color9,
            R.drawable.seek_progress_color10,
            R.drawable.seek_progress_color11,
            R.drawable.seek_progress_color12,
            R.drawable.seek_progress_color13,
            R.drawable.seek_progress_color14,
            R.drawable.seek_progress_color15,
            R.drawable.seek_progress_color16,
            R.drawable.seek_progress_color17,
            R.drawable.seek_progress_color18,
    };
    private String[] colorId = ColorList.ThemeColorList2;

    private final SeekBar test;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final int Type;

    /** Type используется для определения того что надо изменить в seekBar
     * 0 - цвет прогресса
     * 1 - цвет ползунка */
    public SeekBarColorAdapter(Context context, SeekBar test, StyleSave styleSave,int Type){
        layoutInflater = LayoutInflater.from(context);
        this.test = test;
        this.Type = Type;
        this.context = context;
        if(Type == 0) {
            currentDrawableResource = styleSave.SeekBarProgressResource;
        }
        else{
            currentDrawableResource = styleSave.SeekBarThumbResource;
            resourceDrawable = new int[]{
                    R.drawable.seek_thumb_color1,
                    R.drawable.seek_thumb_color2,
                    R.drawable.seek_thumb_color3,
                    R.drawable.seek_thumb_color4,
                    R.drawable.seek_thumb_color5,
                    R.drawable.seek_thumb_color6,
                    R.drawable.seek_thumb_color7,
                    R.drawable.seek_thumb_color8,
                    R.drawable.seek_thumb_color9,
                    R.drawable.seek_thumb_color10,
                    R.drawable.seek_thumb_color11,
                    R.drawable.seek_thumb_color12,
                    R.drawable.seek_thumb_color13,
                    R.drawable.seek_thumb_color14,
                    R.drawable.seek_thumb_color15,
                    R.drawable.seek_thumb_color16,
                    R.drawable.seek_thumb_color17,
                    R.drawable.seek_thumb_color18
            };
            colorId = new String[]{
                    "#B02D2D", "#9E177F", "#77209F", "#4F2BA1","#2A3899",
                    "#1572A7", "#0289A5", "#0289A5", "#007C67", "#2B8C33",
                    "#529130", "#9A9625", "#84440D", "#C38200","#B35713",
                    "#6E6E6E", "#393939", "#161616"
            };
        }
    }

    @NonNull
    @Override
    public SeekBarColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_color_2,viewGroup,false);
        return new ViewHolder(view);
    }

    public int currentDrawableResource;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.colorView.setBackgroundColor(Color.parseColor(colorId[i]));
        viewHolder.colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDrawableResource = resourceDrawable[viewHolder.getAdapterPosition()];
                if(Type == 0) {
                    test.setProgressDrawable(context.getDrawable(resourceDrawable[viewHolder.getAdapterPosition()]));
                }
                else{
                    test.setThumb(context.getDrawable(resourceDrawable[viewHolder.getAdapterPosition()]));
                }
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

