package com.niksaen.pcsim.classes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.niksaen.pcsim.os.cmd.CMD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CMD_Adapter extends ArrayAdapter<String> {
    private final Context context;
    public ArrayList<String> strings;

    public CMD_Adapter(Context context, ArrayList<String> objects) {
        super(context,0, objects);
        this.context = context;
        this.strings = objects;
    }

    // for listview
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"));
        textView.setTextSize(27);
        textView.setPadding(12,12,12,12);
        if(strings.get(position) != null) {
            if (strings.get(position).startsWith(CMD.ERROR)) {
                textView.setTextColor(Color.RED);
                textView.setText(strings.get(position).replace(CMD.ERROR, ""));
            } else if (strings.get(position).startsWith(CMD.WARN)) {
                textView.setTextColor(Color.YELLOW);
                textView.setText(strings.get(position).replace(CMD.WARN, ""));
            } else if (strings.get(position).startsWith(CMD.SUCCESS)) {
                textView.setTextColor(Color.GREEN);
                textView.setText(strings.get(position).replace(CMD.SUCCESS, ""));
            } else {
                textView.setTextColor(Color.WHITE);
                textView.setText(strings.get(position));
            }
        }
        textView.setBackgroundColor(Color.parseColor("#1B1B1B"));
        return textView;
    }
}
