package com.niksaen.pcsim.classes.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.ProgramListAndData;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DesktopAdapter extends  RecyclerView.Adapter<DesktopAdapter.ViewHolder> {

    Context context;
    String[] apps;
    MainActivity activity;
    HashMap<String,Program> programHashMap;
    public DesktopAdapter(MainActivity activity, String[] apps){
        this.context = activity.getBaseContext();
        this.apps = apps;
        this.activity = activity;
        ProgramListAndData programForGetHashmap = new ProgramListAndData();
        programForGetHashmap.initHashMap(activity);
        programHashMap = programForGetHashmap.programHashMap;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.item_desktop,null);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DesktopAdapter.ViewHolder holder, int position) {
        if(!(apps[position].startsWith(Program.DriversPrefix) || apps[position].startsWith(Program.AdditionalSoftPrefix))) {
            holder.app_icon.setImageResource(ProgramListAndData.programIcon.get(apps[position]));
            holder.app_name.setText(activity.words.get(apps[position]));
            holder.app_name.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"));
            holder.itemView.setOnClickListener(v -> {
                Program program = programHashMap.get(apps[position]);
                program.openProgram();
                System.out.println(StringArrayWork.ArrayListToString(activity.apps));
            });
        }
        else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(apps.length, 18);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView app_icon;
        public final TextView app_name;

        ViewHolder(View view) {
            super(view);
            app_icon = view.findViewById(R.id.app_icon);
            app_name = view.findViewById(R.id.app_name);
        }
    }
}
