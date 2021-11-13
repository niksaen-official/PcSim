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
import androidx.annotation.Nullable;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

public class StartMenuAdapter extends  ArrayAdapter<String> {

    public StartMenuAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    MainActivity activity;
    public void setMainActivity(MainActivity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = LayoutInflater.from(getContext()).inflate(R.layout.item_start_menu, null);
        if(!(getItem(position).startsWith(Program.DriversPrefix) || getItem(position).startsWith(Program.AdditionalSoftPrefix))) {
            item.setBackgroundColor(activity.styleSave.StartMenuColor);
            TextView programName = item.findViewById(R.id.app_name);
            ImageView programIcon = item.findViewById(R.id.app_icon);

            if (activity.styleSave.StartMenuAppIconVisible)
                programIcon.setImageResource(Program.programIcon.get(getItem(position)));
            else
                programIcon.setVisibility(View.GONE);

            if (activity.styleSave.StartMenuAppNameVisible) {
                programName.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"));
                programName.setText(activity.words.get(getItem(position)));
                programName.setTextColor(activity.styleSave.StartMenuTextColor);
            } else {
                programName.setVisibility(View.GONE);
            }
        }else {
            item.setPadding(0,0,0,0);
            item.setVisibility(View.GONE);
        }
        return item;
    }
}
