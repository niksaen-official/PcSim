package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.niksaen.pcsim.R;

public class BackgroundResourceGradientAdapter extends RecyclerView.Adapter<BackgroundResourceGradientAdapter.ViewHolder> {

    private int[] gradientId = {
            R.drawable.gradient1,R.drawable.gradient5,R.drawable.gradient9,R.drawable.gradient13,R.drawable.gradient17,
            R.drawable.gradient2,R.drawable.gradient6,R.drawable.gradient10,R.drawable.gradient14,R.drawable.gradient18,
            R.drawable.gradient3,R.drawable.gradient7,R.drawable.gradient11,R.drawable.gradient15,R.drawable.gradient19,
            R.drawable.gradient4,R.drawable.gradient8,R.drawable.gradient12,R.drawable.gradient16,R.drawable.gradient20,
    };

    private View testBackground;
    private LayoutInflater layoutInflater;
    public BackgroundResourceGradientAdapter(Context context, View testBackground){
        layoutInflater = LayoutInflater.from(context);
        this.testBackground = testBackground;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_color_2,viewGroup,false);
        return new ViewHolder(view);
    }

    public int currentGradientId;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.colorView.setBackgroundResource(gradientId[i]);
        viewHolder.colorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGradientId = gradientId[i];
                testBackground.setBackgroundResource(gradientId[i]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gradientId.length;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        Button colorView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.current_color);
        }
    }
}

