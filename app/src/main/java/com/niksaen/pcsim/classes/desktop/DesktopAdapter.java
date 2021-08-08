package com.niksaen.pcsim.classes.desktop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;

import org.jetbrains.annotations.NotNull;

public class DesktopAdapter extends  RecyclerView.Adapter<DesktopAdapter.ViewHolder> {

    Context context;
    String[] apps;
    public DesktopAdapter(Context context,String[] apps){
        this.context = context;
        this.apps = apps;
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

    }

    @Override
    public int getItemCount() {
        return apps.length;
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
