package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.save.StyleSave;

public class TextColorAdapter extends RecyclerView.Adapter<TextColorAdapter.ViewHolder>{

    private String[] colorId = {
            "#B71C1C", "#880E4F", "#4A148C", "#311B92", "#1A237E",
            "#0D47A1", "#01579B", "#006064", "#004D40", "#1B5E20",
            "#33691E", "#827717", "#F57F17", "#FF6F00", "#E65100",
            "#BF360C", "#FFFFFF", "#dddddd", "#cccccc", "#bbbbbb",
            "#aaaaaa", "#999999", "#888888", "#777777", "#666666",
            "#555555", "#444444", "#333333", "#222222", "#111111",
            "#000000",
    };

    private TextView testTextView;
    private Button testButton;
    private LayoutInflater layoutInflater;

    /**TypeView используется для получения установленного цвета
     * 0 - для получения цвета заголовка окна
     * 1 - для получения цвета текста
     * 2 - для получения цвета текста приветствия
     * 3 - для получения цвета текста меню пуск
     * 4 - для получения цвета текста панели задач
     * 5 - для получения цвета текста рабочего стола
     * */
    public TextColorAdapter(Context context, TextView testTextView, StyleSave styleSave, int TypeView){
        layoutInflater = LayoutInflater.from(context);
        this.testTextView = testTextView;
        switch (TypeView){
            case 0:{
                currentTextColor = styleSave.TitleColor;
                break;
            }
            case 1:{
                currentTextColor = styleSave.TextColor;
                break;
            }
            case 2:{
                currentTextColor = styleSave.GreetingColor;
                break;
            }
            case 3:{
                currentTextColor = styleSave.ToolbarTextColor;
                break;
            }
            case 4:{
                currentTextColor = styleSave.StartMenuTextColor;
                break;
            }
            case 5:{
                currentTextColor = styleSave.DesktopTextColor;
                break;
            }
        }
    }
    public TextColorAdapter(Context context, Button testButton,StyleSave styleSave){
        layoutInflater = LayoutInflater.from(context);
        this.testButton = testButton;
        currentTextColor = styleSave.TextButtonColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_color_2,viewGroup,false);
        return new ViewHolder(view);
    }

    public int currentTextColor;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.colorView.setBackgroundColor(Color.parseColor(colorId[i]));
        viewHolder.colorView.setOnClickListener(v -> {
            currentTextColor = Color.parseColor(colorId[i]);
            if(testTextView != null){
                testTextView.setTextColor(currentTextColor);
            }
            else if(testButton != null){
                testButton.setTextColor(currentTextColor);
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

