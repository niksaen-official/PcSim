package com.niksaen.pcsim.program.musicplayer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.FileUtil;

import java.util.ArrayList;

public class MusicListAdapter extends ArrayAdapter<String> {

    ArrayList<String> objects;
    private Context context;

    private Typeface typeface;

    public MusicListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public ArrayList<String>  getObjects() {
        return objects;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable String item) {
        return super.getPosition(item);
    }

    public int ColorBackground,ColorText;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        TextView textView = (TextView) inflater.inflate(R.layout.item_textview,null);
        textView.setBackgroundColor(ColorBackground);
        textView.setTextColor(ColorText);
        textView.setTypeface(typeface);
        textView.setText(objects.get(position).substring(objects.get(position).lastIndexOf("/")+1));
        return textView;
    }
}
