package com.niksaen.pcsim.classes;

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

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<String> {

    private  Context context;
    private String[] strings;

    public CustomListViewAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.strings = objects;
    }
    public CustomListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.strings = objects.toArray(new String[0]);
    }


    @Override
    public int getCount() {
        return strings.length;
    }

    public int BackgroundColor1 = Color.parseColor("#ffffff");
    public int BackgroundColor2 = Color.parseColor("#ffffff");
    public int TextColor = Color.parseColor("#000000");

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"));
        textView.setTextSize(27);
        textView.setPadding(12,12,12,12);
        textView.setTextColor(TextColor);
        textView.setBackgroundColor(BackgroundColor1);
        textView.setText(strings[position]);
        return textView;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"));
        textView.setTextSize(27);
        textView.setPadding(12,12,12,12);
        textView.setTextColor(TextColor);
        textView.setText(strings[position]);
        textView.setBackgroundColor(BackgroundColor2);
        return textView;
    }

}