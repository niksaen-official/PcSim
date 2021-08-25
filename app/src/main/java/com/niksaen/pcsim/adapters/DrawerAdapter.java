package com.niksaen.pcsim.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class DrawerAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] strings;

    public DrawerAdapter(Context context,String[] strings){
        super(context,0,strings);
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    public int BackgroundColor = Color.parseColor("#ffffff");
    public int TextColor = Color.parseColor("#000000");

    // for listview
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        TextView textView = new TextView(context);
        if(position == 0) {
            textView.setTextSize(46);
            textView.setPadding(22,22,22,22);
        }
        else{
            textView.setTextSize(40);
            textView.setPadding(42,22,22,22);
        }
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        textView.setTextColor(TextColor);
        textView.setText(strings[position]);
        textView.setBackgroundColor(BackgroundColor);
        return textView;
    }

}
