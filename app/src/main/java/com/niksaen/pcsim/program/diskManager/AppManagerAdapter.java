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

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;

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
        if(getItem(position).startsWith(DriverInstaller.AdditionalSoftPrefix)){
            item.setPadding(0,0,0,0);
            item.setVisibility(View.GONE);
        }
        else if(getItem(position).startsWith(DriverInstaller.DriversPrefix)){
            appIcon.setVisibility(View.GONE);
            appName.setText(
                    getItem(position)
                    .replace(DriverInstaller.DriverForMotherboard,activity.words.get(DriverInstaller.DriverForMotherboard))
                    .replace(DriverInstaller.DriverForCPU,activity.words.get(DriverInstaller.DriverForCPU))
                    .replace(DriverInstaller.DriverForRAM,activity.words.get(DriverInstaller.DriverForRAM))
                    .replace(DriverInstaller.DriverForStorageDevices,activity.words.get(DriverInstaller.DriverForStorageDevices))
                    .replace(DriverInstaller.DriverForGPU,activity.words.get(DriverInstaller.DriverForGPU))
                    .replace(DriverInstaller.EXTENDED_TYPE,activity.words.get(DriverInstaller.EXTENDED_TYPE))
                    .replace(DriverInstaller.BASE_TYPE,activity.words.get(DriverInstaller.BASE_TYPE)));
            useSpace.setVisibility(View.GONE);
        }
        else {
            appIcon.setImageResource(ProgramListAndData.programIcon.get(getItem(position)));
            appName.setText(activity.words.get(getItem(position)));
            useSpace.setText(ProgramListAndData.programSize.get(getItem(position)) + "Gb");
        }
        return item;
    }
}
