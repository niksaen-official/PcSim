package com.niksaen.pcsim.program.diskManager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class DiskManagerAdapter extends RecyclerView.Adapter<DiskManagerAdapter.ViewHolder> {
    MainActivity activity;
    ArrayList<HashMap<String,String>> diskList;
    public DiskManagerAdapter(MainActivity activity, ArrayList<HashMap<String,String>> diskList){
        this.activity = activity;
        this.diskList = diskList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_for_disk_manager,parent,false);
        return new ViewHolder(view);
    }

    int TextColor = Color.parseColor("#000000"),
            BackgroundColor;
    @Override
    public void onBindViewHolder(@NonNull @NotNull DiskManagerAdapter.ViewHolder holder, int position) {
        HashMap<String,String> disk = diskList.get(position);
        holder.diskName.setText(disk.get("name"));
        holder.allSpace.setText(disk.get("Объём")+"GB");
        holder.emptySpace.setText(activity.pcParametersSave.getEmptyStorageSpace(disk)/1024 + "GB");
        holder.emptySpaceToPercent.setText(activity.pcParametersSave.getEmptyStorageSpace(disk)/(Integer.parseInt(disk.get("Объём"))*1024)*100+"%");

        holder.diskName.setTextColor(TextColor);
        holder.allSpace.setTextColor(TextColor);
        holder.emptySpace.setTextColor(TextColor);
        holder.emptySpaceToPercent.setTextColor(TextColor);

        holder.diskName.setBackgroundColor(BackgroundColor);
        holder.allSpace.setBackgroundColor(BackgroundColor);
        holder.emptySpace.setBackgroundColor(BackgroundColor);
        holder.emptySpaceToPercent.setBackgroundColor(BackgroundColor);

        holder.diskName.setTypeface(activity.font);
        holder.allSpace.setTypeface(activity.font);
        holder.emptySpace.setTypeface(activity.font);
        holder.emptySpaceToPercent.setTypeface(activity.font);

        holder.itemView.setOnClickListener(v -> {
            AppManager appManager = new AppManager(activity);
            appManager.setDiskData(diskList.get(position));
            appManager.openProgram();
        });
    }

    @Override
    public int getItemCount() {
        return diskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView diskName,allSpace,emptySpace,emptySpaceToPercent;

        ViewHolder(View view) {
            super(view);
            diskName = view.findViewById(R.id.diskName);
            allSpace = view.findViewById(R.id.allSpace);
            emptySpace = view.findViewById(R.id.emptySpace);
            emptySpaceToPercent = view.findViewById(R.id.emptySpaceToPercent);
        }
    }
    private int getEmptySpaceToPercent(int allSpace,int emptySpace){
        return emptySpace/allSpace*100;
    }
}
