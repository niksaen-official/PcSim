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

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder>{

    private String[] colorTheme1 = ColorList.ThemeColorList1;
    private String[] colorTheme2 = ColorList.ThemeColorList2;
    private String[] colorTheme3 = ColorList.ThemeColorList3;


    private View[] views;
    private StyleSave styleSave;
    private LayoutInflater layoutInflater;
    public ThemeAdapter(Context context, View[] views, StyleSave styleSave){
        layoutInflater = LayoutInflater.from(context);
        this.views = views;
        ColorTheme1 = styleSave.ThemeColor1;
        ColorTheme2 = styleSave.ThemeColor2;
        ColorTheme3 = styleSave.ThemeColor3;
        BackgroundColor = styleSave.ThemeColor1;
        ArrowButtonImage = styleSave.ArrowButtonImage;
        PlayButtonImage = styleSave.PlayButtonImage;
        PauseButtonRes = styleSave.PauseButtonRes;
        PrevOrNextImageRes = styleSave.PrevOrNextImageRes;
        this.styleSave = styleSave;
    }

    int BackgroundColor;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_buttons,viewGroup,false);
        view.setBackgroundColor(BackgroundColor);
        return new ViewHolder(view);
    }

    int ColorTheme1,ColorTheme2,ColorTheme3,ArrowButtonImage,PlayButtonImage,PauseButtonRes,PrevOrNextImageRes;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.buttons[0].setBackgroundColor(Color.parseColor(colorTheme1[i]));
        viewHolder.buttons[1].setBackgroundColor(Color.parseColor(colorTheme2[i]));
        viewHolder.buttons[2].setBackgroundColor(Color.parseColor(colorTheme3[i]));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                views[0].setBackgroundColor(Color.parseColor(colorTheme1[viewHolder.getAdapterPosition()]));
                views[1].setBackgroundColor(Color.parseColor(colorTheme2[viewHolder.getAdapterPosition()]));
                ColorTheme1 = Color.parseColor(colorTheme1[viewHolder.getAdapterPosition()]);
                ColorTheme2 = Color.parseColor(colorTheme2[viewHolder.getAdapterPosition()]);
                ColorTheme3 = Color.parseColor(colorTheme3[viewHolder.getAdapterPosition()]);
                ArrowButtonImage=ColorList.colorArrow(colorTheme2[viewHolder.getAdapterPosition()]);
                PlayButtonImage=ColorList.colorPlay(colorTheme2[viewHolder.getAdapterPosition()]);
                PauseButtonRes=ColorList.colorPause(colorTheme2[viewHolder.getAdapterPosition()]);
                PrevOrNextImageRes = ColorList.colorNextOrPrev(colorTheme2[viewHolder.getAdapterPosition()]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorTheme1.length;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        Button[] buttons;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttons = new Button[]{
                    itemView.findViewById(R.id.close),
                    itemView.findViewById(R.id.fullscreenMode1),
                    itemView.findViewById(R.id.fullscreenMode2),
                    itemView.findViewById(R.id.roll_up),
            };
            buttons[3].setVisibility(View.GONE);
        }
    }
}

