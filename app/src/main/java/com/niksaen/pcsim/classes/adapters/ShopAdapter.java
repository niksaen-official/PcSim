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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.save.Settings;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private ArrayList<String> strings;
    private Context context;
    private String type;
    private Typeface font;

    public String getType(){
        return this.type;
    }
    public String getItem(int position){ return this.strings.get(position); }

    public ShopAdapter(Context context, ArrayList<String> strings,String type){
        this.strings = strings;
        this.context = context;
        this.type = type;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        getLanguage();
    }

    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+new Settings(context).Language+".json"),typeToken.getType());
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shop,null));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShopAdapter.ViewHolder holder, int position) {
        holder.text.setTypeface(font,Typeface.BOLD);
        holder.text.setTextColor(Color.WHITE);
        if(type != "icon") {
            holder.text.setText(strings.get(position));
            holder.icon.setImageDrawable(new AssetFile(context).getImage("pc_component/images/" + type + "/" + strings.get(position) + ".png"));
        }else{
            holder.text.setText(words.get(getItem(position)));
            holder.icon.setImageDrawable(new AssetFile(context).getImage("icons/shop/" + strings.get(position) + ".png"));
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
