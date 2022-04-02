package com.niksaen.pcsim.classes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;

public class DiskChangeAdapter extends ArrayAdapter<String> {

    String[] strings;
    public boolean imageEnabled = true;
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
        v.setBackgroundColor(Color.parseColor("#111111"));
        ImageView imageView = v.findViewById(R.id.image);
        TextView textView = v.findViewById(R.id.text);
        if(imageEnabled) {
            imageView.setImageDrawable(new AssetFile(getContext()).getImage("pc_component/images/" + PcComponent.Disk + "/" + strings[position] + "_disk.png"));
        }else {
            imageView.setVisibility(View.GONE);
            textView.setPadding(0,0,0,0);
            v.setPadding(0,0,0,0);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.setMarginEnd(0);
        }
        textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        textView.setText(strings[position]);
        return v;
    }
}
