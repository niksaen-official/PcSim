package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.save.StyleSave;

public class ButtonColorAdapter extends RecyclerView.Adapter<ButtonColorAdapter.ViewHolder>{

    private int[] closeButtonDrawable = {
            R.drawable.button_1_color1,
            R.drawable.button_1_color2,
            R.drawable.button_1_color3,
            R.drawable.button_1_color4,
            R.drawable.button_1_color5,
            R.drawable.button_1_color6,
            R.drawable.button_1_color7,
            R.drawable.button_1_color8,
            R.drawable.button_1_color9,
            R.drawable.button_1_color10,
            R.drawable.button_1_color11,
            R.drawable.button_1_color12,
            R.drawable.button_1_color13,
            R.drawable.button_1_color14,
            R.drawable.button_1_color15,
            R.drawable.button_1_color16,
            R.drawable.button_1_color17,
            R.drawable.button_1_color18,
            R.drawable.button_1_color19,
            R.drawable.button_1_color20
    };
    int[] fullscreen1ButtonDrawable = {
            R.drawable.button_2_1_color1,
            R.drawable.button_2_1_color2,
            R.drawable.button_2_1_color3,
            R.drawable.button_2_1_color4,
            R.drawable.button_2_1_color5,
            R.drawable.button_2_1_color6,
            R.drawable.button_2_1_color7,
            R.drawable.button_2_1_color8,
            R.drawable.button_2_1_color9,
            R.drawable.button_2_1_color10,
            R.drawable.button_2_1_color11,
            R.drawable.button_2_1_color12,
            R.drawable.button_2_1_color13,
            R.drawable.button_2_1_color14,
            R.drawable.button_2_1_color15,
            R.drawable.button_2_1_color16,
            R.drawable.button_2_1_color17,
            R.drawable.button_2_1_color18,
            R.drawable.button_2_1_color19,
            R.drawable.button_2_1_color20
    };
    int[] fullscreen2ButtonDrawable = {
            R.drawable.button_2_2_color1,
            R.drawable.button_2_2_color2,
            R.drawable.button_2_2_color3,
            R.drawable.button_2_2_color4,
            R.drawable.button_2_2_color5,
            R.drawable.button_2_2_color6,
            R.drawable.button_2_2_color7,
            R.drawable.button_2_2_color8,
            R.drawable.button_2_2_color9,
            R.drawable.button_2_2_color10,
            R.drawable.button_2_2_color11,
            R.drawable.button_2_2_color12,
            R.drawable.button_2_2_color13,
            R.drawable.button_2_2_color14,
            R.drawable.button_2_2_color15,
            R.drawable.button_2_2_color16,
            R.drawable.button_2_2_color17,
            R.drawable.button_2_2_color18,
            R.drawable.button_2_2_color19,
            R.drawable.button_2_2_color20,
    };

    int[] rollUpButtonDrawable = {
            R.drawable.button_3_color1,
            R.drawable.button_3_color2,
            R.drawable.button_3_color3,
            R.drawable.button_3_color4,
            R.drawable.button_3_color5,
            R.drawable.button_3_color6,
            R.drawable.button_3_color7,
            R.drawable.button_3_color8,
            R.drawable.button_3_color9,
            R.drawable.button_3_color10,
            R.drawable.button_3_color11,
            R.drawable.button_3_color12,
            R.drawable.button_3_color13,
            R.drawable.button_3_color14,
            R.drawable.button_3_color15,
            R.drawable.button_3_color16,
            R.drawable.button_3_color17,
            R.drawable.button_3_color18,
            R.drawable.button_3_color19,
            R.drawable.button_3_color20,
    };

    private Button[] buttons;
    private LayoutInflater layoutInflater;

    public int currentCloseButtonImageRes;
    public int currentFullscreen1ButtonImageRes;
    public int currentFullscreen2ButtonImageRes;
    public int currentRollUpButtonImageRes;

    public ButtonColorAdapter(Context context, Button[] buttons, StyleSave styleSave){
        layoutInflater = LayoutInflater.from(context);
        this.buttons = buttons;
        currentCloseButtonImageRes = styleSave.CloseButtonImageRes;
        currentFullscreen1ButtonImageRes = styleSave.FullScreenMode1ImageRes;
        currentFullscreen2ButtonImageRes = styleSave.FullScreenMode2ImageRes;
        currentRollUpButtonImageRes = styleSave.RollUpButtonImageRes;
        backgroundColor = styleSave.ThemeColor1;
    }
    int backgroundColor;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_buttons,viewGroup,false);
        view.setBackgroundColor(backgroundColor);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.buttons[0].setBackgroundResource(closeButtonDrawable[i]);
        viewHolder.buttons[1].setBackgroundResource(fullscreen1ButtonDrawable[i]);
        viewHolder.buttons[2].setBackgroundResource(fullscreen2ButtonDrawable[i]);
        viewHolder.buttons[3].setBackgroundResource(rollUpButtonDrawable[i]);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons[0].setBackgroundResource(closeButtonDrawable[i]);
                buttons[1].setBackgroundResource(fullscreen1ButtonDrawable[i]);
                buttons[2].setBackgroundResource(rollUpButtonDrawable[i]);
                currentCloseButtonImageRes = closeButtonDrawable[i];
                currentFullscreen1ButtonImageRes = fullscreen1ButtonDrawable[i];
                currentFullscreen2ButtonImageRes = fullscreen2ButtonDrawable[i];
                currentRollUpButtonImageRes = rollUpButtonDrawable[i];
            }
        });
    }

    @Override
    public int getItemCount() {
        return closeButtonDrawable.length;
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
        }
    }
}

