package com.niksaen.pcsim.program.diskManager;

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

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

public class AppManagerAdapter extends ArrayAdapter<String> {

    public AppManagerAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public int BackgroundColor = Color.parseColor("#ffffff");
    public int TextColor = Color.parseColor("#000000");
    public MainActivity activity;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = LayoutInflater.from(getContext()).inflate(R.layout.item_app_manager,null);
        ImageView appIcon = item.findViewById(R.id.app_icon);
        TextView appName = item.findViewById(R.id.app_name);
        TextView useSpace = item.findViewById(R.id.use_space);

        appName.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/pixelFont.ttf"));
        useSpace.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/pixelFont.ttf"));

        item.setBackgroundColor(BackgroundColor);
        appName.setTextColor(TextColor);
        useSpace.setTextColor(TextColor);
        try {
            appIcon.setImageResource(Program.programIcon.get(getItem(position)));
            appName.setText(activity.words.get(getItem(position)));
            useSpace.setText(Program.programSize.get(getItem(position)) + "Gb");
        }catch (Exception e){
            item.setVisibility(View.GONE);
            System.out.println("not found: "+getItem(position));
        }
        return item;
    }
}
