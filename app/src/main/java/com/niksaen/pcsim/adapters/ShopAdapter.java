package com.niksaen.pcsim.adapters;

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
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.save.Settings;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private ArrayList<String> strings;
    private Context context;
    private String type;
    private Typeface font;

    public ShopAdapter(Context context, ArrayList<String> strings,String type){
        this.strings = strings;
        this.context = context;
        this.type = type;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shop,null));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShopAdapter.ViewHolder holder, int position) {
        setStyle(holder);
        holder.text.setTypeface(font,Typeface.BOLD);
        holder.text.setText(strings.get(position));
        if(type != "icon") {
            holder.icon.setImageDrawable(new AssetFile(context).getImage("pc_component/images/" + type + "/" + strings.get(position) + ".png"));
        }else{
            holder.icon.setImageDrawable(new AssetFile(context).getImage("icons/iconShop/"+new Settings(context).Theme+ "/" + strings.get(position) + ".png"));
        }
    }

    private void setStyle(ShopAdapter.ViewHolder holder){
        if(new Settings(context).Theme == "Dark"){
            holder.text.setTextColor(Color.WHITE);
        }
        else {
            holder.text.setTextColor(Color.BLACK);
        }
    }
    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView text;
        public ViewHolder(View item){
            super(item);
            icon = item.findViewById(R.id.icon);
            text = item.findViewById(R.id.text);
        }
    }
}
