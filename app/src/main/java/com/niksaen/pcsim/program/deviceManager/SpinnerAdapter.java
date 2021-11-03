package com.niksaen.pcsim.program.deviceManager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    String[] objects;
    LayoutInflater layoutInflater;
    Typeface font;
    String str;
    String type;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects, String text, String type) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        str = text;
        this.type = type;
        layoutInflater=LayoutInflater.from(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    //settings adapter
    public int TextColor_View = Color.parseColor("#000000");
    public int TextColor_DropDownView = Color.parseColor("#000000");
    public int BackgroundColor_View = Color.TRANSPARENT;
    public int BackgroundColor_DropDownView = Color.TRANSPARENT;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects, String text, String type) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects.toArray(new String[0]);
        str = text;
        this.type = type;
        layoutInflater=LayoutInflater.from(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.check_iron_item,null);
        TextView textView = view.findViewById(R.id.textView);
        view.findViewById(R.id.image).setVisibility(View.GONE);
        textView.setTypeface(font);
        textView.setText(objects[position]);
        textView.setTextColor(TextColor_DropDownView);
        view.setBackgroundColor(BackgroundColor_DropDownView);
        return view;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.check_iron_item,null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setTypeface(font);
        textView.setText(str);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageDrawable(new AssetFile(context).getImage("icons/deviceManager/"+type+".png"));

        textView.setTextColor(TextColor_View);
        textView.setBackgroundColor(BackgroundColor_View);
        return view;
    }

}
