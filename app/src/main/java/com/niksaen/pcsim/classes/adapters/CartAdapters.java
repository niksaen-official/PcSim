package com.niksaen.pcsim.classes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.save.Settings;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartAdapters extends RecyclerView.Adapter<CartAdapters.ViewHolder> {

    private ArrayList<PcComponent> pcComponents;
    private Context context;
    private Typeface font;

    public CartAdapters(Context context, ArrayList<PcComponent> pcComponents){
        this.pcComponents = pcComponents;
        this.context = context;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    public CartAdapters(Context context, List<String> pcComponents, String type){
        for(String name:pcComponents){
            this.pcComponents.add(new PcComponent(context,name,type));
        }
        this.context = context;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    public PcComponent get(int position){ return pcComponents.get(position); }
    public void remove(int pos){pcComponents.remove(pos);}
    public void add(PcComponent component){pcComponents.add(component);}

    @NonNull
    @NotNull
    @Override
    public CartAdapters.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CartAdapters.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pc_component,null));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapters.ViewHolder holder, int position) {
        holder.text.setTypeface(font,Typeface.BOLD);
        holder.text.setTextColor(Color.WHITE);
        holder.text.setText(pcComponents.get(position).Name);
        holder.icon.setImageDrawable(pcComponents.get(position).Textures);
    }
    @Override
    public int getItemCount() {
        return pcComponents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;
        public ViewHolder(View item){
            super(item);
            icon = item.findViewById(R.id.image);
            text = item.findViewById(R.id.text);
        }
    }
}
