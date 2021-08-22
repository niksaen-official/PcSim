package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.save.StyleSave;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder>{

    private String[] colorId = {
            "#B71C1C", "#880E4F", "#4A148C", "#311B92", "#1A237E",
            "#0D47A1", "#01579B", "#006064", "#004D40", "#1B5E20",
            "#33691E", "#827717", "#F57F17", "#FF6F00", "#E65100",
            "#BF360C", "#FFFFFF", "#dddddd", "#cccccc", "#bbbbbb",
            "#aaaaaa", "#999999", "#888888", "#777777", "#666666",
            "#555555", "#444444", "#333333", "#222222", "#111111",
            "#000000",
    };
    private View test;
    private LayoutInflater layoutInflater;

    /**TypeView используется для получения установленного цвета
     * 0-для получения цвета окна
     * 1-для получения цвета меню пуска
     * 2-для получения цвета панели задач
     * */
    public ColorAdapter(Context context, View test,StyleSave styleSave,int TypeView){
        layoutInflater = LayoutInflater.from(context);
        this.test = test;
        switch (TypeView){
            case 0:{
                currentColor = styleSave.ColorWindow;
                break;
            }
            case 1:{
                currentColor = styleSave.StartMenuColor;
                colorId = ColorList.ColorLaunch;
                break;
            }
            case 2:{
                currentColor = styleSave.ToolbarColor;
                colorId = ColorList.ColorToolbar;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_color_2,viewGroup,false);
        return new ViewHolder(view);
    }

    public int currentColor;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.colorView.setBackgroundColor(Color.parseColor(colorId[i]));
        viewHolder.colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentColor = Color.parseColor(colorId[i]);
                test.setBackgroundColor(currentColor);
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

