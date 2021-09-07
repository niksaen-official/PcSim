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

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PcComponentStringAdapter  extends RecyclerView.Adapter<PcComponentStringAdapter.ViewHolder>{
        private List<String> pcComponents;
        private Context context;
        private Typeface font;
        private String type;

    public PcComponentStringAdapter(Context context, List<String> pcComponents, String type){
        this.pcComponents = pcComponents;
        this.context = context;
        this.type = type;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

        @NonNull
        @NotNull
        @Override
        public PcComponentStringAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new PcComponentStringAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pc_component,null));
    }

        @Override
        public void onBindViewHolder(@NonNull @NotNull PcComponentStringAdapter.ViewHolder holder, int position) {
        holder.text.setTypeface(font,Typeface.BOLD);
        holder.text.setText(pcComponents.get(position));
        holder.icon.setImageDrawable(new AssetFile(context).getImage("pc_component/images/"+ type+"/" + pcComponents.get(position) + ".png"));
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
