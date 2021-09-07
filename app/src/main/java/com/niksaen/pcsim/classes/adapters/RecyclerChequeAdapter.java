package com.niksaen.pcsim.classes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.pcComponents.PcComponent;
import com.niksaen.pcsim.save.Settings;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerChequeAdapter extends RecyclerView.Adapter<RecyclerChequeAdapter.ViewHolder>{
    private ArrayList<PcComponent> pcComponents;
    private Context context;
    private Typeface font;

    public RecyclerChequeAdapter(Context context, ArrayList<PcComponent> pcComponents){
        this.pcComponents = pcComponents;
        this.context = context;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        getLanguage();
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerChequeAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RecyclerChequeAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_textview,null));
    }

    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+new Settings(context).Language+".json"),typeToken.getType());
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerChequeAdapter.ViewHolder holder, int position) {
        holder.text.setTypeface(font);
        holder.text.setBackgroundColor(Color.WHITE);
        holder.text.setText(
                words.get("Name of product")+":\n"+words.get(pcComponents.get(position).Type)+" "+pcComponents.get(position).Name+"\n"
                +words.get("The price of the product")+": "+pcComponents.get(position).Price+"R");
    }
    @Override
    public int getItemCount() {
        return pcComponents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(View item){
            super(item);
            text = item.findViewById(R.id.text);
        }
    }
}
