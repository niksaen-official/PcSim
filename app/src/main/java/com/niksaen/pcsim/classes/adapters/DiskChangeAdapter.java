package com.niksaen.pcsim.classes.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;

public class DiskChangeAdapter extends ArrayAdapter<String> {

    String[] strings;
    public DiskChangeAdapter(@NonNull Context context,String[] strings) {
        super(context, 0,strings);
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_pc_component,null);
        v.setBackgroundResource(R.color.background2);
        ImageView imageView = v.findViewById(R.id.image);
        TextView textView = v.findViewById(R.id.text);
        imageView.setImageDrawable(new AssetFile(getContext()).getImage("pc_component/images/"+ PcComponent.Disk +"/" + strings[position] + "_disk.png"));
        textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        textView.setText(strings[position]);
        return v;
    }
}
