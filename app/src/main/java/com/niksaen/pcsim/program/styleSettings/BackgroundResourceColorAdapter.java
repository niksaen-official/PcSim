package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.niksaen.pcsim.R;

public class BackgroundResourceColorAdapter extends RecyclerView.Adapter<BackgroundResourceColorAdapter.ViewHolder> {

    private int[] colorId = {
            R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7, R.color.color8,
            R.color.color9, R.color.color10, R.color.color11, R.color.color12, R.color.color13, R.color.color14, R.color.color15, R.color.color16,
            R.color.color17, R.color.color18, R.color.color19, R.color.color20, R.color.color21, R.color.color22, R.color.color23, R.color.color24,
            R.color.color25, R.color.color26, R.color.color27, R.color.color28, R.color.color29, R.color.color30, R.color.color31
    };

    private View testBackground;
    private LayoutInflater layoutInflater;
    public BackgroundResourceColorAdapter(Context context, View testBackground){
        layoutInflater = LayoutInflater.from(context);
        this.testBackground = testBackground;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_color_2,viewGroup,false);
        return new ViewHolder(view);
    }

    public int currentColorId;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.colorView.setBackgroundResource(colorId[i]);
        viewHolder.colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentColorId = colorId[i];
                testBackground.setBackgroundResource(colorId[i]);
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

